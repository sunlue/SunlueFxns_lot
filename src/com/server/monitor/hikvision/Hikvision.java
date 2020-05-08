package com.server.monitor.hikvision;

import java.awt.Panel;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.server.monitor.hikvision.HCNetSDK.NET_DVR_DEVICEINFO_V30;
import com.server.monitor.hikvision.Login.LoginCallback;
import com.server.monitor.hikvision.RealPlay.RealPlayCallback;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.util.Layer;
import com.view.monitor.DeviceTree;

/**
 * 
 * @author xiebing
 *
 */
public class Hikvision {
	/**
	 * 通道树节点数目
	 */
	static int mHikvisionTreeNodeNum;
	/**
	 * 通道树根节点
	 */
	static DefaultMutableTreeNode mDeviceHikvisionRoot = DeviceTree.liveTree;

	private String ip;
	private short port;
	private String username;
	private String password;
	private Panel playPanel;
	private Boolean isCreateTree = false;

	public Hikvision() {
	}

	/**
	 * 初始化海康类
	 * 
	 * @param ip        设备地址
	 * @param port      设备端口
	 * @param username  设备用户名
	 * @param password  设备密码
	 * @param playPanel 播放窗口
	 */
	public Hikvision(String ip, short port, String username, String password, Panel playPanel) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.playPanel = playPanel;
	}

	/**
	 * 初始化海康类
	 * 
	 * @param ip           设备地址
	 * @param port         设备端口
	 * @param username     设备用户名
	 * @param password     设备密码
	 * @param playPanel    播放窗口
	 * @param isCreateTree 是否创建树
	 */
	public Hikvision(String ip, short port, String username, String password, Panel playPanel, Boolean isCreateTree) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.playPanel = playPanel;
		this.isCreateTree = isCreateTree;
	}

	/**
	 * 海康威视SDK初始化 设备鉴权、预览
	 * 
	 * @param callback
	 */
	public void handle(RealPlayCallback callback) {
		boolean initSuc = HCNetSDK.INSTANCE.NET_DVR_Init();
		if (initSuc != true) {
			callback.fail();
			Layer.alert("海康威视SDK初始化失败", 180, 140);
			return;
		}
		new HikvisionHandleThread(ip, port, username, password, playPanel, isCreateTree, callback).start();
	}

	/**
	 * 生成设备树
	 * 
	 * @param sDvrIp
	 * @param userId
	 * @param mDeviceInfo
	 */
	static void createDeviceTree(String sDvrIp, NativeLong userId, NET_DVR_DEVICEINFO_V30 mDeviceInfo) {
		// 获取IP接入配置参数
		IntByReference ibrBytesReturned = new IntByReference(0);
		boolean bRet = false;

		// IP参数
		HCNetSDK.NET_DVR_IPPARACFG mStrIpParaCfg = new HCNetSDK.NET_DVR_IPPARACFG();
		mStrIpParaCfg.write();
		Pointer lpIpParaConfig = mStrIpParaCfg.getPointer();
		bRet = HCNetSDK.INSTANCE.NET_DVR_GetDVRConfig(userId, HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0),
				lpIpParaConfig, mStrIpParaCfg.size(), ibrBytesReturned);
		mStrIpParaCfg.read();
		// 获取树模型
		DefaultTreeModel deciveTreeModel = ((DefaultTreeModel) DeviceTree.rootTree.getModel());
		if (!bRet) {
			// 设备不支持,则表示没有IP通道
			for (int iChannum = 0; iChannum < mDeviceInfo.byChanNum; iChannum++) {
				int channum = (iChannum + mDeviceInfo.byStartChan);
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sDvrIp + "@" + channum);
				deciveTreeModel.insertNodeInto(newNode, mDeviceHikvisionRoot, iChannum);
			}
		} else {
			// 设备支持IP通道
			for (int iChannum = 0; iChannum < mDeviceInfo.byChanNum; iChannum++) {
				if (mStrIpParaCfg.byAnalogChanEnable[iChannum] == 1) {
					int channum = (iChannum + mDeviceInfo.byStartChan);
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("C@" + sDvrIp + ":" + channum);
					deciveTreeModel.insertNodeInto(newNode, mDeviceHikvisionRoot, mHikvisionTreeNodeNum);
					mHikvisionTreeNodeNum++;
				}
			}
			for (int iChannum = 0; iChannum < HCNetSDK.MAX_IP_CHANNEL; iChannum++) {
				if (mStrIpParaCfg.struIPChanInfo[iChannum].byEnable == 1) {
					int channum = (iChannum + mDeviceInfo.byStartChan);
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("I@" + sDvrIp + ":" + channum);
					deciveTreeModel.insertNodeInto(newNode, mDeviceHikvisionRoot, mHikvisionTreeNodeNum);
				}
			}
		}
		// 将添加的节点显示到界面
		deciveTreeModel.reload();
		TreeNode root = (TreeNode) DeviceTree.rootTree.getModel().getRoot();
		DeviceTree.expandAll(DeviceTree.rootTree, new TreePath(root));
	}

}

class HikvisionHandleThread extends Thread {

	private String ip;
	private short port;
	private String username;
	private String password;
	private Panel playPanel;
	private Boolean isCreateTree;
	private RealPlayCallback callback;

	public HikvisionHandleThread(String ip, short port, String username, String password, Panel playPanel,
			Boolean isCreateTree, RealPlayCallback callback) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.playPanel = playPanel;
		this.isCreateTree = isCreateTree;
		this.callback = callback;
	}

	@Override
	public void run() {
		/**
		 * 海康威视设备登录
		 */
		new Login(ip, port, username, password).handle(new LoginCallback() {
			@Override
			public void fail(int errCode) {
				callback.fail();
				Layer.alert("注册失败【" + new Error().hikvision(errCode) + "】", 200, 140);
			}

			@Override
			public void success(NET_DVR_DEVICEINFO_V30 mDeviceInfo, NativeLong userId) {
				if (isCreateTree) {
					Hikvision.createDeviceTree(ip, userId, mDeviceInfo);
				}
				new RealPlay(ip, userId, 1, playPanel).handle(callback);
			}
		});
	}
}
