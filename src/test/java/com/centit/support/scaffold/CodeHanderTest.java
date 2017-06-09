package com.centit.support.scaffold;

import com.centit.support.database.metadata.HibernateMapInfo;

public class CodeHanderTest {

	public static void main(String[] args) 
	{
		ScaffoldTranslate varTrans = new ScaffoldTranslate();
		//varTrans.setManagerInterface(true);
		//varTrans.setDaoInterface(true);
		
		StrutsCodeHandler handler = new StrutsCodeHandler();
		handler.setScaffoldTranslate(varTrans);
		varTrans.setAppName("app");
		handler.setJavaSourcePath("D:/centit/j2ee/centit-scaffold/src/test/java");
		handler.setJspSourcePath("D:/centit/j2ee/centit-scaffold/src/test/resources");
		handler.setJavaTmplDir("D:/centit/j2ee/centit-scaffold/src/main/resources/struts2template");
		handler.setJspTmplDir("D:/centit/j2ee/centit-scaffold/src/main/resources/struts2template/jsp");

		handler.setClassPath("com/centit/app");
		
		HibernateMapInfo hbmMD = new HibernateMapInfo();
		hbmMD.loadHibernateMetadata("/com/centit/app/po/","Employee.hbm.xml");
		handler.createCodeSuite(hbmMD);   
		
		System.out.println("生成代码文件完成 ！");
	}


}
