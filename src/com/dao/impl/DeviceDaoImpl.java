package com.dao.impl;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.dao.DeviceDao;
import com.util.Log;

public class DeviceDaoImpl extends DaoImpl implements DeviceDao {

	@Override
	public void insert(String ip, int port, String username, String password) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("ip", ip);
		data.put("port", String.valueOf(port));
		data.put("username", username);
		data.put("password", password);
		int result = db.name("video_monitor").data(data).insert();
		Log.write("操作成功条数：" + result);
		if (result < 1) {
			throw new Exception("操作失败：" + result);
		}
	}

	@Override
	public ResultSet select() throws Exception {
		return db.name("video_monitor").select();
	}

}
