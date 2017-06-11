package com.centit.support.scaffold;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class VerificationHibernateDataType {
    private String po;
    private String target;
    private String output;
    private TaskDesc taskDesc;

    private DatabaseMetaData databaseMetaData;
    private Map<String, Integer> dataType = new HashMap<String, Integer>();
    private Map<Integer, String> dataTypeValue = new HashMap<Integer, String>();

    private StringBuilder classnotfoundlog = new StringBuilder();
    private StringBuilder notmatchlog = new StringBuilder();
    private static StringBuilder exception = new StringBuilder();
    private String N = "\n";

    public static void runTask(TaskDesc taskDesc) {
        VerificationHibernateDataType v = new VerificationHibernateDataType();
        try {
            v.init(taskDesc);
            v.println();
        } catch (Exception e) {
            e.printStackTrace();
            exception.append(e);
        }
    }

    private DatabaseMetaData getDatabaseMetaData(TaskDesc taskDesc) {
        if (null != databaseMetaData) {
            return databaseMetaData;
        }
        try {
            Class.forName(taskDesc.getDataSourceDesc().getDriver());
        } catch (ClassNotFoundException e) {
            exception.append(e);
        }

        try {
            Connection connection =  DriverManager.getConnection(taskDesc.getDataSourceDesc().getConnUrl(),
                    taskDesc.getDataSourceDesc().getUsername(), taskDesc.getDataSourceDesc().getPassword());

            return databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            exception.append(e);
        }
        return null;
    }

    private void init(TaskDesc taskDesc) throws IllegalArgumentException, IllegalAccessException {
        target = taskDesc.getProjDir() + "/target/classes/";
        po = target + taskDesc.getAppPackagePath() + "/po";

        output = taskDesc.getOutput();
        this.taskDesc = taskDesc;

        // java类型与sql类型对应[类型多时再继续添加]
        dataType.put("java.lang.String", Types.VARCHAR);
        dataType.put("java.lang.Long", Types.DECIMAL);
        dataType.put("java.util.Date", Types.DATE);

        // 打印时将java.sql.Types中常量转换为字符串
        for (Field field : Types.class.getDeclaredFields()) {
            dataTypeValue.put(field.getInt(Types.class), field.getName());
        }
    }

    private List<String> listHbmXmlFileNames(File f) throws IOException {

        final List<String> names = new ArrayList<String>();

        f.listFiles(new FilenameFilter() {
            // 过滤class文件和hbm.xml文件
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".hbm.xml")) {
                    names.add(name);
                    return true;
                }
                return false;
            }

        });

        return names;
    }

    private Hbm parseXml(String name) throws DocumentException, SAXException {
        SAXReader reader = new SAXReader();
        // 去除dtd验证
        reader.setValidation(false);
        reader.setEntityResolver(new EntityResolver() {

            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

                return new InputSource(new StringReader(""));
            }
        });

        Document document = reader.read(new File(po + "/" + name));

        Element root = document.getRootElement();

        Element table = (Element) root.element("class");
        String tableName = table.attributeValue("table");
        if (null == tableName || "".equals(tableName)) {

            exception.append("hbm.xml : " + name + " tablename not found" + N);
            return null;
        }
        Hbm hbm = new Hbm(name, tableName);

        // id
        Element id = table.element("id");
        if (null != id) {
            Element column = id.element("column");

            hbm.getColumns().add(this.getColumn(id.attributeValue("type"), column));
        }

        // property
        List<Element> property = table.elements("property");
        for (Element element : property) {
            Element column = element.element("column");

            hbm.getColumns().add(this.getColumn(element.attributeValue("type"), column));
        }
        // 无须关心set,onetomany,manytomany,manytoone,onetoone,对象终究会转换成数据库列,匹配列就可以

        return hbm;
    }

    private int getType(String strType, String length) {
        Integer type = dataType.get(strType);
        if ("java.lang.String".equals(strType) && "1".equals(length)) {
            // type is char
            type = Types.CHAR;
        }

        return type;
    }

    private Column getColumn(String type, Element column) {
        return new Column(column.attributeValue("name"), this.getType(type, column.attributeValue("length")),
                column.attributeValue("precision"), column.attributeValue("scale"), column.attributeValue("length"),
                column.attributeValue("not-null"));
    }

    private Map<Hbm, List<Column[]>> p = new HashMap<Hbm, List<Column[]>>();

    private void verification(List<Hbm> hbms) {
        for (Hbm hbm : hbms) {
            for (Column c : hbm.getColumns()) {
                try {
                    Column z = this.getMetadataColumn(hbm.getTableName(), c.getName());
                    if (!c.equals(z)) {
                        if (!p.containsKey(hbm)) {
                            p.put(hbm, new ArrayList<Column[]>());
                        }

                        p.get(hbm).add(new Column[] { c, z });
                    }

                } catch (SQLException e) {
                    exception.append(e);
                }

            }
        }

        for (Entry<Hbm, List<Column[]>> e : p.entrySet()) {
            notmatchlog.append("hbm.xml : " + e.getKey().getXmlName() + ", ");
            notmatchlog.append("table : " + e.getKey().getTableName() + " {" + N);

            for (Column[] c : e.getValue()) {
                Column x = c[0];
                Column y = c[1];
                notmatchlog.append("  column : ");
                notmatchlog.append(x.getName() + " ");
                if (!x.getType().equals(y.getType())) {
                    notmatchlog.append(", type : {" + dataTypeValue.get(x.getType()) + " : "
                            + dataTypeValue.get(y.getType()) + "}");
                }

                if (null != x.getPrecision()) {
                    if (!x.getPrecision().equals(y.getPrecision())) {
                        notmatchlog.append(", precision : {" + x.getPrecision() + " : " + y.getPrecision() + "}");
                    }
                }

                if (null != x.getScale()) {
                    if (!x.getScale().equals(y.getScale())) {
                        notmatchlog.append(", scale : {" + x.getScale() + " : " + y.getScale() + "}");
                    }
                }

                if (null != x.getLength()) {
                    if (!x.getLength().equals(y.getLength())) {
                        notmatchlog.append(", length : {" + dataTypeValue.get(x.getType()) + "(" + x.getLength()
                                + ") : " + dataTypeValue.get(y.getType()) + "(" + y.getLength() + ")}");
                    }
                }

                if (!x.getNotNull().equals(y.getNotNull())) {
                    notmatchlog.append(", not-null : {" + x.getNotNull() + " : " + y.getNotNull() + "}");
                }

                notmatchlog.append(" " + N);
            }

            notmatchlog.append("} " + N);

        }
    }

    private Column getMetadataColumn(String tableName, String columnName) throws SQLException {
        ResultSet rs = databaseMetaData.getColumns(null, taskDesc.getDataSourceDesc().getUsername().toUpperCase(), tableName,
                columnName);

        while (rs.next()) {
            return new Column(rs.getString("COLUMN_NAME"), rs.getInt("DATA_TYPE"), rs.getString("COLUMN_SIZE"),
                    rs.getString("DECIMAL_DIGITS"), rs.getString("COLUMN_SIZE"),
                    String.valueOf(rs.getInt("NULLABLE") == DatabaseMetaData.columnNoNulls));
        }
        return null;
    }

    private void println() throws Exception {
        getDatabaseMetaData(taskDesc);
        if (null != databaseMetaData) {

            run();
        }

        File o = new File(output);
        if (!o.exists()) {
            o.mkdirs();
        }

        o = new File(output + "/output.txt");
        if (!o.canRead()) {
            o.createNewFile();
        }
        BufferedWriter br = new BufferedWriter(new FileWriter(o));

        br.append(exception);
        br.append(N);

        br.append(classnotfoundlog);

        if (0 != classnotfoundlog.length()) {
            br.append(N);

        }
        br.append(notmatchlog);

        if (0 == notmatchlog.length() && 0 == classnotfoundlog.length()) {
            br.append("All matching");
        }

        br.close();

        Runtime.getRuntime().exec("cmd.exe /c start " + output);
    }

    private void run() throws Exception {
        List<String> names = listHbmXmlFileNames(new File(po));
        if (!names.isEmpty()) {
            List<Hbm> hbms = new ArrayList<Hbm>();
            for (String name : names) {
                Hbm hbm = parseXml(name);
                if (null != hbm) {
                    hbms.add(hbm);
                }
            }

            this.verification(hbms);
        } else {

            classnotfoundlog.append("hibernate configuration file not found");
        }
    }

    public static void main(String[] args) throws Exception {

    }

    private class Hbm {
        private String xmlName;

        private String tableName;

        private List<Column> columns = new ArrayList<Column>();

        public Hbm(String xmlName, String tableName) {
            super();
            this.xmlName = xmlName;
            this.tableName = tableName;
        }

        public String getXmlName() {
            return xmlName;
        }

        public String getTableName() {
            return tableName;
        }

        public List<Column> getColumns() {
            return columns;
        }

    }

    private class Column {
        private String name;

        private Integer type;

        private Integer precision;

        private Integer scale;

        private Integer length;

        private Boolean notNull;

        public Column(String name, Integer type, String precision, String scale, String length, String notNull) {
            super();
            this.name = name;
            this.type = type;
            this.precision = null == precision ? null : Integer.parseInt(precision);
            this.scale = null == scale ? null : Integer.parseInt(scale);
            this.length = null == length ? null : Integer.parseInt(length);
            if (null == notNull) {
                this.notNull = false;
            } else {
                this.notNull = Boolean.parseBoolean(notNull);
            }
        }

        public String getName() {
            return name;
        }

        public Integer getType() {
            return type;
        }

        public Integer getPrecision() {
            return precision;
        }

        public Integer getScale() {
            return scale;
        }

        public Integer getLength() {
            return length;
        }

        public Boolean getNotNull() {
            return notNull;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Column)) {
                return false;
            }

            Column c = (Column) obj;

            boolean flag = true;

            if (!this.getName().equals(c.getName())) {

                return false;
            }

            if (!this.getType().equals(c.getType()) && Types.VARCHAR != this.getType() && Types.CHAR != c.getType()) {
                return false;
            }

            if (null != this.getPrecision()) {
                if (!this.getPrecision().equals(c.getPrecision())) {
                    return false;
                }
            }

            if (null != this.getScale()) {
                if (!this.getScale().equals(c.getScale())) {
                    return false;
                }
            }

            if (null != this.getLength()) {
                if (!this.getLength().equals(c.getLength())) {
                    return false;
                }
            }

            if (!this.getNotNull().equals(c.getNotNull())) {
                return false;
            }

            return flag;
        }

        @Override
        public String toString() {
            return "列属性 [name=" + name + ", type=" + dataTypeValue.get(type) + ", precision=" + precision + ", scale="
                    + scale + ", length=" + length + ", notNull=" + notNull + "]";
        }

    }

}
