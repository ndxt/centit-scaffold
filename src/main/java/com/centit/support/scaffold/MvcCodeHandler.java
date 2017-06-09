package com.centit.support.scaffold;

import java.io.File;

import com.centit.support.algorithm.StringBaseOpt;
import com.centit.support.database.metadata.HibernateMapInfo;
import com.centit.support.file.FileSystemOpt;

/**
 * 根据 hibernate xml元数据文件生成 相关的类 和 jsp
 */
public class MvcCodeHandler  extends CodeHandler {

    private String sJavaSourcePath;
    private String sJspSourcePath;

    private String sClassPath;
    private String sJavaTmplDir, sJspTmplDir;;
    private Config cConfig;

    public void setConfig(Config cg) {
        cConfig = cg;
    }
    
    public Config getConfig() {
        return cConfig ;
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

    
    private String transFileName(String filename){
    	String fn = filename;
    	if(filename.startsWith("_[CID]")){
    		if( m_varTrans.isCombineID())
    			fn = filename.substring(6);
    		else
    			return "";
    	}
    	return fn.replace("[po]",m_varTrans.getClassSimpleName().toLowerCase() )
    			.replace("[Po]",m_varTrans.getClassSimpleName())
    			.replace("[appname]", m_varTrans.getVarValue("appname"));
    }
    /**
     * @author codefan
     * @param tempFile
     *            模板文件路径名
     * @param destFile
     *            目标文件路劲名 如果文件已存在 就返回 不能覆盖
     */
    public void createCodeDir(String tempFileDir, String destFileDir,boolean force) {
    	
    	System.out.println("正在转换文件夹: "+tempFileDir);
    	
    	FileSystemOpt.createDirect(destFileDir); 
    	
    	File dirFile = new File(tempFileDir);
    	File[] fileArray = dirFile.listFiles();
		if (null == fileArray) {
			return;
		}
		for (int i = 0; i < fileArray.length; i++) {
			// 如果是个目录
			if (fileArray[i].isFile()) {
				String fn = fileArray[i].getName();
				String dfn = transFileName(fn);
				if(!StringBaseOpt.isNvl(dfn))
					createCode(tempFileDir+"/"+fn,destFileDir +"/"+dfn,force);
			}else if(fileArray[i].isDirectory()){
				String fn = fileArray[i].getName();
				String dfn = transFileName(fn);	
				if(!StringBaseOpt.isNvl(dfn))
					createCodeDir(tempFileDir+"/"+fn,destFileDir +"/"+dfn,force);
			}
		}
		
    }
    
    /**
     * @author codefan
     * @param htmFileName
     *            hibernate 文件名
     */
    public void createCodeSuite(HibernateMapInfo hbmMD,boolean force) {
        String sBaseDir = sJavaSourcePath + "/" + sClassPath;
        // String sTempDir = sProjectPath+"/template";
        String sJspDir = sJspSourcePath;// web/page/"+sAppPath;

        FileSystemOpt.createDirect(sJspDir);

        m_varTrans.setHbmMetadata(hbmMD);
      
        /**
         * 创建java代码
         */
        createCodeDir(sJavaTmplDir, sBaseDir,force );
        
        /**
         * 创建jsp代码
         */
        createCodeDir(sJspTmplDir, sJspDir,force );  
    }

}
