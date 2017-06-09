package com.centit.support.scaffold;

import com.centit.support.database.metadata.PdmReader;
import com.centit.support.database.metadata.SimpleTableInfo;
import com.centit.support.file.FileSystemOpt;

public class PDMHibernateReverse {

    public static void runTask(TaskDesc task) {
        if (!task.isRunPdmHibernateReverse())
            return;

        String sJavaSourcetDir = task.getProjDir() + '/' + task.getSrcDir();
        String sConfigPath = task.getProjDir() + '/' + task.getConfigDir();
        String appName = task.getAppName();
        String sClassPath = task.getAppPackagePath() + "/po";

        String sPackageName = sClassPath.replace('/', '.');
        String sTableNames = task.getReverseTables();
        String sPdmFile = task.getReversePdmfile();
        String[] sTables = sTableNames.split(",");
        String sTableSchema = task.getDbschema();
        // nCreateProperties = task.isCreateResource();

        // System.out.println( sPdmFile );

        PdmReader reader = new PdmReader();
        reader.setDBSchema(sTableSchema);
        if (!reader.loadPdmFile(sPdmFile)) {
            System.out.println("读取" + sPdmFile + "出错！");
            return;
        }
        // System.out.println("读取"+sPdmFile+"OK！");
        for (int i = 0; i < sTables.length; i++) {
            // 这个地方应该去掉前后空格和#10 #13 去掉
            String sTableName = sTables[i].trim().replaceAll("\r:", "").replaceAll("\n:", "");// .toUpperCase();
            SimpleTableInfo tm = reader.getTableMetadata(sTableName);

            if (task.isCreateHbmFile() && tm != null) {
                tm.setPackageName(sPackageName);

                String fileName = sJavaSourcetDir + "/" + sClassPath;
                FileSystemOpt.createDirect(fileName);

                tm.saveHibernateMappingFile(fileName + "/" + tm.getClassName() + ".hbm.xml");
                System.out.println("转换" + sTableName + "已完成！");
            }

            if (task.isCreateResource() && tm != null) {
                String sPropFileName = sConfigPath + "/" + appName.substring(0, 1).toUpperCase() + appName.substring(1)
                        + "Resource";// _zh_CN.properties";

                tm.addResource(sPropFileName);
                System.out.println("添加" + sTableName + "资源信息完成！");
            }
            if (tm == null)
                System.out.println("没有找到表 " + sTableName + "，请检查，注意大小写!");
        }
    }

    /**
     * @param args
     */

    public static void main(String[] args) {

        // DBConn.loadHibernateConfig("E:/Study/Centit/BSDFW/web/WEB-INF/hibernate.cfg.xml");
        if (args.length < 6) {
            System.out.println("缺少参数！");
            return;
        }

        String sJavaSourcetDir = args[0];
        String sConfigPath = args[1];
        String appName = args[2];
        String sClassPath = args[3] + "/po";

        String sPackageName = sClassPath.replace('/', '.');
        String sTableNames = args[4];
        String sPdmFile = args[5];
        String[] sTables = sTableNames.split(",");
        int nCreateProperties = 1;
        String sTableSchema = args[6];
        if (args.length > 7) {
            try {
                nCreateProperties = Integer.valueOf(args[7]);
            } catch (NumberFormatException e) {
                nCreateProperties = 1;
            }
        }

        // System.out.println( sPdmFile );

        PdmReader reader = new PdmReader();
        reader.setDBSchema(sTableSchema);
        if (!reader.loadPdmFile(sPdmFile)) {
            System.out.println("读取" + sPdmFile + "出错！");
            return;
        }
        // System.out.println("读取"+sPdmFile+"OK！");
        for (int i = 0; i < sTables.length; i++) {
            // 这个地方应该去掉前后空格和#10 #13 去掉
            String sTableName = sTables[i].trim().replaceAll("\r:", "").replaceAll("\n:", "");// .toUpperCase();
            SimpleTableInfo tm = reader.getTableMetadata(sTableName);

            if (nCreateProperties != 2 && tm != null) {
                tm.setPackageName(sPackageName);
                tm.saveHibernateMappingFile(sJavaSourcetDir + "/" + sClassPath + "/" + tm.getClassName() + ".hbm.xml");
                System.out.println("转换" + sTableName + "已完成！");
            }

            if (nCreateProperties != 0 && tm != null) {
                String sPropFileName = sConfigPath + "/" + appName.substring(0, 1).toUpperCase() + appName.substring(1)
                        + "Resource";// _zh_CN.properties";

                tm.addResource(sPropFileName);
                System.out.println("添加" + sTableName + "资源信息完成！");
            }
            if (tm == null)
                System.out.println("没有找到表 " + sTableName + "，请检查，注意大小写!");
        }
    }

}
