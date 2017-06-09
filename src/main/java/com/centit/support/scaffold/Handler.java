package com.centit.support.scaffold;

import java.io.File;
import java.util.List;

import com.centit.support.database.metadata.HibernateMapInfo;
import com.centit.support.file.FileSystemOpt;


/** 
 *  根据 hibernate xml元数据文件生成 相关的类 和 jsp
*/

public class Handler {

	public static void runTask(TaskDesc task ) {
		if(! task.isRunCreateSourceHandler())
			return ;
		ScaffoldTranslate varTrans = new ScaffoldTranslate();

		Config cfg = new Config();
		cfg.setConfig( task.getInstruction() );
	
		varTrans.setHandleCfg(cfg);
						
		varTrans.setAppName(task.getAppName());
				
		StrutsCodeHandler codehandler = new StrutsCodeHandler();
		codehandler.setConfig(cfg);
		codehandler.setScaffoldTranslate(varTrans);
		codehandler.setJavaSourcePath(task.getProjDir()+'/'+ task.getSrcDir());
		codehandler.setJspSourcePath(task.getProjDir()+'/'+task.getJspDir()+"/page/"+task.getAppName());
		
		codehandler.setClassPath(task.getAppPackagePath());
		
		XmlConfigHanlder confighandler = new XmlConfigHanlder();
		confighandler.setScaffoldTranslate(varTrans);
		confighandler.setConfigPath( task.getProjDir()+'/'+task.getConfigDir());
		
		confighandler.setAppPath(task.getAppName() );

		confighandler.setConfig(cfg);
		
		String sHbmXmlFile  = task.getHbmfile();
		String sBaseDir = task.getProjDir()+'/'+ task.getSrcDir() +'/'+task.getAppPackagePath();

		codehandler.setJavaTmplDir( task.getJavatemplate());
		codehandler.setJspTmplDir( task.getJsptemplate());
		
		System.out.println(sBaseDir);
		if( "all".equalsIgnoreCase(sHbmXmlFile)){
			List<File> files = FileSystemOpt.findFilesByExt(sBaseDir+"/po", ".hbm.xml");
			for(File f:files){   
				System.out.println("正在转换文件: " +f.getName());
				HibernateMapInfo hbmMD = new HibernateMapInfo();
				hbmMD.loadHibernateMetadata( sBaseDir+ "/po/" , f.getName());		
				codehandler.createCodeSuite(hbmMD);   
				if( cfg.isCreateXmlConfig())
					confighandler.createConfigSuite(hbmMD);
			}   
		}else{
			String [] sFiles = sHbmXmlFile.split(",");
			for(String sFilename : sFiles){
				System.out.println("正在转换文件: "+sFilename);
				HibernateMapInfo hbmMD = new HibernateMapInfo();
				hbmMD.loadHibernateMetadata( sBaseDir + "/po/" , sFilename);		
				codehandler.createCodeSuite(hbmMD);   
				if( cfg.isCreateXmlConfig() )
					confighandler.createConfigSuite(hbmMD);
				}
		}		
	}

	/** 
	 *  根据 hibernate xml元数据文件生成 相关的类 和 jsp
	 *  @param args 项目路径名 分包名 类路径 是否需要服务接口 是否需要dao接口
	*/
	public static void main(String[] args) 
	{
		if( args.length < 9 ){
			System.out.println("缺少参数!");
			return;
		}
		ScaffoldTranslate varTrans = new ScaffoldTranslate();

		Config cfg = new Config();
		cfg.setConfig( args[5]);
	
		varTrans.setHandleCfg(cfg);
				
		varTrans.setAppName(args[3]);
				
		StrutsCodeHandler codehandler = new StrutsCodeHandler();
		codehandler.setConfig(cfg);
		codehandler.setScaffoldTranslate(varTrans);
		codehandler.setJavaSourcePath(args[0]);
		codehandler.setJspSourcePath(args[1]);
		
		codehandler.setClassPath(args[4]);
		
		XmlConfigHanlder confighandler = new XmlConfigHanlder();
		confighandler.setScaffoldTranslate(varTrans);
		confighandler.setConfigPath(args[2]);
		
		confighandler.setAppPath(args[3]);

		confighandler.setConfig(cfg);
		
		String sHbmXmlFile  = args[6];
		String sBaseDir = args[0]+'/'+args[4];

		codehandler.setJavaTmplDir(args[7]);
		codehandler.setJspTmplDir(args[8]);
		
		System.out.println(sBaseDir);
		if( "all".equalsIgnoreCase(sHbmXmlFile)){
			List<File> files = FileSystemOpt.findFilesByExt(sBaseDir+"/po", ".hbm.xml");
			for(File f:files){   
				System.out.println("正在转换文件: " +f.getName());
				HibernateMapInfo hbmMD = new HibernateMapInfo();
				hbmMD.loadHibernateMetadata( sBaseDir+ "/po/" , f.getName());		
				codehandler.createCodeSuite(hbmMD);   
				if( cfg.isCreateXmlConfig())
					confighandler.createConfigSuite(hbmMD);
			}   
		}else{
			String [] sFiles = sHbmXmlFile.split(",");
			for(String sFilename : sFiles){
				System.out.println("正在转换文件: "+sFilename);
				HibernateMapInfo hbmMD = new HibernateMapInfo();
				hbmMD.loadHibernateMetadata( sBaseDir + "/po/" , sFilename);		
				codehandler.createCodeSuite(hbmMD);   
				if( cfg.isCreateXmlConfig() )
					confighandler.createConfigSuite(hbmMD);
				}
		}
	}

}
