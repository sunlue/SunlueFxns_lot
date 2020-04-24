package com.view.monitor;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.util.Util;
import com.view.monitor.container.Container;
import com.view.monitor.device.Device;

public class Monitor extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final JDesktopPane desktopPane = new JDesktopPane();

	public static int width = 1278;
	public static int height = 818;

	public Monitor() throws Exception {
		JSplitPane SplitPane = new JSplitPane();
		SplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		SplitPane.setLeftComponent(new Device());
		SplitPane.setRightComponent(new Container());
		SplitPane.setDividerSize(6);
		SplitPane.setDividerLocation(200);
		
		int x = ((Toolkit.getDefaultToolkit().getScreenSize().width) / 2) - width / 2;
		int y = ((Toolkit.getDefaultToolkit().getScreenSize().height) / 2) - height / 2;
		setLocationRelativeTo(null);
		setSize(width, height);
		setTitle("四川上略互动网络技术有限公司-景区综合管理平台-视频监控子系统");
		setBounds(x, y, width, height);
		setIconImage(Util.getLogoIcon("logo_16_16.png"));
		setJMenuBar(menubar());
		setContentPane(SplitPane);
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

}
