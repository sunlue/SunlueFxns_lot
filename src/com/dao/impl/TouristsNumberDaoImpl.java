package com.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.dao.TouristsNumberDao;
import com.util.Db;
import com.util.Log;

public class TouristsNumberDaoImpl implements TouristsNumberDao {

	private Db db = new Db();

	@Override
	public int insert(int number, int in, int out, String time) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("number", String.valueOf(number));
		data.put("in", String.valueOf(in));
		data.put("out", String.valueOf(out));
		data.put("time", time.trim());
		int result = db.name("tourists_number").data(data).insert();
		Log.write("操作成功");
		return result;
	}

}
