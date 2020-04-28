package com.server.monitor.hikvision;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JWindow;

import com.entity.VideoMonitor;
import com.server.monitor.hikvision.HCNetSDK.NET_DVR_CLIENTINFO;
import com.server.monitor.hikvision.HCNetSDK.NET_DVR_DEVICEINFO_V30;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.examples.win32.W32API.HWND;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.util.Layer;
import com.view.monitor.Container;

/**
 * 视频预览类
 * 
 * @author xiebing
 *
 */
public class RealPlay {

	protected int previewType = 0;
	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	static PlayCtrl playControl = PlayCtrl.INSTANCE;
	static FRealDataCallBack fRealDataCallBack;
	static NativeLongByReference m_lPort;// 回调预览时播放库端口指针

	public String ip;
	public NativeLong UserID;
	public Panel playPanel;
	public NET_DVR_DEVICEINFO_V30 m_DeviceInfo;
	public int channel;

	/**
	 * 
	 * @param UserID    用户参数
	 * @param channel   通道号
	 * @param playPanel 要播放的视图
	 */
	public RealPlay(String ip, NativeLong UserID, int channel, Panel playPanel) {
		fRealDataCallBack = new FRealDataCallBack();
		m_lPort = new NativeLongByReference(new NativeLong(-1));
		this.ip = ip;
		this.UserID = UserID;
		this.playPanel = playPanel;
		this.channel = channel;
	}

	public void handle(RealPlayCallback callback) {
		// 获取窗口句柄
		HWND hwnd = new HWND(Native.getComponentPointer(playPanel));
		// 用户参数
		HCNetSDK.NET_DVR_CLIENTINFO m_ClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
		m_ClientInfo.lChannel = new NativeLong(channel);
		// 预览句柄
		NativeLong lPreviewHandle = new NativeLong(-1);
		if (previewType == 0) {
			// 直接预览
			m_ClientInfo.hPlayWnd = hwnd;
			lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V30(UserID, m_ClientInfo, null, null, true);
		} else {
			// 回调预览
			m_ClientInfo.hPlayWnd = null;
			lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V30(UserID, m_ClientInfo, fRealDataCallBack, null, true);
		}

		long previewSucValue = lPreviewHandle.longValue();

		// 预览失败时:
		if (previewSucValue == -1) {
			callback.fail(lPreviewHandle);
			Layer.alert("预览失败", 280, 160);
			return;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ClientInfo", m_ClientInfo);
		data.put("UserID", UserID);
		data.put("channel", 1);
		VideoMonitor.setDeviceHikvisionRealPlay(ip, data);
		callback.success(data,lPreviewHandle);
	}

	/**
	 * 全屏预览
	 * 
	 * @param e
	 */
	public static void FullScreen(MouseEvent e) {
		// 新建全屏预览窗口
		final JWindow wind = new JWindow();
		// 获取屏幕尺寸
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		wind.setSize(screenSize);
		wind.setVisible(true);
		
		// 获取窗口句柄
		final HWND hwnd = new HWND(Native.getComponentPointer(wind));
		// 播放视频信息
		Map<String, Object> Realplay = VideoMonitor.getDeviceHikvisionRealPlay(e.getComponent().getName());
		// 用户参数
		HCNetSDK.NET_DVR_CLIENTINFO m_ClientInfo = (NET_DVR_CLIENTINFO) Realplay.get("ClientInfo");
		// 用户句柄
		NativeLong UserID = (NativeLong) Realplay.get("UserID");
		// 通道号
		int iChannelNum = (int) Realplay.get("channel");

		m_ClientInfo.hPlayWnd = hwnd;
		m_ClientInfo.lChannel = new NativeLong(iChannelNum);
		final NativeLong lRealHandle = hCNetSDK.NET_DVR_RealPlay_V30(UserID, m_ClientInfo, null, null, true);
			
		// JWindow增加双击响应函数,双击时停止预览,退出全屏
		wind.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					// 停止预览
					hCNetSDK.NET_DVR_StopRealPlay(lRealHandle);
					wind.dispose();
				}
			}
		});
	}
	
	public interface RealPlayCallback {

		void success(Map<String, Object> data, NativeLong RealHandle);

		void fail(NativeLong lPreviewHandle);

	}
	

	/******************************************************************************
	 * 内部类: FRealDataCallBack 实现预览回调数据
	 ******************************************************************************/
	class FRealDataCallBack implements HCNetSDK.FRealDataCallBack_V30 {
		// 预览回调
		public void invoke(NativeLong lRealHandle, int dwDataType, ByteByReference pBuffer, int dwBufSize,
				Pointer pUser) {
			HWND hwnd = new HWND(Native.getComponentPointer(Container.RealplayPanel));
			switch (dwDataType) {
			case HCNetSDK.NET_DVR_SYSHEAD: // 系统头
				// 获取播放库未使用的通道号
				if (!playControl.PlayM4_GetPort(m_lPort)) {
					break;
				}

				if (dwBufSize > 0) {
					// 设置实时流播放模式
					if (!playControl.PlayM4_SetStreamOpenMode(m_lPort.getValue(), PlayCtrl.STREAME_REALTIME)) {
						break;
					}
					// 打开流接口
					if (!playControl.PlayM4_OpenStream(m_lPort.getValue(), pBuffer, dwBufSize, 1024 * 1024)) {
						break;
					}
					// 播放开始
					if (!playControl.PlayM4_Play(m_lPort.getValue(), hwnd)) {
						break;
					}
				}
			case HCNetSDK.NET_DVR_STREAMDATA: // 码流数据
				if ((dwBufSize > 0) && (m_lPort.getValue().intValue() != -1)) {
					// 输入流数据
					if (!playControl.PlayM4_InputData(m_lPort.getValue(), pBuffer, dwBufSize)) {
						break;
					}
				}
			}
		}
	}



	
}


