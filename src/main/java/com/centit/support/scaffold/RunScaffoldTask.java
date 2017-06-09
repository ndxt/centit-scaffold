package com.centit.support.scaffold;

import java.sql.SQLException;

public class RunScaffoldTask {

	public static void runTask(String taskConfigFile) throws SQLException{
		TaskDesc task = new TaskDesc();
		if( task.loadTaskFromConfig(taskConfigFile) <=0){
			System.out.println("任务配置文件不正确！");
			return;
		}
		PDMHibernateReverse.runTask(task);
		HibernateReverse.runTask(task);
		MetadataHandler.runTask(task);
		Handler.runTask(task);
		
		//VerificationHibernateDataType.runTask(task);
	}
	/**
	 * @param args 任务配置文件
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		if( args.length < 1 ){
			System.out.println("缺少参数:任务配置文件!");
			return;
		}
		runTask(args[0]);
		//runTask("D:/Projects/j2eews/centit-scaffold/src/main/resources/scaffoldtask.xml");
	}

}
