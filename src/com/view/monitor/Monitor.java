package com.view.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
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
import com.sun.jna.NativeLong;
import com.util.Layer;
import com.util.Util;
import com.util.Layer.LayerLoadingCallback;
import com.view.monitor.DeviceMgr.DeviceMgrCallback;
import com.view.monitor.hikvision.JDialogHideAlarm;
import com.view.monitor.hikvision.JDialogHideArea;
import com.view.monitor.hikvision.JDialogMotionDetect;
import com.view.monitor.hikvision.JDialogRecordSchedule;
import com.view.monitor.hikvision.JDialogShowString;
import com.view.monitor.hikvision.JDialogVideoInLost;

public class Monitor extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final JDesktopPane desktopPane = new JDesktopPane();

	public static int width = 1278;
	public static int height = 818;

	public Monitor() {
		Thread view = new Thread(new Runnable() {
			@Override
			public void run() {
				JSplitPane SplitPane = new JSplitPane();
				SplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
				SplitPane.setLeftComponent(new DevicePanel());
				SplitPane.setRightComponent(new Container());
				SplitPane.setDividerSize(6);
				SplitPane.setDividerLocation(200);
				setContentPane(SplitPane);
				validate();
			}
		});
		view.start();
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

	public static NativeLong g_lVoiceHandle;

	public static void centerWindow(JDialogHideArea dlg) {
		// TODO Auto-generated method stub

	}

	public static void centerWindow(JDialogVideoInLost dlg) {
		// TODO Auto-generated method stub

	}

	public static void centerWindow(JDialogMotionDetect dlg) {
		// TODO Auto-generated method stub

	}

	public static void centerWindow(JDialogHideAlarm dlg) {
		// TODO Auto-generated method stub

	}

	public static void centerWindow(JDialogShowString dialogShowString) {
		// TODO Auto-generated method stub

	}

	public static void centerWindow(JDialogRecordSchedule dialogRecordSchedule) {
		// TODO Auto-generated method stub

	}

}

class DevicePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JDialog addDeviceDialog = null;
	private static JDialog mngDeviceDialog = null;

	public DevicePanel() {
		setLayout(new BorderLayout());
		setBackground(Color.white);
		add(new JLabel("设备"));
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
						new Thread(new Runnable() {
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
						}).start();
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
