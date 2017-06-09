package com.centit.support.scaffold;

import java.sql.SQLException;

import com.centit.support.database.DataSourceDescription;
import com.centit.support.database.DbcpConnect;
import com.centit.support.database.DbcpConnectPools;
import com.centit.support.database.metadata.PdmReader;
import com.centit.support.database.metadata.SimpleTableInfo;

public class MetadataHandler {

	public static void runTask(TaskDesc task ) throws SQLException {
		if(! task.isRunMetadataHandler())
			return ;
		
		String sPdmFile = task.getMetaPdmfile();

		String sTableNames = task.getMetaTables();
		String [] sTables=null;
		
	
		
		DbcpConnect dbc= DbcpConnectPools.getDbcpConnect(
				task.getDataSourceDesc());
		
		MetadataPersistent db = new MetadataPersistent();
		db.setDBConfig(dbc);
		
		PdmReader reader =new PdmReader();

		if(!reader.loadPdmFile(sPdmFile )){
			System.out.println("读取"+sPdmFile+"出错！");
			return;
		}
		if(sTableNames.equalsIgnoreCase("all"))
			sTables = reader.getAllTableCode().toArray(new String[0]);
		else
			sTables = sTableNames.split(",");
		
		for(int i=0;i<sTables.length;i++ ){
			String sTableName = sTables[i];//.toUpperCase();
			SimpleTableInfo tm = reader.getTableMetadata(sTableName);
			if(tm==null){
				System.out.println("读取"+sTableName+"元数据失败！");
				continue;
			}
			db.saveTableMetadata(tm);
			System.out.println("保存"+sTableName+"元数据已完成！");
		}
	}
	/**
	 * @param args
	 * @throws SQLException 
	 */
	
	
	public static void main(String[] args) throws SQLException {
		if( args.length < 3 ){
			System.out.println("缺少参数！");
			return;
		}
		
		String sPdmFile = args[0];

		String sTableNames = args[1];
		String [] sTables=null;
		
		String sHibernateConfigFile = args[2];
		
		String sDbBeanName = "dataSource";
		if( args.length > 3 ) 
			sDbBeanName = args[3];
		
		DataSourceDescription dataSource=new DataSourceDescription();
		dataSource.loadHibernateConfig(sHibernateConfigFile,sDbBeanName);
		
		DbcpConnect dbc= DbcpConnectPools.getDbcpConnect(dataSource);
		
		MetadataPersistent db = new MetadataPersistent();
		db.setDBConfig(dbc);
		
		PdmReader reader =new PdmReader();

		if(!reader.loadPdmFile(sPdmFile )){
			System.out.println("读取"+sPdmFile+"出错！");
			return;
		}
		if(sTableNames.equalsIgnoreCase("all"))
			sTables = reader.getAllTableCode().toArray(new String[0]);
		else
			sTables = sTableNames.split(",");
		
		for(int i=0;i<sTables.length;i++ ){
			String sTableName = sTables[i];//.toUpperCase();
			SimpleTableInfo tm = reader.getTableMetadata(sTableName);
			if(tm==null){
				System.out.println("读取"+sTableName+"元数据失败！");
				continue;
			}
			db.saveTableMetadata(tm);
			System.out.println("保存"+sTableName+"元数据已完成！");
		}
	}

}
