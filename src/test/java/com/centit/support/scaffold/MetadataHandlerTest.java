package com.centit.support.scaffold;

import java.sql.SQLException;

import com.centit.support.database.DataSourceDescription;
import com.centit.support.database.DbcpConnect;
import com.centit.support.database.DbcpConnectPools;
import com.centit.support.database.metadata.PdmReader;
import com.centit.support.database.metadata.SimpleTableInfo;

public class MetadataHandlerTest {
	
	public static void main(String[] args) throws SQLException 
	{
		String sHibernateConfigFile = "E:/Centit/J2EE/BSDFW/web/WEB-INF/hibernate.cfg.xml";
		String sDbBeanName = "dataSource";


		DataSourceDescription dataSource=new DataSourceDescription();
		dataSource.loadHibernateConfig(sHibernateConfigFile,sDbBeanName);
		
		DbcpConnect dbc= DbcpConnectPools.getDbcpConnect(dataSource);
		
		MetadataPersistent db = new MetadataPersistent();
		db.setDBConfig(dbc);
		
		PdmReader reader =new PdmReader();

		if(!reader.loadPdmFile("E:/temp/BS.xml")){
			System.out.println("读取 E:/temp/BS.xml 出错！");
			return;
		}
		String [] sTables = {"F_ROLEINFO"};
		
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
