package com.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import com.util.Layer;
import com.util.Util;
import com.util.Layer.LayerCallback;

public class Frame {

	public static int tempX;
	public static int tempY;
	public static int winX;
	public static int winY;
	public static int oldX;
	public static int oldY;
	public static JFrame jf = new JFrame();
	public static Component WEST;
	public static Component CENTER;

	public static int width = 900;
	public static int height = 640;

	public Frame() {
		System.setProperty("ROOT_PATH", System.getProperty("user.dir"));
		Properties properties = Util.config("log4j.ini");
		PropertyConfigurator.configure(properties);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				init();
			}
		});
	}

	protected void init() {
		int x = ((Toolkit.getDefaultToolkit().getScreenSize().width) / 2) - width / 2;
		int y = ((Toolkit.getDefaultToolkit().getScreenSize().height) / 2) - height / 2;

		jf.setTitle("四川上略互动网络技术有限公司");
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setUndecorated(true);
		jf.setBounds(x, y, width, height);
		jf.setIconImage(Util.getLogoIcon("logo_16_16.png"));

		jf.add(Module.init(), BorderLayout.WEST);
		jf.add(Main.init(jf), BorderLayout.CENTER);
		jf.setVisible(true);

		Tray();

		jf.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				tempX = (int) point.getX();
				tempY = (int) point.getY();
				oldX = (int) point.getX();
				oldY = (int) point.getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		jf.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {

				Point point = e.getPoint();
				Rectangle rec = jf.getBounds();

				winX = (int) rec.getX();
				winY = (int) rec.getY();
				int x = (int) point.getX();
				int y = (int) point.getY();
				tempX = x - oldX;
				tempY = y - oldY;

				jf.setLocation((int) (winX + tempX), (int) (winY + tempY));

			}
		});
	}

	private void Tray() {
		if (java.awt.SystemTray.isSupported()) {
			// 获取当前平台的系统托盘
			SystemTray tray = SystemTray.getSystemTray();

			// 加载一个图片用于托盘图标的显示
			Image image = Util.getLogoIcon("logo_16_16.png");

			// 创建点击图标时的弹出菜单
			PopupMenu popupMenu = new PopupMenu();

			MenuItem openItem = new MenuItem("打开");
			MenuItem exitItem = new MenuItem("退出");

			openItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 点击打开菜单时显示窗口
					if (!jf.isShowing()) {
						jf.setVisible(true);
					}
				}
			});
			exitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Layer.confirm("确认要退出并关闭程序吗?", 240, 160, new LayerCallback() {
						@Override
						public void clickBtn(String btn) {

						}

						@Override
						public void clickOkBtn(boolean confirm) {
							String DIR_PATH = System.getProperty("user.dir") + File.separator + "msg";
							File file = new File(DIR_PATH);
							file.delete();
							System.exit(0);
						}

						@Override
						public void clickCancelBtn() {

						}
					});
				}
			});

			popupMenu.add(openItem);
			popupMenu.add(exitItem);

			// 创建一个托盘图标
			TrayIcon trayIcon = new TrayIcon(image, jf.getTitle(), popupMenu);

			// 托盘图标自适应尺寸
			trayIcon.setImageAutoSize(true);

			trayIcon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("托盘图标被右键点击");
				}
			});
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					switch (e.getButton()) {
					case MouseEvent.BUTTON1: {
						System.out.println("托盘图标被鼠标左键被点击");
						break;
					}
					case MouseEvent.BUTTON2: {
						System.out.println("托盘图标被鼠标中键被点击");
						break;
					}
					case MouseEvent.BUTTON3: {
						System.out.println("托盘图标被鼠标右键被点击");
						break;
					}
					default: {
						break;
					}
					}
				}
			});

			// 添加托盘图标到系统托盘
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("当前系统不支持系统托盘");
		}

	}
}
