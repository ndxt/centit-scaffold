package com.centit.support.scaffold;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.centit.support.database.metadata.HibernateMapInfo;
import com.centit.support.file.FileSystemOpt;
import com.centit.support.xml.IgnoreDTDEntityResolver;

/**
 * 
 * @author codefan 自动 设置配置文件 daoContext.xml applicationContext-app.xml action-servlet.xml struts-config.xml
 *         validation.xml
 * 
 *         create by yanghuaisheng@gmail.com
 */

public class XmlConfigHanlder {

    private ScaffoldTranslate m_varTrans;
    private String sConfigPath;
    private String sAppPath;
    private HibernateMapInfo hbmMetadata;
    private Config cConfig;

    public void setConfig(Config cg) {
        cConfig = cg;
    }

    public void setConfigPath(String configPath) {
        sConfigPath = configPath;
    }

    public void setAppPath(String appPath) {
        sAppPath = appPath;
    }

    public void setScaffoldTranslate(ScaffoldTranslate varTrans) {
        m_varTrans = varTrans;
    }

    protected void writerConfigFile(Document doc, String xmlFile) {
        XMLWriter output;
        try {
            output = new XMLWriter(new FileWriter(new File(xmlFile)));
            output.write(doc);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void addDaoContext(String xmlFile) {
        Document doc = null;
        Element bean = null;

        if (!FileSystemOpt.existFile(xmlFile)) { // create

            doc = DocumentHelper.createDocument();
            // Doc.addProcessingInstruction("xml", "version=\"1.0\" encoding=\"gb2312\"");
            doc.addDocType("beans", "-//SPRING//DTD BEAN//EN", "http://www.springframework.org/dtd/spring-beans.dtd");
            Element root = doc.addElement("beans");// 首先建立根元素
            bean = root.addElement("bean");//

        } else {// addContext never update
            SAXReader builder = new SAXReader(false);
            builder.setValidation(false);
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            try {
                doc = builder.read(new File(xmlFile));
                Element root = doc.getRootElement();// 获取根元素
                bean = (Element) root.selectSingleNode("bean[@id=\"" + m_varTrans.getEntityName() + "Dao\"]");
                if (bean != null) { // 如果已经配置好了则返回
                    System.out.println("系统已经存在 " + m_varTrans.getEntityName() + "Dao 相关的配置！");
                    return;
                }
                bean = root.addElement("bean");//
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        if (bean == null) // 如果已经配置好了则返回
            return;
        bean.addAttribute("id", m_varTrans.getEntityName() + "Dao");
        if (m_varTrans.getHandleCfg().isCreateDaoImpl())
            bean.addAttribute("class", m_varTrans.getBasePackage() + ".dao.impl." + m_varTrans.getClassSimpleName()
                    + "DaoImpl");
        else
            bean.addAttribute("class", m_varTrans.getBasePackage() + ".dao." + m_varTrans.getClassSimpleName() + "Dao");
        Element property = bean.addElement("property");
        property.addAttribute("name", "sessionFactory");
        property.addElement("ref").addAttribute("bean", "sessionFactory");

        writerConfigFile(doc, xmlFile);
    }

    protected void addManagerContext(String xmlFile) {
        Document doc = null;
        Element bean = null;

        if (!FileSystemOpt.existFile(xmlFile)) { // create

            doc = DocumentHelper.createDocument();
            // Doc.addProcessingInstruction("xml", "version=\"1.0\" encoding=\"gb2312\"");
            doc.addDocType("beans", "-//SPRING//DTD BEAN//EN", "http://www.springframework.org/dtd/spring-beans.dtd");
            Element root = doc.addElement("beans");// 首先建立根元素
            // root.addAttribute("default-autowire", "byType");
            bean = root.addElement("bean");//

        } else {// addContext never update
            SAXReader builder = new SAXReader(false);
            builder.setValidation(false);
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            try {
                doc = builder.read(new File(xmlFile));
                Element root = doc.getRootElement();// 获取根元素
                bean = (Element) root.selectSingleNode("bean[@id=\"" + m_varTrans.getEntityName() + "Manager\"]");
                if (bean != null) { // 如果已经配置好了则返回
                    System.out.println("系统已经存在 " + m_varTrans.getEntityName() + "Manager 相关的配置！");
                    return;
                }
                bean = root.addElement("bean");//
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        if (bean == null) // 如果已经配置好了则返回
            return;

        bean.addAttribute("id", m_varTrans.getEntityName() + "Manager");
        if (cConfig.isInTransaction()) {
            bean.addAttribute("parent", "baseTransactionProxy");
            Element property = bean.addElement("property");
            property.addAttribute("name", "target");
            bean = property.addElement("bean");
        }
        if (m_varTrans.getHandleCfg().isCreateManagerImpl())
            bean.addAttribute("class", m_varTrans.getBasePackage() + ".service.impl." + m_varTrans.getClassSimpleName()
                    + "ManagerImpl");
        else
            bean.addAttribute("class", m_varTrans.getBasePackage() + ".service." + m_varTrans.getClassSimpleName()
                    + "Manager");
        Element property = bean.addElement("property");
        property.addAttribute("name", m_varTrans.getEntityName() + "Dao");
        property.addAttribute("ref", m_varTrans.getEntityName() + "Dao");

        writerConfigFile(doc, xmlFile);
    }

    protected void addActionContext(String xmlFile) {
        Document doc = null;
        Element bean = null;

        if (!FileSystemOpt.existFile(xmlFile)) { // create

            doc = DocumentHelper.createDocument();
            // Doc.addProcessingInstruction("xml", "version=\"1.0\" encoding=\"gb2312\"");
            doc.addDocType("beans", "-//SPRING//DTD BEAN//EN", "http://www.springframework.org/dtd/spring-beans.dtd");
            Element root = doc.addElement("beans");// 首先建立根元素
            bean = root.addElement("bean");//
        } else {// addContext never update
            SAXReader builder = new SAXReader(false);
            builder.setValidation(false);
            builder.setEntityResolver(new IgnoreDTDEntityResolver());

            try {
                doc = builder.read(new File(xmlFile));
                Element root = doc.getRootElement();// 获取根元素
                bean = (Element) root.selectSingleNode("bean[@name=\"" + m_varTrans.getEntityName() + "Action\"]");
                // System.out.println("bean[@name=\""+m_varTrans.getEntityName()+"Action\"]");
                if (bean != null) { // 如果已经配置好了则返回
                    System.out.println("系统已经存在 " + m_varTrans.getEntityName() + "Action 相关的配置！");
                    return;
                }
                bean = root.addElement("bean");//
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        if (bean == null) // 如果已经配置好了则返回
            return;

        bean.addAttribute("name", m_varTrans.getEntityName() + "Action");
        bean.addAttribute("scope", "request");
        bean.addAttribute("class",  m_varTrans.getBasePackage()+".action."+m_varTrans.getClassSimpleName() +"Action");
        Element property = bean.addElement("property");
        property.addAttribute("name", m_varTrans.getEntityName() + "Manager");
        property.addAttribute("ref", m_varTrans.getEntityName() + "Manager");

        writerConfigFile(doc, xmlFile);
    }

    protected void addStruts1Config(String xmlFile) {
        Document doc = null;
        Element bean = null;

        if (!FileSystemOpt.existFile(xmlFile)) { // create

            doc = DocumentHelper.createDocument();
            // Doc.addProcessingInstruction("xml", "version=\"1.0\" encoding=\"gb2312\"");
            doc.addDocType("struts-config", "-//Apache Software Foundation//DTD Struts Configuration 1.2//EN",
                    "http://struts.apache.org/dtds/struts-config_1_2.dtd");

            Element root = doc.addElement("struts-config");// 首先建立根元素
            bean = root.addElement("form-beans").addElement("form-bean");//

            Element action = root.addElement("action-mappings").addElement("action");
            action.addAttribute("path", "/" + sAppPath + "/*").addAttribute("name", "{1}Form")
                    .addAttribute("parameter", "method").addAttribute("scope", "request")
                    .addAttribute("validate", "false")
                    .addAttribute("type", "org.springframework.web.struts.DelegatingActionProxy");
            action.addElement("forward").addAttribute("name", "init")
                    .addAttribute("path", "/page/" + sAppPath + "/{1}List.jsp");
            action.addElement("forward").addAttribute("name", "list")
                    .addAttribute("path", "/page/" + sAppPath + "/{1}List.jsp");
            action.addElement("forward").addAttribute("name", "view")
                    .addAttribute("path", "/page/" + sAppPath + "/{1}View.jsp");
            action.addElement("forward").addAttribute("name", "edit")
                    .addAttribute("path", "/page/" + sAppPath + "/{1}Form.jsp");
            action.addElement("forward").addAttribute("name", "success")
                    .addAttribute("path", "/" + sAppPath + "/{1}.do?method=list");

        } else {// addContext never update
            SAXReader builder = new SAXReader(false);
            builder.setValidation(false);
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            try {
                doc = builder.read(new File(xmlFile));
                Element root = doc.getRootElement();// 获取根元素
                bean = (Element) root.selectSingleNode("form-beans/form-bean[@name=\"" + m_varTrans.getEntityName()
                        + "Form\"]");
                if (bean != null) { // 如果已经配置好了则返回
                    System.out.println("系统已经存在 " + m_varTrans.getEntityName() + " Struts 相关的配置！");
                    return;
                }
                bean = root.element("form-beans");//
                if (bean == null)
                    bean = root.addElement("form-beans");
                bean = bean.addElement("form-bean");
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        bean.addAttribute("name", m_varTrans.getEntityName() + "Form").addAttribute("type",
                "org.apache.struts.validator.LazyValidatorForm");

        writerConfigFile(doc, xmlFile);
    }

    protected void addStruts2Config(String xmlFile, Config config) {
        if (!FileSystemOpt.existFile(xmlFile)) { // create

            Document doc = DocumentHelper.createDocument();
            // Doc.addProcessingInstruction("xml", "version=\"1.0\" encoding=\"gb2312\"");
            doc.addDocType("struts", "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN",
                    "http://struts.apache.org/dtds/struts-2.0.dtd");

            Element root = doc.addElement("struts");// 首先建立根元素
            Element pack = root.addElement("package");
            pack.addAttribute("name", sAppPath).addAttribute("namespace", "/" + sAppPath)
                    .addAttribute("extends", "centitbsdfw");

            Element action = pack.addElement("action");
            action.addAttribute("name", "*").addAttribute("class", "{1}Action");
            action.addElement("result").addAttribute("name", "list").setText("/page/" + sAppPath + "/{1}List.jsp");
            action.addElement("result").addAttribute("name", "view").setText("/page/" + sAppPath + "/{1}View.jsp");
            action.addElement("result").addAttribute("name", "edit").setText("/page/" + sAppPath + "/{1}Form.jsp");
            action.addElement("result").addAttribute("name", "built").addAttribute("type", "chain");

            // Dwz 页面
            if (config.isIsDwz()) {
                Element result = action.addElement("result");
                result.addAttribute("name", "delete").addAttribute("type", "centitui");
                result.addElement("param").addAttribute("name", "newWin").setText("false");

                result = action.addElement("result");
                result.addAttribute("name", "success").addAttribute("type", "centitui");
                result.addElement("param").addAttribute("name", "newWin").setText("false");
                result.addElement("param").addAttribute("name", "callbackType").setText("closeCurrent");
                result.addElement("param").addAttribute("name", "navTabId").setText("");

            } else {
                Element result = action.addElement("result");
                result.addAttribute("name", "built").addAttribute("type", "chain");
                result.addElement("param").addAttribute("name", "actionName").setText("{1}");
                result.addElement("param").addAttribute("name", "method").setText("edit");

                result = action.addElement("result");
                result.addAttribute("name", "success").addAttribute("type", "chain");
                result.addElement("param").addAttribute("name", "actionName").setText("{1}");
                result.addElement("param").addAttribute("name", "method").setText("list");
            }

            writerConfigFile(doc, xmlFile);
        }
    }

    protected void addValidationConfig(String xmlFile) {
        Document doc = null;
        Element bean = null;

        if (!FileSystemOpt.existFile(xmlFile)) { // create

            doc = DocumentHelper.createDocument();
            // Doc.addProcessingInstruction("xml", "version=\"1.0\" encoding=\"gb2312\"");
            doc.addDocType("form-validation",
                    "-//Apache Software Foundation//DTD Commons Validator Rules Configuration 1.1.3//EN",
                    "http://jakarta.apache.org/commons/dtds/validator_1_1_3.dtd");

            Element root = doc.addElement("form-validation");// 首先建立根元素
            bean = root.addElement("formset").addElement("form");//
        } else {// addContext never update
            SAXReader builder = new SAXReader(false);
            builder.setValidation(false);
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            try {
                doc = builder.read(new File(xmlFile));
                Element root = doc.getRootElement();// 获取根元素
                bean = (Element) root
                        .selectSingleNode("formset/form[@name=\"" + m_varTrans.getEntityName() + "Form\"]");
                if (bean != null) { // 如果已经配置好了则返回
                    System.out.println("系统已经存在 " + m_varTrans.getEntityName() + " 验证相关的配置！");
                    return;
                }
                bean = root.element("formset");//
                if (bean == null)
                    bean = root.addElement("formset");
                bean = bean.addElement("form");
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        bean.addAttribute("name", m_varTrans.getEntityName() + "Form");

        int n = hbmMetadata.getKeyProperties().size();
        for (int i = 0; i < n; i++) {
            Element field = bean.addElement("field");
            field.addAttribute("property", hbmMetadata.getKeyProperty(i).getPropertyName());
            int maxL = hbmMetadata.getKeyProperty(i).getMaxLength();
            if ("String".equalsIgnoreCase(hbmMetadata.getKeyProperty(i).getJavaType()) && maxL > 0) {
                field.addAttribute("depends", "required,maxlength");
                Element var = field.addElement("var");
                var.addElement("var-name").setText("maxlength");
                var.addElement("var-value").setText(String.valueOf(maxL));
            } else {
                field.addAttribute("depends", "required");
            }
            field.addElement("arg").addAttribute("key", hbmMetadata.getKeyProperty(i).getColumnName())
                    .addAttribute("resource", "false");
        }

        n = hbmMetadata.getProperties().size();
        for (int i = 0; i < n; i++) {
            String sType = hbmMetadata.getProperty(i).getJavaType();
            int maxL = hbmMetadata.getProperty(i).getMaxLength();
            if (hbmMetadata.getProperty(i).isMandatory() || ("String".equalsIgnoreCase(sType) && maxL > 0)) {
                String depends = "";
                if (hbmMetadata.getProperty(i).isMandatory()) {
                    if ("String".equalsIgnoreCase(sType) && maxL > 0)
                        depends = "required,maxlength";
                    else
                        depends = "required";
                } else
                    depends = "maxlength";

                Element field = bean.addElement("field");
                field.addAttribute("property", hbmMetadata.getProperty(i).getPropertyName()).addAttribute("depends", depends);
                if ("String".equalsIgnoreCase(sType) && maxL > 0) {
                    Element var = field.addElement("var");
                    var.addElement("var-name").setText("maxlength");
                    var.addElement("var-value").setText(String.valueOf(maxL));
                }

                field.addElement("arg").addAttribute("key", hbmMetadata.getProperty(i).getColumnName())
                        .addAttribute("resource", "false");
            }
        }

        writerConfigFile(doc, xmlFile);
    }

    public void createConfigSuite(HibernateMapInfo hbmMD) {
        String sConfigDir = sConfigPath;
        hbmMetadata = hbmMD;
        m_varTrans.setHbmMetadata(hbmMD);
        FileSystemOpt.createDirect(sConfigDir);

        
        if (cConfig.isCreateDao())
            addDaoContext(sConfigDir + "/spring-dao.xml");
        if (cConfig.isCreateManager())
            addManagerContext(sConfigDir + "/spring-manager.xml");
        if (cConfig.isCreateAction()) {
            addActionContext(sConfigDir + "/spring-action.xml");
            addStruts2Config(sConfigDir + "/struts-action.xml", cConfig);
            // AddValidationConfig(sConfigDir+"/validation.xml");
        }
    }

}
