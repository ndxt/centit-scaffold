package com.centit.support.scaffold;

import java.sql.SQLException;

public class RunScaffoldTaskTest {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		RunScaffoldTask.runTask("D:\\Projects\\j2eews\\centit-scaffold\\src\\main\\resources\\scaffoldtask.xml");
	}

}
