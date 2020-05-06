package com.entity;

import java.util.HashMap;
import java.util.Map;
/**
 * 环境监测实体类
 * @author xiebing
 */
public class VideoMonitor {
	public int id;
	public int type;
	public String ip;
	public int port;
	public String username;
	public String password;
	private static Map<String, Map<String, Object>> DeviceHikvisionRealPlay= new HashMap<String, Map<String, Object>>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static Map<String, Object> getDeviceHikvisionRealPlay(String key) {
		return DeviceHikvisionRealPlay.get(key);
	}
	
	public static void setDeviceHikvisionRealPlay(String key,Map<String, Object> value) {
		DeviceHikvisionRealPlay.put(key, value);
	}

}
