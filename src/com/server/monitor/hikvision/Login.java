package com.server.monitor.hikvision;

import java.util.HashMap;
import java.util.Map;

import com.server.monitor.hikvision.HCNetSDK.NET_DVR_DEVICEINFO_V30;
import com.sun.jna.NativeLong;

/**
 * 设备注册类
 * 
 * @author xiebing
 *
 */
public class Login {

	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

	public String ip;
	public Short port;
	public String username;
	public String password;

	public NativeLong UserID;
	public HCNetSDK.NET_DVR_DEVICEINFO_V30 m_DeviceInfo;

	public static Map<String, Map<String, Object>> DeviceLoginList = new HashMap<String, Map<String, Object>>();

	public NativeLong getUserID() {
		return UserID;
	}

	public HCNetSDK.NET_DVR_DEVICEINFO_V30 getM_DeviceInfo() {
		return m_DeviceInfo;
	}

	public Login(String ip, short port, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public void handle(LoginCallback callback) {
		m_DeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
		UserID = hCNetSDK.NET_DVR_Login_V30(ip, port, username, password, m_DeviceInfo);
		if (UserID.longValue() == -1) {
			callback.fail();
		} else {
			callback.success(m_DeviceInfo, UserID);
		}
	}

	public interface LoginCallback {

		void success(NET_DVR_DEVICEINFO_V30 m_DeviceInfo, NativeLong userID);

		void fail();

	}

}
