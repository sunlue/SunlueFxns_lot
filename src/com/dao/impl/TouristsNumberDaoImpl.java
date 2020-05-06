package com.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.dao.TouristsNumberDao;
import com.util.Log;
/**
 * 实现客流量统计接口
 * @author xiebing
 */
public class TouristsNumberDaoImpl extends DaoImpl implements TouristsNumberDao {
	@Override
	public int insert(int number, int in, int out, String time) {
		Map<String, String> data = new HashMap<String, String>(4);
		data.put("number", String.valueOf(number));
		data.put("in", String.valueOf(in));
		data.put("out", String.valueOf(out));
		data.put("time", time.trim());
		int result = db.name("tourists_number").data(data).insert();
		Log.write("操作成功条数：" + result);
		return result;
	}
}
