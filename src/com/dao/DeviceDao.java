package com.dao;

import java.sql.ResultSet;

public interface DeviceDao {
	public void insert(int type, String ip, int port, String username, String password) throws Exception;
	
	public void update(int id,int type, String ip, int port, String username, String password) throws Exception;

	public ResultSet select() throws Exception;

	public ResultSet find(String field, String data);

	
}
