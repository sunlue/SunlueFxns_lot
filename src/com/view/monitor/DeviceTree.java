package com.view.monitor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.alibaba.fastjson.JSONObject;
import com.server.monitor.hikvision.Hikvision;
import com.util.Layer;

public class DeviceTree extends JTree {
	private static final long serialVersionUID = 1L;
	public static JTree rootTree;
	public static DefaultMutableTreeNode rootNode;
	public static DefaultMutableTreeNode hikvisionTree;
	public static DefaultMutableTreeNode dahuaTree;
	public static DefaultMutableTreeNode liveTree;
	public static Map<String, JSONObject> deviceList = new HashMap<String, JSONObject>();

	public DeviceTree() {
		rootNode = new DefaultMutableTreeNode("根节点");
		hikvisionTree = new DefaultMutableTreeNode("海康威视");
		dahuaTree = new DefaultMutableTreeNode("大华监控");
		liveTree = new DefaultMutableTreeNode("已打开");
		try {
			ArrayList<JSONObject> rSet = new com.action.Device().select();
			for (int i = 0; i < rSet.size(); i++) {
				String type = rSet.get(i).getString("type");
				String ip = rSet.get(i).getString("ip");
				if (type.equals("0")) {
					hikvisionTree.add(new DefaultMutableTreeNode(ip));
				}
				if (type.equals("1")) {
					dahuaTree.add(new DefaultMutableTreeNode(ip));
				}
				deviceList.put(ip, rSet.get(i));
			}
		} catch (Exception e1) {
			Layer.alert("数据获取错误", 260, 140);
		}
		rootNode.add(hikvisionTree);
		rootNode.add(dahuaTree);
		rootNode.add(liveTree);
		rootTree = new JTree(rootNode);
		rootTree.setModel(new DefaultTreeModel(rootNode));
		rootTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				String node = e.getPath().getLastPathComponent().toString();
				JSONObject rSet = deviceList.get(node);
				if (rSet == null) {
					try {
						rSet = new com.action.Device().find("ip", node);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				String ip = rSet.getString("ip");
				short port = Short.parseShort(rSet.getString("port"));
				String username = rSet.getString("username");
				String password = rSet.getString("password");
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Hikvision(ip, port, username, password, Container.RealplayPanel);
					}
				}).start();

			}
		});
		// 设置树显示根节点句柄
		rootTree.setShowsRootHandles(true);
		TreeNode root = (TreeNode) rootTree.getModel().getRoot();
		DeviceTree.expandAll(rootTree, new TreePath(root));
	}

	public JScrollPane panel() {
		JScrollPane scrollPane = new JScrollPane(rootTree);
		return scrollPane;
	}

	public static void expandAll(JTree tree, TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path);
			}
		}
		tree.expandPath(parent);
	}
}
