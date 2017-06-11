package com.centit.support.scaffold;

import java.sql.SQLException;

import com.centit.support.database.DataSourceDescription;
import com.centit.support.database.DbcpConnect;
import com.centit.support.database.DbcpConnectPools;
import com.centit.support.database.metadata.DatabaseMetadata;
import com.centit.support.database.metadata.IbmDb2Metadata;
import com.centit.support.database.metadata.MsSqlSvrMetadata;
import com.centit.support.database.metadata.OracleMetadata;
import com.centit.support.database.metadata.SimpleTableInfo;

public class HibernateReverseTest {

	public static void main(String[] args) throws SQLException 
	{
		//DBConn.loadHibernateConfig("E:/Study/Centit/BSDFW/web/WEB-INF/hibernate.cfg.xml");
		String sJavaSourceDir = "D:/centit/j2ee/centit-scaffold/src/test/java";
		String sClassPath = "com/centit/support/po";
		String sPackageName = sClassPath.replace('/', '.');
		String sTableNames = "Employee";
		String sHibernateConfigFile = "D:/centit/j2ee/centit-demo/src/main/webapp/WEB-INF/hibernate.cfg.xml";
		String [] sTables = sTableNames.split(",");
		String sDbBeanName = "sqlDataSource";

		DataSourceDescription dataSource=new DataSourceDescription();
		dataSource.loadHibernateConfig(sHibernateConfigFile,sDbBeanName);
		
		DbcpConnect dbc= DbcpConnectPools.getDbcpConnect(dataSource);
		
		DatabaseMetadata db = null;
		if( dataSource.getConnUrl().indexOf("oracle")>=0)
			db = new OracleMetadata();
		else if( dataSource.getConnUrl().indexOf("db2")>=0)
			db = new IbmDb2Metadata();
		else if( dataSource.getConnUrl().indexOf("sqlserver")>=0)
			db = new MsSqlSvrMetadata();
		else {
			System.out.println("无法辨认数据库类型！");
			return ;
		}		
		
		db.setDBConfig(dbc);
		try {
			db.setDBSchema(dbc.getSchema());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<sTables.length;i++ ){
			String sTableName = sTables[i].toUpperCase();
			SimpleTableInfo tm = db.getTableMetadata(sTableName);
			tm.setPackageName(sPackageName);
			
			tm.saveHibernateMappingFile(sJavaSourceDir+"/"+sClassPath+"/"+tm.getClassName()+".hbm.xml");
			System.out.println("转换"+sTableName+"已完成！");
		}
	}

}
