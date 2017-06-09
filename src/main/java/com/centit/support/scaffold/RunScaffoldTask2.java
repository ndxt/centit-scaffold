package com.centit.support.scaffold;

public class RunScaffoldTask2 {

	public static void runTask(String taskConfigFile){
		TaskDesc task = new TaskDesc();
		if( task.loadTaskFromConfig(taskConfigFile) <=0){
			System.out.println("任务配置文件不正确！");
			return;
		}
		MvcHandler.runTask(task);		
		//VerificationHibernateDataType.runTask(task);
	}
	/**
	 * @param args 任务配置文件
	 */
	public static void main(String[] args) {
		if( args.length < 1 ){
			runTask("D:/temp/script/scaffoldtask2.xml");
			System.out.println("缺少参数:任务配置文件!");
			return;
		}
		runTask(args[0]);
	}

}
