package com.dao;

import java.sql.ResultSet;

/**
 * 设备管理
 * @author xiebing
 */
public interface DeviceDao {
	/**
	 * 添加设备数据
	 * @param type 设备类型
	 * @param ip 设备地址
	 * @param port 设备端口
	 * @param username 设备账号
	 * @param password 设备密码
	 * @throws Exception
	 */
	public void insert(int type, String ip, int port, String username, String password) throws Exception;

	/**
	 * 修改设备数据
	 * @param id 设备id
	 * @param type 设备类型
	 * @param ip 设备地址
	 * @param port 设备端口
	 * @param username 设备账号
	 * @param password 设备密码
	 * @throws Exception
	 */
	public void update(int id,int type, String ip, int port, String username, String password) throws Exception;

	/**
	 * 获取设备信息
	 * @return
	 * @throws Exception
	 */
	public ResultSet select() throws Exception;

	/**
	 * 获取单个设备信息
	 * @param field
	 * @param data
	 * @return
	 */
	public ResultSet find(String field, String data);

	
}
