package com.centit.support.scaffold;

import java.sql.SQLException;

public class RunScaffoldTaskTest {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		if( args.length < 1 ){
			RunScaffoldTask2.runTask(
					"D:\\centit-scaffold\\src\\main\\resources\\scaffoldtask2.xml");

			System.out.println("缺少参数:任务配置文件!");
			return;
		}
		RunScaffoldTask2.runTask(args[0]);
		System.out.println("Done!");
	}

}
