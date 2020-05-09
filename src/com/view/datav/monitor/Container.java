package com.view.datav.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.alibaba.fastjson.JSONObject;
import com.server.monitor.hikvision.Hikvision;
import com.server.monitor.hikvision.RealPlay;
import com.server.monitor.hikvision.RealPlay.RealPlayCallback;
import com.sun.jna.NativeLong;
import com.util.CyFont;
import com.util.Layer;
import com.view.datav.monitor.Container.PlayVideoCallback;

/**
 * 视频监控
 * 
 * @author xiebing
 *
 */
public class Container extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	protected static final String JLabel = null;
	private static final int DoubleClick = 2;

	public Container() {
		setBackground(new Color(7, 10, 85));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		int grid = 16;
		int cell = (int) Math.sqrt(grid);
		GridLayout layout = new GridLayout(cell, cell, 0, 0);
		setLayout(layout);
		for (int i = 0; i < grid; i++) {
			JPanel cellPanel = new JPanel();
			cellPanel.setLayout(new BorderLayout());
			cellPanel.setName(String.valueOf(i));
			cellPanel.setBorder(BorderFactory.createLineBorder(new Color(51, 51, 51), 1));
			cellPanel.setOpaque(false);
			cellPanel.add(setPanel());
			cellPanel.add(playPanel(String.valueOf(i)));
			add(cellPanel);
		}
		new DeviceInfo(new PlayVideoCallback() {
			@Override
			public void play(int index, String ip, Short port, String username, String password) {
				JPanel cellPanel = (JPanel) getComponent(index);
				JPanel setPanel = (JPanel) cellPanel.getComponent(0);
				setPanel.add(new JLabel(ip));
				Panel playPanel = (Panel) cellPanel.getComponent(1);
				((JLabel) playPanel.getComponent(0)).setText("实时视频获取中");
				new Hikvision(ip, port, username, password, playPanel).handle(new RealPlayCallback() {
					@Override
					public void success(Map<String, Object> data, NativeLong realHandle) {
						playPanel.setName(ip);
					}

					@Override
					public void fail(String errMsg) {
						JLabel label = ((JLabel) playPanel.getComponent(0));
						label.setSize(200, 100);
						label.setForeground(Color.RED);
						Layer.labelTextLine(label, errMsg);
					}

					@Override
					public void fail(String errMsg, int width, int height) {
						JLabel label = ((JLabel) playPanel.getComponent(0));
						label.setSize(width, height);
						label.setForeground(Color.RED);
						Layer.labelTextLine(label, errMsg);
					}
				});
			}
		}).start();
	}

	private JPanel setPanel() {
		JPanel setPanel = new JPanel();
		setPanel.setLayout(new BorderLayout());
		setPanel.setBackground(new Color(51, 51, 51));
		return setPanel;
	}

	private Panel playPanel(String name) {
		Panel playPanel = new Panel();
		playPanel.setCursor(new Cursor(12));
		playPanel.setName(name);
		playPanel.setBackground(new Color(255, 255, 255, 100));
		playPanel.setLayout(new BorderLayout());
		playPanel.addMouseListener(this);
		JLabel label = new JLabel("<html><body>暂无视频源或<br>没有视频信号<body></html>");
		label.setFont(CyFont.puHuiTi(CyFont.Medium, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setOpaque(false);
		playPanel.add(label, BorderLayout.CENTER);
		return playPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == DoubleClick) {
			try {
				RealPlay.fullScreen(e);
			} catch (Exception e1) {
				Panel playPanel = (Panel) e.getComponent();
				JLabel label = ((JLabel) playPanel.getComponent(0));
				label.setText(e1.getMessage());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

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

	public interface PlayVideoCallback {
		void play(int index, String ip, Short port, String username, String password);
	}

}

class DeviceInfo extends Thread {
	private ArrayList<JSONObject> rSet;
	private PlayVideoCallback callback;

	public DeviceInfo(PlayVideoCallback callback) {
		this.callback = callback;
	}

	@Override
	public void run() {
		try {
			rSet = new com.action.Device().select();
			for (int i = 0; i < rSet.size(); i++) {
				new PlayThread(rSet.get(i), i, callback).start();
			}
		} catch (Exception e1) {
			Layer.alert("设备数据获取错误", 260, 140);
		}
	}
}

class PlayThread extends Thread {
	private String ip;
	private Short port;
	private String username;
	private String password;
	private int index;
	private PlayVideoCallback callback;

	public PlayThread(JSONObject obj, int index, PlayVideoCallback callback) {
		this.ip = obj.getString("ip");
		this.port = obj.getShort("port");
		this.username = obj.getString("username");
		this.password = obj.getString("password");
		this.index = index;
		this.callback = callback;
	}

	@Override
	public void run() {
		callback.play(index, ip, port, username, password);
	}
}
