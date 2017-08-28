package com.centit.support.scaffold;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.centit.support.algorithm.StringRegularOpt;
import com.centit.support.database.utils.DataSourceDescription;
import com.centit.support.xml.IgnoreDTDEntityResolver;

public class TaskDesc {
    private static Log log = LogFactory.getLog(TaskDesc.class);
    // project properties
    private String projDir;
    private String srcDir;
    private String jspDir;
    private String configDir;
    private String appName;
    private String appPackagePath;

    private DataSourceDescription dataSource;

    // runHibernateReverse task
    private boolean runHibernateReverse;
    private String tableNames;

    // runPdmHibernateReverse task
    private boolean runPdmHibernateReverse;
    private String reversePdmfile;
    private String dbschema;
    private String reverseTables;
    private boolean createHbmFile;
    private boolean createResource;

    // runCreateSourceHandler
    private boolean runCreateSourceHandler;
    private String instruction;
    private String hbmfile;
    private String javatemplate;
    private String jsptemplate;

	private boolean runCreateSourceMvcHandler;
    private String mvcPdmfile;
    private String mvcTables;
    private boolean force;
    
    // runMetadataHandler
    private boolean runMetadataHandler;
    private String metaPdmfile;
    private String metaTables;

    // runVerificationhandler
    private boolean runVerificationHandler;
    private String output;

    public boolean isRunVerificationHandler() {
        return runVerificationHandler;
    }

    public String getOutput() {
        return output;
    }

    public String getProjDir() {
        return projDir;
    }

    public String getSrcDir() {
        return srcDir;
    }

    public String getJspDir() {
        return jspDir;
    }

