package com.dao;

import java.sql.ResultSet;

public interface DeviceDao {
	public void insert(String ip, int port, String username, String password) throws Exception;

	public ResultSet select() throws Exception;
}
