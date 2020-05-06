package com.server.monitor.hikvision;

import java.util.HashMap;
import java.util.Map;

import com.server.monitor.hikvision.HCNetSDK.NET_DVR_DEVICEINFO_V30;
import com.sun.jna.NativeLong;

/**
 * 设备注册类
 *
 * @author xiebing
 */
public class Login {

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

    public String ip;
    public Short port;
    public String username;
    public String password;

    public NativeLong userId;
    public HCNetSDK.NET_DVR_DEVICEINFO_V30 mDeviceInfo;

    public static Map<String, Map<String, Object>> DeviceLoginList = new HashMap<String, Map<String, Object>>();

    public NativeLong getUserId() {
        return userId;
    }

    public HCNetSDK.NET_DVR_DEVICEINFO_V30 getDeviceInfo() {
        return mDeviceInfo;
    }

    public Login(String ip, short port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void handle(LoginCallback callback) {
        mDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        userId = hCNetSDK.NET_DVR_Login_V30(ip, port, username, password, mDeviceInfo);
        if (userId.longValue() == -1) {
            callback.fail(hCNetSDK.NET_DVR_GetLastError());
        } else {
            callback.success(mDeviceInfo, userId);
        }
    }

    public interface LoginCallback {

        /**
         * 成功
         *
         * @param mDeviceInfo
         * @param userId
         */
        void success(NET_DVR_DEVICEINFO_V30 mDeviceInfo, NativeLong userId);

        /**
         * 失败
         *
         * @param errorCode
         */
        void fail(int errorCode);

    }

}
