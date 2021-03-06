package com.view.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.alibaba.fastjson.JSONObject;
import com.util.Layer;
import com.util.Layer.LayerLoadingCallback;
import com.util.Util;
import com.view.monitor.DeviceMgr.DeviceMgrCallback;
import com.view.monitor.Monitor.InitThreadCallback;

/**
 * @author xiebing
 */
public class Monitor extends JFrame {

	private static final long serialVersionUID = 1L;

	public static int width = 1278;
	public static int height = 818;

	public Monitor() {

		new Initialize(new InitThreadCallback() {
			@Override
			public void complete(JSplitPane splitPane) {
				setContentPane(splitPane);
				validate();
			}
		}).start();

		JLabel loading = new JLabel("正在加载中,请稍后...");
		loading.setFont(new Font("微软雅黑", 1, 20));
		loading.setHorizontalAlignment(SwingConstants.CENTER);
		loading.setVerticalAlignment(SwingConstants.CENTER);

		int x = ((Toolkit.getDefaultToolkit().getScreenSize().width) / 2) - width / 2;
		int y = ((Toolkit.getDefaultToolkit().getScreenSize().height) / 2) - height / 2;
		setLocationRelativeTo(null);
		setSize(width, height);
		setTitle("四川上略互动网络技术有限公司-景区综合管理平台-视频监控子系统");
		setBounds(x, y, width, height);
		setIconImage(Util.getLogoIcon("logo_16_16.png"));
		setJMenuBar(menubar());
		add(loading);

		setVisible(true);
	}

	private JMenuBar menubar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		menuBar.add(menu("配置", new String[] { "基本信息" }));
		return menuBar;
	}

	private JMenu menu(String text, String[] menuItem) {
		JMenu menu = new JMenu();
		menu.setText(text);
		for (int i = 0; i < menuItem.length; i++) {
			menu.add(menuItem(menuItem[i]));
		}
		return menu;
	}

	private JMenuItem menuItem(String text) {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText(text);
		menuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				menuItemClick(evt);
			}
		});
		return menuItem;
	}

	protected void menuItemClick(MouseEvent evt) {
		System.out.println(evt);

		JFrame jf = new JFrame();
		jf.setVisible(true);
		jf.setSize(200, 300);
	}

	/*************************************************
	 * 函数: centerWindow 函数描述:窗口置中
	 *************************************************/
	public static void centerWindow(java.awt.Container window) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = window.getSize().width;
		int h = window.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		window.setLocation(x, y);
	}

	public interface InitThreadCallback {
		/**
		 * 完成
		 * 
		 * @param splitPane
		 */
		void complete(JSplitPane splitPane);
	}

}

class DevicePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JDialog addDeviceDialog = null;
	private static JDialog mngDeviceDialog = null;

	public DevicePanel() {
		setLayout(new BorderLayout());
		setBackground(Color.white);
		add(action(), BorderLayout.SOUTH);
		add(new DeviceTree().panel(), BorderLayout.CENTER);
	}

	private Component action() {
		JPanel panel = new JPanel();
		/****************** start设备管理 **********************/
		JButton deviceMngBtn = new JButton();
		deviceMngBtn.setText("设备管理");
		deviceMngBtn.setCursor(new Cursor(12));
		deviceMngBtn.setContentAreaFilled(false);
		deviceMngBtn.setFocusPainted(false);
		deviceMngBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Layer.loading(new LayerLoadingCallback() {
					@Override
					public void start() {
					}

					@Override
					public void handle(JDialog dialog) {
						// 启用一个线程来获取数据并渲染到视图，关闭加载层
						new DeviceMngThread(deviceMngBtn, addDeviceDialog, mngDeviceDialog, dialog).start();
					}

					@Override
					public void end(JDialog dialog) {
						// 打开加载层
						dialog.setVisible(true);
					}
				});
			}
		});

		panel.add(deviceMngBtn);
		/********************* end设备管理 **********************/

		/********************* start 添加设备 **********************/
		JButton addDeviceBtn = new JButton();
		addDeviceBtn.setText("添加设备");
		addDeviceBtn.setCursor(new Cursor(12));
		addDeviceBtn.setContentAreaFilled(false);
		addDeviceBtn.setFocusPainted(false);

		addDeviceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel container = new DeviceMgr().addComp(new DeviceMgrCallback() {
					@Override
					public void handle(String action) {
						if (action == "success") {
							Layer.alert("添加成功", 200, 120);
							addDeviceDialog.dispose();
						} else if (action == "close") {
							addDeviceDialog.dispose();
						}
					}
				});
				addDeviceDialog = Layer.window("添加设备", container, 428, 268);
				addDeviceDialog.setVisible(true);
			}
		});

		panel.add(addDeviceBtn);
		/********************* end 添加设备 **********************/

		return panel;
	}

}

class Initialize extends Thread {

	private InitThreadCallback callback;

	public Initialize(InitThreadCallback callback) {
		this.callback = callback;
	}

	@Override
	public void run() {
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(new DevicePanel());
		splitPane.setRightComponent(new Container());
		splitPane.setDividerSize(6);
		splitPane.setDividerLocation(200);
		callback.complete(splitPane);
	}
}

class DeviceMngThread extends Thread {

	private JButton deviceMngBtn;
	private JDialog addDeviceDialog;
	private JDialog mngDeviceDialog;
	private JDialog dialog;

	public DeviceMngThread(JButton deviceMngBtn, JDialog addDeviceDialog, JDialog mngDeviceDialog, JDialog dialog) {
		this.deviceMngBtn = deviceMngBtn;
		this.addDeviceDialog = addDeviceDialog;
		this.mngDeviceDialog = mngDeviceDialog;
		this.dialog = dialog;
	}

	@Override
	public void run() {
		ArrayList<JSONObject> rSet;
		try {
			String name = deviceMngBtn.getText();
			rSet = new com.action.Device().select();
			JPanel container = new DeviceMgr(rSet).mngComp(new DeviceMgrCallback() {
				@Override
				public void handle(String action) {
					if (action == "add") {
						JPanel container = new DeviceMgr().addComp(new DeviceMgrCallback() {
							@Override
							public void handle(String action) {
								if (action == "success") {
									Layer.alert("添加成功", 200, 120);
									addDeviceDialog.dispose();
								} else if (action == "close") {
									addDeviceDialog.dispose();
								}
							}
						});
						addDeviceDialog = Layer.window("添加设备", container, 428, 268);
						addDeviceDialog.setVisible(true);
					} else if (action == "") {

					}
				}
			});
			mngDeviceDialog = Layer.window(name, container, 815, 600);
			dialog.dispose();
			mngDeviceDialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
