package com.centit.support.scaffold;

import com.centit.support.database.metadata.PdmReader;
import com.centit.support.database.metadata.SimpleTableInfo;

public class PDMHibernateReverseTest {

	public static void main(String[] args) 
	{
		//DBConn.loadHibernateConfig("E:/Study/Centit/BSDFW/web/WEB-INF/hibernate.cfg.xml");
		String sJavaSourcetDir = "D:/centit/j2ee/framework-base/src/main/java";
		String appName = "sampleflow";
		String sClassPath = "com/centit/workflow/sample/po";
		String sConfigPath = "D:/centit/j2ee/framework-base/src/main/resources/sampleflow";
		
		String sPackageName = sClassPath.replace('/', '.');
		String sTableNames = "WF_NODE";
		String sPdmFile = "D:/Centit/J2EE/framework-base/Document/设计文档/BS开发框架.pdm";

		String sTableSchema = "BSDFW2";
		String [] sTables = sTableNames.split(",");
		
		PdmReader reader =new PdmReader();
		reader.setDBSchema(sTableSchema);
		if(!reader.loadPdmFile(sPdmFile )){
			System.out.println("读取"+sPdmFile+"出错！");
			return;
		}
		
		for(int i=0;i<sTables.length;i++ ){
			String sTableName = sTables[i];//.toUpperCase();
			SimpleTableInfo tm = reader.getTableMetadata(sTableName);
			if(tm !=null){
				tm.setPackageName(sPackageName);
				tm.saveHibernateMappingFile(sJavaSourcetDir+"/"+sClassPath+"/"+tm.getClassName()+".hbm.xml");
				System.out.println("转换"+sTableName+"已完成！");
				String sPropFileName= sConfigPath+"/" +
						appName.substring(0,1).toUpperCase()+appName.substring(1) + 
						"Resource";//_zh_CN.properties";

				tm.addResource(sPropFileName);
				System.out.println("添加"+sTableName+"资源信息完成！");

			}else
				System.out.println("没有找到表："+sTableName+" ！");

		}
	}

}
