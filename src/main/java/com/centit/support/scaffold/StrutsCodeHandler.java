package com.centit.support.scaffold;

import com.centit.support.database.metadata.HibernateMapInfo;
import com.centit.support.file.FileSystemOpt;

/**
 * 根据 hibernate xml元数据文件生成 相关的类 和 jsp
 */
public class StrutsCodeHandler extends CodeHandler{

    private String sJavaSourcePath;
    private String sJspSourcePath;

    private String sClassPath;
    private String sJavaTmplDir, sJspTmplDir;;
    private Config cConfig;

    public void setConfig(Config cg) {
        cConfig = cg;
    }

    public String getJavaTempDir() {
        return sJavaTmplDir;
    }

    public void setJavaTmplDir(String tempDir) {
        sJavaTmplDir = tempDir;
    }

    public String getJspTempDir() {
        return sJspTmplDir;
    }

    public void setJspTmplDir(String tempDir) {
        sJspTmplDir = tempDir;
    }

    public void setJavaSourcePath(String javaSourcePath) {
        sJavaSourcePath = javaSourcePath;
    }

    public void setJspSourcePath(String jspSourcePath) {
        sJspSourcePath = jspSourcePath;
    }

    public void setClassPath(String classPath) {
        sClassPath = classPath;
    }
    
    /**
     * @author codefan
     * @param htmFileName
     *            hibernate 文件名
     */
    public void createCodeSuite(HibernateMapInfo hbmMD) {
        String sBaseDir = sJavaSourcePath + "/" + sClassPath;
        // String sTempDir = sProjectPath+"/template";
        String sJspDir = sJspSourcePath;// web/page/"+sAppPath;

        FileSystemOpt.createDirect(sJspDir);

        m_varTrans.setHbmMetadata(hbmMD);
        /**
         * 创建java代码
         */
        // create pojo
        if (cConfig.isCreatePojo()) {
            System.out.println("根据  " + sJavaTmplDir + "/PojoTemp.java" + "生成 " + sBaseDir + "/po/"
                    + m_varTrans.getClassSimpleName() + ".java");
            FileSystemOpt.createDirect(sBaseDir + "/po");
            createCode(sJavaTmplDir + "/PojoTemp.java", sBaseDir + "/po/" + m_varTrans.getClassSimpleName() + ".java");
            if (m_varTrans.hasCompositeId()) {
                createCode(sJavaTmplDir + "/PojoIdTemp.java", sBaseDir + "/po/" + m_varTrans.getClassSimpleName()
                        + "Id.java");
            }
        }
        // create dao interface
        if (cConfig.isCreateDao()) {
            // create dao impl
            if (m_varTrans.getHandleCfg().isCreateDaoImpl()) {
                System.out.println("根据  " + sJavaTmplDir + "/DaoTemp.java" + "生成 " + sBaseDir + "/dao/"
                        + m_varTrans.getClassSimpleName() + "Dao.java");

                FileSystemOpt.createDirect(sBaseDir + "/dao");
                createCode(sJavaTmplDir + "/DaoTemp.java", sBaseDir + "/dao/" + m_varTrans.getClassSimpleName()
                        + "Dao.java");

                System.out.println("根据  " + sJavaTmplDir + "/DaoImplTemp.java" + "生成 " + sBaseDir + "/dao/impl/"
                        + m_varTrans.getClassSimpleName() + "DaoImpl.java");

                FileSystemOpt.createDirect(sBaseDir + "/dao/impl");
                createCode(sJavaTmplDir + "/DaoImplTemp.java",
                        sBaseDir + "/dao/impl/" + m_varTrans.getClassSimpleName() + "DaoImpl.java");

            } else {
                System.out.println("根据  " + sJavaTmplDir + "/DaoImplTemp.java" + "生成 " + sBaseDir + "/dao/"
                        + m_varTrans.getClassSimpleName() + "Dao.java");

                FileSystemOpt.createDirect(sBaseDir + "/dao");
                createCode(sJavaTmplDir + "/DaoImplTemp.java", sBaseDir + "/dao/" + m_varTrans.getClassSimpleName()
                        + "Dao.java");
            }
        }
        // create manager interface
        if (cConfig.isCreateManager()) {
            // create manager impl
            if (m_varTrans.getHandleCfg().isCreateManagerImpl()) {
                System.out.println("根据  " + sJavaTmplDir + "/ManagerTemp.java" + "生成 " + sBaseDir + "/service/"
                        + m_varTrans.getClassSimpleName() + "Manager.java");

                FileSystemOpt.createDirect(sBaseDir + "/service");
                createCode(sJavaTmplDir + "/ManagerTemp.java", sBaseDir + "/service/" + m_varTrans.getClassSimpleName()
                        + "Manager.java");

                System.out.println("根据  " + sJavaTmplDir + "/ManagerImplTemp.java" + "生成 " + sBaseDir
                        + "/service/impl/" + m_varTrans.getClassSimpleName() + "ManagerImpl.java");

                FileSystemOpt.createDirect(sBaseDir + "/service/impl");
                createCode(sJavaTmplDir + "/ManagerImplTemp.java",
                        sBaseDir + "/service/impl/" + m_varTrans.getClassSimpleName() + "ManagerImpl.java");
            } else {
                System.out.println("根据  " + sJavaTmplDir + "/ManagerImplTemp.java" + "生成 " + sBaseDir + "/service/"
                        + m_varTrans.getClassSimpleName() + "Manager.java");

                FileSystemOpt.createDirect(sBaseDir + "/service");
                createCode(sJavaTmplDir + "/ManagerImplTemp.java",
                        sBaseDir + "/service/" + m_varTrans.getClassSimpleName() + "Manager.java");
            }
        }
        // create action
        if (cConfig.isCreateAction()) {
            System.out.println("根据  " + sJavaTmplDir + "/ActionTemp.java" + "生成 " + sBaseDir + "/action/"
                    + m_varTrans.getClassSimpleName() + "Action.java");

            FileSystemOpt.createDirect(sBaseDir + "/action");
            createCode(sJavaTmplDir + "/ActionTemp.java", sBaseDir + "/action/" + m_varTrans.getClassSimpleName()
                    + "Action.java");
        }
        /**
         * 创建jsp代码
         */
        if (cConfig.isCreateJsp()) {
            FileSystemOpt.createDirect(sJspDir);

            System.out.println("根据  " + sJspTmplDir + "/ListTemp.java" + "生成 " + sJspDir + '/'
                    + m_varTrans.getEntityName() + "List.jsp");
            createCode(sJspTmplDir + "/ListTemp.jsp", sJspDir + '/' + m_varTrans.getEntityName() + "List.jsp");

            System.out.println("根据  " + sJspTmplDir + "/FormTemp.java" + "生成 " + sJspDir + '/'
                    + m_varTrans.getEntityName() + "Form.jsp");
            createCode(sJspTmplDir + "/FormTemp.jsp", sJspDir + '/' + m_varTrans.getEntityName() + "Form.jsp");

            System.out.println("根据  " + sJspTmplDir + "/ViewTemp.java" + "生成 " + sJspDir + '/'
                    + m_varTrans.getEntityName() + "View.jsp");
            createCode(sJspTmplDir + "/ViewTemp.jsp", sJspDir + '/' + m_varTrans.getEntityName() + "View.jsp");
        }

    }

}