    public String getConfigDir() {
        return configDir;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppPackagePath() {
        return appPackagePath;
    }

    public DataSourceDescription getDataSourceDesc() {
        return dataSource;
    }

    public boolean isRunHibernateReverse() {
        return runHibernateReverse;
    }

    public String getTableNames() {
        return tableNames;
    }

    public boolean isRunPdmHibernateReverse() {
        return runPdmHibernateReverse;
    }

    public String getReversePdmfile() {
        return reversePdmfile;
    }

    public String getDbschema() {
        return dbschema;
    }

    public String getReverseTables() {
        return reverseTables;
    }

    public boolean isCreateResource() {
        return createResource;
    }

    public boolean isCreateHbmFile() {
        return createHbmFile;
    }

    public boolean isRunCreateSourceHandler() {
        return runCreateSourceHandler;
    }

    /**
     * 
     * @return 生成代码指示
     */
    public String getInstruction() {
        return instruction;
    }

    public String getHbmfile() {
        return hbmfile;
    }

    public String getJavatemplate() {
        return javatemplate;
    }

    public String getJsptemplate() {
        return jsptemplate;
    }

    public boolean isRunMetadataHandler() {
        return runMetadataHandler;
    }

    public String getMetaPdmfile() {
        return metaPdmfile;
    }

    public String getMetaTables() {
        return metaTables;
    }


    public boolean isRunCreateSourceMvcHandler() {
		return runCreateSourceMvcHandler;
	}

	public String getMvcPdmfile() {
		return mvcPdmfile;
	}

	public String getMvcTables() {
		return mvcTables;
	}

	
    public TaskDesc() {
        runHibernateReverse = runPdmHibernateReverse = 
        		createResource = runCreateSourceHandler = runMetadataHandler = false;
        runCreateSourceMvcHandler = false;
        force = false;
        dataSource = new DataSourceDescription();
    }

    public int loadTaskFromConfig(String configFile) {
        SAXReader builder = new SAXReader(false);
        builder.setValidation(false);
        builder.setEntityResolver(new IgnoreDTDEntityResolver());

        Document doc = null;
        int nTaskSum = 0;
        try {
            doc = builder.read(new File(configFile));
            Element property;
            Element root = doc.getRootElement();// 获取根元素
            Element project = (Element) root.selectSingleNode("project");
            if (project == null)
                return -1;

            property = (Element) project.selectSingleNode("property[@name=\"projdir\"]");
            if (property != null)
                projDir = property.attributeValue("value");
            property = (Element) project.selectSingleNode("property[@name=\"srcdir\"]");
            if (property != null)
                srcDir = property.attributeValue("value");
            property = (Element) project.selectSingleNode("property[@name=\"jspdir\"]");
            if (property != null)
                jspDir = property.attributeValue("value");
            property = (Element) project.selectSingleNode("property[@name=\"configdir\"]");
            if (property != null)
                configDir = property.attributeValue("value");
            property = (Element) project.selectSingleNode("property[@name=\"appname\"]");
            if (property != null)
                appName = property.attributeValue("value");
            property = (Element) project.selectSingleNode("property[@name=\"apppackagepath\"]");
            if (property != null)
                appPackagePath = property.attributeValue("value");

            Element dbconfig = (Element) root.selectSingleNode("dbconfig");
            if (dbconfig != null) {
                String configtype = dbconfig.attributeValue("type");
                if ("ref".equals(configtype)) {
                    property = (Element) dbconfig.selectSingleNode("property[@name=\"configfile\"]");
                    if (property != null) {
                        String sConfFile = property.attributeValue("value");
                        if (StringUtils.isNotBlank(sConfFile)) {
                            if (sConfFile.indexOf(':') < 0)
                                sConfFile = projDir + '/' + sConfFile;
                            String sDbBeanName = "dataSource";
                            property = (Element) dbconfig.selectSingleNode("property[@name=\"beanname\"]");
                            if (property != null)
                                sDbBeanName = property.attributeValue("value");
                            dataSource.loadHibernateConfig(sConfFile, sDbBeanName);
                        }
                    }
                } else {
                	property = (Element) dbconfig.selectSingleNode("property[@name=\"url\"]");
                    if (property != null)
                        dataSource.setConnUrl(property.attributeValue("value"));
                    
                    property = (Element) dbconfig.selectSingleNode("property[@name=\"driverClassName\"]");
                    if (property != null)
                        dataSource.setDriver(property.attributeValue("value"));
                    
                    property = (Element) dbconfig.selectSingleNode("property[@name=\"username\"]");
                    if (property != null)
                        dataSource.setUsername(property.attributeValue("value"));
                    property = (Element) dbconfig.selectSingleNode("property[@name=\"password\"]");
                    if (property != null)
                        dataSource.setPassword(property.attributeValue("value"));
                }
            }// dbconfig
            List<Node> taskList =  root.selectNodes("//tasklist/task[@run=\"yes\"]");
            for (Node task : taskList) {
                String taskName = ((Element)task).attributeValue("name");
                if ("runHibernateReverse".equals(taskName)) {
                    runHibernateReverse = true;                    
                    property = (Element) ((Element)task).selectSingleNode("property[@name=\"tables\"]");
                    if (property != null)
                        tableNames = property.attributeValue("value");
                    nTaskSum++;
                } else if ("runPdmHibernateReverse".equals(taskName)) {
                    runPdmHibernateReverse = true;
                    property = (Element) task.selectSingleNode("property[@name=\"pdmfile\"]");
                    if (property != null)
                        reversePdmfile = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"dbschema\"]");
                    if (property != null)
                        dbschema = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"tables\"]");
                    if (property != null)
                        reverseTables = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"createresource\"]");
                    if (property != null)
                        createResource = "yes".equalsIgnoreCase(property.attributeValue("value"));
                    property = (Element) task.selectSingleNode("property[@name=\"createhbmfile\"]");
                    if (property != null)
                        createHbmFile = "yes".equalsIgnoreCase(property.attributeValue("value"));

                    nTaskSum++;
                } else if ("runCreateSourceHandler".equals(taskName)) {
                    runCreateSourceHandler = true;
                    property = (Element) task.selectSingleNode("property[@name=\"instruction\"]");
                    if (property != null)
                        instruction = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"hbmfile\"]");
                    if (property != null)
                        hbmfile = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"javatemplate\"]");
                    if (property != null)
                        javatemplate = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"jsptemplate\"]");
                    if (property != null)
                        jsptemplate = property.attributeValue("value");

                    nTaskSum++;
                }  else if ("runCreateSourceMvcHandler".equals(taskName)) {
                	runCreateSourceMvcHandler = true;
                    property = (Element) task.selectSingleNode("property[@name=\"instruction\"]");
                    if (property != null)
                        instruction = property.attributeValue("value");
                    
                    property = (Element) task.selectSingleNode("property[@name=\"force\"]");
                    if (property != null){
                    	force = StringRegularOpt.isTrue(property.attributeValue("value"));
                    }else
                    	force = false;

                    property = (Element) task.selectSingleNode("property[@name=\"pdmfile\"]");
                    if (property != null)
                    	mvcPdmfile = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"dbschema\"]");
                    if (property != null)
                        dbschema = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"tables\"]");
                    if (property != null)
                        mvcTables = property.attributeValue("value");                    

                    property = (Element) task.selectSingleNode("property[@name=\"javatemplate\"]");
                    if (property != null)
                        javatemplate = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"jsptemplate\"]");
                    if (property != null)
                        jsptemplate = property.attributeValue("value");

                    nTaskSum++;
                } else if ("runMetadataHandler".equals(taskName)) {
                    runMetadataHandler = true;
                    property = (Element) task.selectSingleNode("property[@name=\"pdmfile\"]");
                    if (property != null)
                        metaPdmfile = property.attributeValue("value");
                    property = (Element) task.selectSingleNode("property[@name=\"tables\"]");
                    if (property != null)
                        metaTables = property.attributeValue("value");
                    nTaskSum++;
                } else if ("runVerificationHandler".equals(taskName)) {
                    runVerificationHandler = true;
                    property = (Element) task.selectSingleNode("property[@name=\"output\"]");
                    if (property != null)
                        output = property.attributeValue("value");

                    nTaskSum++;
                }
                ;
            }
        } catch (DocumentException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return nTaskSum;
    }

	/**
	 * @return the force
	 */
	public boolean isForce() {
		return force;
	}

	/**
	 * @param force the force to set
	 */
	public void setForce(boolean force) {
		this.force = force;
	}
}
