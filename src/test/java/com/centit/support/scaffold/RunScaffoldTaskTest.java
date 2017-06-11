package com.centit.support.scaffold;

import java.sql.SQLException;

public class RunScaffoldTaskTest {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		//D:\Projects\framework\centit-scaffold\src\main\resources\scaffoldtask2.xml
		RunScaffoldTask2.runTask("D:\\Projects\\framework\\centit-scaffold\\src\\main\\resources\\scaffoldtask2.xml");
		System.out.println("Done!");
	}

}
