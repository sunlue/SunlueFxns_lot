package com.service;

import com.dao.Factory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.dao.EnvMonitorDao;
import com.dao.impl.EnvMonitorDaoImpl;
import com.server.Client;
import com.view.hardware.page.EnvMonitorView;
/**
 * 套接字服务端环境监测仪数据处理类
 * @author xiebing
 */
public class EnvMonitorService {
	private EnvMonitorDao dao = (EnvMonitorDao) Factory.dao(EnvMonitorDaoImpl.class.getName());

	private Client client;
	private byte[] data;
	private int length;

	public EnvMonitorService(Client client, byte[] data, int length) {
		this.client = client;
		this.length = length;
		this.data = data;
	}

	public void handle() throws Exception {
		String string = new String(data, 0, length);
		EnvMonitorView.insertMsg("收到" + client.getName() + ":" + string);
		boolean flag = true;
		for (int i = 0; i < string.length(); i++) {
			char cc = string.charAt(i);
			if (cc == '0' || cc == '1' || cc == '2' || cc == '3' || cc == '4' || cc == '5' || cc == '6' || cc == '7'
					|| cc == '8' || cc == '9' || cc == 'A' || cc == 'B' || cc == 'C' || cc == 'D' || cc == 'E'
					|| cc == 'F' || cc == 'a' || cc == 'b' || cc == 'c' || cc == 'd' || cc == 'e' || cc == 'f') {
				continue;
			} else {
				flag = false;
				break;
			}
		}
		if (flag == false || string == "") {
			EnvMonitorView.insertMsg("[error]" + client.getName() + ":【数据不是有效16进制数据】" + string);
			throw new Exception("数据不是有效16进制数据");
		}

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

		String wendu = list.get(0).toString();
		String shidu = list.get(1).toString();
		String yuliang = list.get(2).toString();
		String fengxiang = list.get(3).toString();
		String fengsu = list.get(4).toString();
		String co = list.get(8).toString();
		String pm25 = list.get(11).toString();
		String pm10 = list.get(12).toString();
		String qiya = list.get(14).toString();
		String fulizi = list.get(15).toString();
		String addTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		int result = dao.insert(wendu, shidu, yuliang, fengxiang, fengsu, co, pm25, pm10, qiya, fulizi, addTime,string);
		EnvMonitorView.insertMsg("成功处理数据"+result+"条");
	}

}
