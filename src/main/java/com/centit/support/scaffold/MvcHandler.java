package com.centit.support.scaffold;

import com.centit.support.database.metadata.HibernateMapInfo;
import com.centit.support.database.metadata.PdmReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;


/** 
 *  根据 hibernate xml元数据文件生成 相关的类 和 jsp
*/

public class MvcHandler {

	public static void runTask(TaskDesc task ) {
		if(! task.isRunCreateSourceMvcHandler())
			return ;
		ScaffoldTranslate varTrans = new ScaffoldTranslate();

		Config cfg = new Config();
		cfg.setConfig( task.getInstruction() );
	
		varTrans.setHandleCfg(cfg);
						
		varTrans.setAppName(task.getAppName());
			
		MvcCodeHandler codehandler = new MvcCodeHandler();
		codehandler.setConfig(cfg);
		codehandler.setScaffoldTranslate(varTrans);
		codehandler.setJavaSourcePath( task.getProjDir()+'/'+ task.getSrcDir() );
		codehandler.setJspSourcePath( task.getProjDir()+'/'+task.getJspDir() );
		
		codehandler.setClassPath(task.getAppPackagePath());
		
		String sMvcTables  = task.getMvcTables();
		String sBaseDir = task.getProjDir()+'/'+ task.getSrcDir() +'/'+task.getAppPackagePath();

		codehandler.setJavaTmplDir( task.getJavatemplate());
		codehandler.setJspTmplDir( task.getJsptemplate());
		
		System.out.println(sBaseDir);
	
		String sPdmFile = task.getMvcPdmfile();
        String sTableSchema = task.getDbschema();
        PdmReader reader = new PdmReader();
        reader.setDBSchema(sTableSchema);
        if (!reader.loadPdmFile(sPdmFile)) {
            System.out.println("读取" + sPdmFile + "出错！");
            return;
        }
        
        String sClassPath = task.getAppPackagePath() + "/po";

        String sPackageName = sClassPath.replace('/', '.');
        if(StringUtils.isBlank(sMvcTables) || "all".equalsIgnoreCase(sMvcTables)){
			List<Pair<String,String>> tables = reader.getAllTableCode();
			for (Pair<String,String> table : tables) {
				System.out.println("正在转换表: " + table.getLeft());
				HibernateMapInfo hbmMD = reader.getHibernateMetadata(
						table.getLeft().trim(), sPackageName);

				codehandler.createCodeSuite(hbmMD, task.isForce());
			}
		}else {
			String[] sTables = sMvcTables.split(",");

			for (String sTabName : sTables) {
				System.out.println("正在转换表: " + sTabName);
				HibernateMapInfo hbmMD = reader.getHibernateMetadata(sTabName.trim(), sPackageName);

				codehandler.createCodeSuite(hbmMD, task.isForce());
			}
		}
		
		System.out.println("所有工作完成！");
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
		cfg.setConfig( args[6]);
	
		varTrans.setHandleCfg(cfg);
				
		varTrans.setAppName(args[3]);
				
		MvcCodeHandler codehandler = new MvcCodeHandler();
		codehandler.setConfig(cfg);
		codehandler.setScaffoldTranslate(varTrans);
		codehandler.setJavaSourcePath(args[0]);
		codehandler.setJspSourcePath(args[1]);
		
		codehandler.setClassPath(args[5]);

		codehandler.setJavaTmplDir(args[7]);
		codehandler.setJspTmplDir(args[8]);
	
        PdmReader reader = new PdmReader();
        reader.setDBSchema(args[3]);
        if (!reader.loadPdmFile(args[2])) {
            System.out.println("读取" + args[2] + "出错！");
            return;
        }
        
        String sClassPath = args[5] + "/po";

        String sPackageName = sClassPath.replace('/', '.');
        
		String [] sTables = args[4].split(",");
		for(String sTabName : sTables){
			System.out.println("正在转换表: "+sTabName);
			HibernateMapInfo hbmMD = reader.getHibernateMetadata(sTabName,sPackageName);
			codehandler.createCodeSuite(hbmMD,true); 
		}
	}

}
