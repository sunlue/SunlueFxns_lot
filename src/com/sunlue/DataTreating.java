package com.sunlue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataTreating {

	public SocketEntity treat(String string) {
		// 删除字符串中的空格
		String s = string.replaceAll(" ", "").substring(42);
		ArrayList<Object> list = new ArrayList<Object>();
		int index = 0;
		do {
			String d = Integer.valueOf(s.substring(index * 4, index * 4 + 4), 16).toString();
			if (index == 0 || index == 1 || index == 14) {// 转浮点数逻辑
				list.add(Float.parseFloat(d) / 10);
			} else {// 正常数据逻辑
				list.add(d);
			}
			index += 1;
		} while (index < 24);
		SocketEntity entity = new SocketEntity();
		try {
			entity.setWendu(list.get(0).toString());
			entity.setShidu(list.get(1).toString());
			entity.setYuliang((String) list.get(2));
			entity.setFengxiang(list.get(3).toString());
			entity.setFengsu(list.get(4).toString());
			entity.setCo(list.get(8).toString());
			entity.setPm25(list.get(11).toString());
			entity.setPm10(list.get(12).toString());
			entity.setQiya(list.get(14).toString());
			entity.setFulizi(list.get(15).toString());
			entity.setOriginal(string);
			entity.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		} catch (Exception e) {
			System.out.println("--------处理实体异常-------------");
			return null;
		}
		return entity;
	}

}
