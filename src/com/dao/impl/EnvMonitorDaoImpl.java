package com.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.dao.EnvMonitorDao;
import com.util.Log;

public class EnvMonitorDaoImpl extends DaoImpl implements EnvMonitorDao {

	@Override
	public int insert(String wendu, String shidu, String yuliang, String fengxiang, String fengsu, String co,
			String pm25, String pm10, String qiya, String fulizi, String addTime,String original) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("wendu", wendu);
		data.put("shidu", wendu);
		data.put("yuliang", yuliang);
		data.put("fengxiang", fengxiang);
		data.put("fengsu", fengsu);
		data.put("co", co);
		data.put("pm25", pm25);
		data.put("pm10", pm10);
		data.put("qiya", qiya);
		data.put("fulizi", fulizi);
		data.put("original", original);
		data.put("addTime", addTime.trim());
		int result = db.name("env_monitor").data(data).insert();
		Log.write("操作成功条数：" + result);
		return result;
	}
}
