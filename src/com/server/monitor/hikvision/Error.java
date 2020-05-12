package com.server.monitor.hikvision;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author xiebing
 *
 */
public class Error {

	JSONObject hikvisionMsg = new JSONObject();

	public Error() {
		hikvisionMsg.put("0", "没有错误");
		hikvisionMsg.put("1", "用户名密码错误");
		hikvisionMsg.put("2", "通道权限不足");
		hikvisionMsg.put("3", "SDK未初始化");
		hikvisionMsg.put("4", "通道号错误");
		hikvisionMsg.put("5", "设备总的连接数超过最大");
		hikvisionMsg.put("6", "版本不匹配");
		hikvisionMsg.put("7", "连接设备失败。设备不在线或网络原因引起的连接超时等");
		hikvisionMsg.put("8", "向设备发送失败");
		hikvisionMsg.put("9", "从设备接收数据失败");
		hikvisionMsg.put("10", "从设备接收数据超时");
		hikvisionMsg.put("11", "传送的数据有误。发送给设备或者从设备接收到的数据错误");
		hikvisionMsg.put("12", "调用次序错误");
		hikvisionMsg.put("13", "无此权限。");
	}

	public String hikvision(int i) {
		return hikvisionMsg.get(String.valueOf(i)).toString();
	}
}
