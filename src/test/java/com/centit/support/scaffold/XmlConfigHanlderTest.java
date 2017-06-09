package com.centit.support.scaffold;

import com.centit.support.database.metadata.HibernateMapInfo;

public class XmlConfigHanlderTest {

	public static void main(String[] args) 
	{
		Config cfg = new Config();
		cfg.setConfig("1,1,0,1,1,1,1,1,1" );

		XmlConfigHanlder handler = new XmlConfigHanlder();
		handler.setConfig(cfg);
		ScaffoldTranslate varTrans = new ScaffoldTranslate();
		varTrans.setHandleCfg(cfg);
		//varTrans.setManagerInterface(true);
		//varTrans.setDaoInterface(true);
		handler.setScaffoldTranslate(varTrans);
		varTrans.setAppName("support");
		handler.setConfigPath("E:/Centit/j2ee/bsdfw2/com/centit/support/config");
		handler.setAppPath("support");
	
		HibernateMapInfo hbmMD = new HibernateMapInfo();
		hbmMD.loadHibernateMetadata("E:/Centit/j2ee/bsdfw2/supportSrc/com/centit/support/po/","Employee.hbm.xml");
		handler.createConfigSuite(hbmMD); 
		
		System.out.println("生成配置文件完成 ！");
	}
	 
}
