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

public class Hikvision {

	int m_iHikvisionTreeNodeNum;// 通道树节点数目
	DefaultMutableTreeNode m_DeviceHikvisionRoot = DeviceTree.liveTree;// 通道树根节点
	private String ip;
	private short port;
	private String username;
	private String password;
	private Panel playPanel;

	public Hikvision() {
	}

	public Hikvision(String ip, short port, String username, String password, Panel playPanel) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.playPanel = playPanel;
	}

	public void handle(RealPlayCallback callback) {
		boolean initSuc = HCNetSDK.INSTANCE.NET_DVR_Init();
		if (initSuc != true) {
			callback.fail();
			Layer.alert("海康威视SDK初始化失败", 180, 140);
			return;
		}
		new Thread(new Runnable() {
			public void run() {
				new Login(ip, port, username, password).handle(new LoginCallback() {
					@Override
					public void fail(int errCode) {
						callback.fail();
						Layer.alert("注册失败【" + new Error().Hikvision(errCode) + "】", 200, 140);
					}

					@Override
					public void success(NET_DVR_DEVICEINFO_V30 m_DeviceInfo, NativeLong userID) {
						CreateDeviceTree(ip, userID, m_DeviceInfo);
						new RealPlay(ip, userID, 1, playPanel).handle(callback);
					}
				});
			}
		}).start();
	}

	/**
	 * 生成设备树
	 * 
	 * @param sDVRIP
	 * @param lUserID
	 * @param m_DeviceInfo
	 */
	private void CreateDeviceTree(String sDVRIP, NativeLong lUserID, NET_DVR_DEVICEINFO_V30 m_DeviceInfo) {

		IntByReference ibrBytesReturned = new IntByReference(0);// 获取IP接入配置参数
		boolean bRet = false;

//		// IP参数
		HCNetSDK.NET_DVR_IPPARACFG m_strIpparaCfg = new HCNetSDK.NET_DVR_IPPARACFG();
		m_strIpparaCfg.write();
		Pointer lpIpParaConfig = m_strIpparaCfg.getPointer();
		bRet = HCNetSDK.INSTANCE.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0),
				lpIpParaConfig, m_strIpparaCfg.size(), ibrBytesReturned);
		m_strIpparaCfg.read();
		DefaultTreeModel deciveTreeModel = ((DefaultTreeModel) DeviceTree.rootTree.getModel());// 获取树模型
		if (!bRet) {
			// 设备不支持,则表示没有IP通道
			for (int iChannum = 0; iChannum < m_DeviceInfo.byChanNum; iChannum++) {
				int channum = (iChannum + m_DeviceInfo.byStartChan);
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sDVRIP + "@" + channum);
				deciveTreeModel.insertNodeInto(newNode, m_DeviceHikvisionRoot, iChannum);
			}
		} else {
			// 设备支持IP通道
			for (int iChannum = 0; iChannum < m_DeviceInfo.byChanNum; iChannum++) {
				if (m_strIpparaCfg.byAnalogChanEnable[iChannum] == 1) {
					int channum = (iChannum + m_DeviceInfo.byStartChan);
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("C@" + sDVRIP + ":" + channum);
					deciveTreeModel.insertNodeInto(newNode, m_DeviceHikvisionRoot, m_iHikvisionTreeNodeNum);
					m_iHikvisionTreeNodeNum++;
				}
			}
			for (int iChannum = 0; iChannum < HCNetSDK.MAX_IP_CHANNEL; iChannum++)
				if (m_strIpparaCfg.struIPChanInfo[iChannum].byEnable == 1) {
					int channum = (iChannum + m_DeviceInfo.byStartChan);
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("I@" + sDVRIP + ":" + channum);
					deciveTreeModel.insertNodeInto(newNode, m_DeviceHikvisionRoot, m_iHikvisionTreeNodeNum);
				}
		}
		deciveTreeModel.reload();// 将添加的节点显示到界面
		TreeNode root = (TreeNode) DeviceTree.rootTree.getModel().getRoot();
		DeviceTree.expandAll(DeviceTree.rootTree, new TreePath(root));
	}

}
