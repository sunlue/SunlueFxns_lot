package com.view.monitor;

import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.alibaba.fastjson.JSONObject;
import com.server.monitor.hikvision.Hikvision;
import com.server.monitor.hikvision.RealPlay.RealPlayCallback;
import com.sun.jna.NativeLong;
import com.util.Layer;
import com.util.Layer.LayerLoadingCallback;

/**
 * 
 * @author xiebing
 *
 */
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
				if ("0".equals(type)) {
					hikvisionTree.add(new DefaultMutableTreeNode(ip));
				}
				if ("1".equals(type)) {
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
		rootTree.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				TreePath node = rootTree.getSelectionPath();
				jTreeChange(node);
			}
		});
		// 设置树显示根节点句柄
		rootTree.setShowsRootHandles(true);
		TreeNode root = (TreeNode) rootTree.getModel().getRoot();
		DeviceTree.expandAll(rootTree, new TreePath(root));
	}

	protected void jTreeChange(TreePath node) {
		if (!rootTree.isSelectionEmpty()) {
			String currNode = node.getLastPathComponent().toString();
			JSONObject rSet = deviceList.get(currNode);
			if (rSet == null) {
				try {
					rSet = new com.action.Device().find("ip", currNode);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			String ip = rSet.getString("ip");
			short port = Short.parseShort(rSet.getString("port"));
			String username = rSet.getString("username");
			String password = rSet.getString("password");

			Layer.loading("执行中，请稍后", new LayerLoadingCallback() {
				@Override
				public void start() {

				}

				@Override
				public void handle(JDialog dialog) {
					new DevionTreeChangeThread(ip, port, username, password, dialog).start();
				}

				@Override
				public void end(JDialog dialog) {
					dialog.setVisible(true);
				}
			});

		}
	}

	public static void reload(String nodeName, String ip, int type, String port, String username, String password) {
		JSONObject value = new JSONObject();
		value.put("ip", ip);
		value.put("type", type);
		value.put("port", port);
		value.put("username", username);
		value.put("password", password);
		deviceList.remove(ip);
		deviceList.put(ip, value);
		TreePath treePath = findInPath(new TreePath(rootTree.getModel().getRoot()), nodeName);
		if (treePath != null) {
			rootTree.getModel().valueForPathChanged(treePath, ip);
			rootTree.updateUI();
			rootTree.scrollPathToVisible(treePath);
		}
	}

	public static TreePath findInPath(TreePath treePath, String str) {
		Object object = treePath.getLastPathComponent();
		if (object == null) {
			return null;
		}

		String value = object.toString();
		if (str.equals(value)) {
			return treePath;
		} else {
			TreeModel model = rootTree.getModel();
			int n = model.getChildCount(object);
			for (int i = 0; i < n; i++) {
				Object child = model.getChild(object, i);
				TreePath path = treePath.pathByAddingChild(child);

				path = findInPath(path, str);
				if (path != null) {
					return path;
				}
			}
			return null;
		}
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

class DevionTreeChangeThread extends Thread {

	private String ip;
	private short port;
	private String username;
	private String password;
	private JDialog dialog;

	public DevionTreeChangeThread(String ip, short port, String username, String password, JDialog dialog) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.dialog = dialog;
	}

	@Override
	public void run() {
		Panel playPanel = Container.RealPlayPanel;
		new Hikvision(ip, port, username, password, playPanel, true).handle(new RealPlayCallback() {
			@Override
			public void success(Map<String, Object> data, NativeLong realHandle) {
				JPanel parentPanel = (JPanel) playPanel.getParent();
				parentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				parentPanel.setName(ip);
				playPanel.setName(ip);
				JPanel setPanel = (JPanel) parentPanel.getComponent(0);
				JLabel nameLabel = (JLabel) setPanel.getComponent(0);
				nameLabel.setText(ip);
				Container.console(parentPanel, realHandle);
			}

			@Override
			public void fail(String errMsg) {
				dialog.dispose();
				Layer.alert(errMsg, 180, 140);
			}

			@Override
			public void fail(String errMsg, int width, int height) {
				Layer.alert(errMsg, width, height);
			}
		});
	}
}
