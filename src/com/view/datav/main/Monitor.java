package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.server.monitor.hikvision.Hikvision;
import com.server.monitor.hikvision.RealPlay;
import com.server.monitor.hikvision.RealPlay.RealPlayCallback;
import com.sun.jna.NativeLong;
import com.util.CyFont;
import com.view.datav.Cpanel;

/**
 * @author xiebing
 */
public class Monitor extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;

	public Monitor(Dimension preferredSize) {
		int width = (int) (preferredSize.getWidth());
		int height = (int) (preferredSize.getHeight());
		new Monitor(width, height);
	}

	public Monitor(int width, int height) {
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(width, height));
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new GridLayout(1, 2, 10, 0));
		mainPanel.add(monitorPanel("1"));
		mainPanel.add(monitorPanel("2"));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 16, 10));

		Cpanel panel = new Cpanel("视频监控", "Daily traffic flow", mainPanel);

		setOpaque(false);
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);

	}

	private Component monitorPanel(String name) {
		JPanel cellPanel = new JPanel();
		cellPanel.setLayout(new BorderLayout());
		cellPanel.setName(name);
		cellPanel.setOpaque(false);

		JLabel label = new JLabel("<html><body>暂无视频源或<br>没有视频信号<body></html>");
		label.setFont(CyFont.puHuiTi(CyFont.Medium, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setOpaque(false);

		Panel playPanel = new Panel();
		playPanel.setCursor(new Cursor(12));
		playPanel.setName(name);
		playPanel.setLayout(new BorderLayout());
		playPanel.add(label, BorderLayout.CENTER);
		playPanel.addMouseListener(this);
		cellPanel.add(playPanel, BorderLayout.CENTER);

		String ip = "192.168.110.206";
		short port = 8000;
		String username = "admin";
		String password = "DGANFN";

		new PlayMonitorVideo(ip, port, username, password, playPanel).start();
		return cellPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 2) {
			try {
				RealPlay.fullScreen(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}

class PlayMonitorVideo extends Thread {

	private Panel playPanel;
	private String ip;
	private short port;
	private String username;
	private String password;

	public PlayMonitorVideo(String ip, short port, String username, String password, Panel playPanel) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.playPanel = playPanel;
	}

	@Override
	public void run() {
		JLabel label = (JLabel) playPanel.getComponent(0);
		label.setText("正在执行中");
		new Hikvision(ip, port, username, password, playPanel).handle(new RealPlayCallback() {
			@Override
			public void success(Map<String, Object> data, NativeLong realHandle) {
				playPanel.setName(ip);
			}

			@Override
			public void fail(String errMsg) {
				// TODO Auto-generated method stub

			}

			@Override
			public void fail(String errMsg, int width, int height) {
				// TODO Auto-generated method stub

			}
		});
	}
}
