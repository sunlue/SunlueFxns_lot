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

	private int type;
	private String ip;
	private int port;
	private String username;
	private String password;

	public Device() {

	}

	public Device(int type, String ip, String port, String username, String password) throws Exception {
		if ("".equals(String.valueOf(type))) {
			throw new Exception("请选择设备类型");
		} else if (ip.isEmpty()) {
			throw new Exception("请输入IP地址");
		} else if (port.isEmpty()) {
			throw new Exception("请输入端口");
		} else if (username.isEmpty()) {
			throw new Exception("请输入用户名");
		} else if (password.isEmpty()) {
			throw new Exception("请输入密码");
		}
		this.type = type;
		this.ip = ip;
		this.port = Integer.valueOf(port);
		this.username = username;
		this.password = password;
	}

	public void insert() throws Exception {
		dao.insert(type, ip, port, username, password);
	}

	public ArrayList<JSONObject> select() throws Exception {
		ResultSet rSet = dao.select();
		ArrayList<JSONObject> rData = new ArrayList<JSONObject>();
		try {
			while (rSet.next()) {
				JSONObject result = new JSONObject();
				result.put("type", rSet.getInt("type"));
				result.put("id", rSet.getInt("id"));
				result.put("ip", rSet.getString("ip"));
				result.put("port", rSet.getString("port"));
				result.put("username", rSet.getString("username"));
				result.put("password", rSet.getString("password"));
				rData.add(result);
			}
		} catch (SQLException e) {
			throw new Exception("数据读取错误");
		}
		return rData;
	}

	public JSONObject find(String field,String data) throws Exception {
		ResultSet rSet = dao.find(field,data);
		JSONObject result = new JSONObject();
		try {
			while (rSet.next()) {
				result.put("type", rSet.getInt("type"));
				result.put("id", rSet.getInt("id"));
				result.put("ip", rSet.getString("ip"));
				result.put("port", rSet.getString("port"));
				result.put("username", rSet.getString("username"));
				result.put("password", rSet.getString("password"));
			}
		} catch (SQLException e) {
			throw new Exception("数据读取错误");
		}
		return result;
	}
}


