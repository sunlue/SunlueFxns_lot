package com.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.dao.DeviceDao;
import com.dao.Factory;
import com.dao.impl.DeviceDaoImpl;

public class Device {

	private DeviceDao dao = (DeviceDao) Factory.dao(DeviceDaoImpl.class.getName());

	private String ip;
	private int port;
	private String username;
	private String password;

	public Device() {
	}

	public Device(String ip, String port, String username, String password) throws Exception {
		if (port.isEmpty()) {
			throw new Exception("请输入端口");
		}
		this.ip = ip;
		this.port = Integer.valueOf(port);
		this.username = username;
		this.password = password;
	}

	public Device(String ip, int port, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public void insert() throws Exception {
		if (ip.isEmpty()) {
			throw new Exception("请输入IP地址");
		} else if (username.isEmpty()) {
			throw new Exception("请输入用户名");
		} else if (password.isEmpty()) {
			throw new Exception("请输入密码");
		}
		dao.insert(ip, port, username, password);
	}

	public ArrayList<JSONObject> select() throws Exception {
		ResultSet rSet = dao.select();
		ArrayList<JSONObject> rData = new ArrayList<JSONObject>();
		try {
			while (rSet.next()) {
				JSONObject result = new JSONObject();
				result.put("id", rSet.getInt("id"));
				result.put("ip", rSet.getString("ip"));
				result.put("port", rSet.getString("port"));
				result.put("username", rSet.getInt("username"));
				result.put("password", rSet.getString("password"));
				rData.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("数据读取错误");
		}
		return rData;
	}
}














