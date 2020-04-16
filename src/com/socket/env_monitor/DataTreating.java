package com.socket.env_monitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataTreating {

	public SocketEntity treat(String string) {
		String s = string.replaceAll(" ", "").substring(42);
		ArrayList<Object> list = new ArrayList<Object>();
		int index = 0;
		do {
			String d = Integer.valueOf(s.substring(index * 4, index * 4 + 4), 16).toString();
			if (index == 0 || index == 1 || index == 14) {
				list.add(Float.parseFloat(d) / 10);
			} else {
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
			System.out.println("--------实体错误-------------");
			return null;
		}
		return entity;
	}

}
