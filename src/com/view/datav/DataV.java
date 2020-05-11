package com.view.datav;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.util.Http;
import com.util.Util;
import com.view.datav.DataV.WeaterCallback;

/**
 * 数据可视化底层类
 * 
 * @author xiebing
 */
public class DataV extends JWindow implements MouseListener {

	private static final long serialVersionUID = 1L;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel menuPanel;

	public DataV() {
		setSize(screenSize);
		setLayout(new BorderLayout());
		add(header(), BorderLayout.NORTH);
		add(menu(), BorderLayout.WEST);
		add(new com.view.datav.weather.Container(), BorderLayout.CENTER);
		setVisible(true);
		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
//					dispose();
					System.exit(0);
				}
			}
		});
	}

	private JPanel header() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 60));
		panel.setBackground(new Color(7, 10, 85));
		panel.setLayout(new BorderLayout());

		JLabel currTime = new JLabel();
		currTime.setIcon(Util.getImageIcon("logo_200_200.png", 48, 48));
		currTime.setForeground(Color.white);
		currTime.setFont(new Font("微软雅黑", 1, 14));
		currTime.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
		currTime.setIconTextGap(16);
		currTime.setPreferredSize(new Dimension((int) (screenSize.getWidth() * 0.2), 60));
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currTime.setText(Util.getDateTime());
			}
		});
		timer.start();

		JLabel title = new JLabel();
		title.setText("四川上略互动网络技术有限公司");
		title.setFont(new Font("微软雅黑", 1, 34));
		title.setForeground(Color.white);
		title.setVerticalAlignment(SwingConstants.CENTER);
		title.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel centerPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Image bg = Util.getImage("datav_main_header_bg.png");
				g.drawImage(bg, 0, 0, getSize().width, getSize().height, this);
			}
		};
		centerPanel.setOpaque(false);
		centerPanel.add(title);

		new GetWeater(new WeaterCallback() {
			@Override
			public void complete(JPanel weatherPanel) {
				weatherPanel.setPreferredSize(new Dimension((int) (screenSize.getWidth() * 0.2), 60));
				panel.add(weatherPanel, BorderLayout.EAST);
			}
		}).start();

		panel.add(currTime, BorderLayout.WEST);
		panel.add(centerPanel, BorderLayout.CENTER);

		return panel;
	}

	private JPanel menu() {

		menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(80, 100));
		menuPanel.setBackground(new Color(8, 38, 113));
		menuPanel.setLayout(null);

		String[] menuItem = new String[] { "综合管理", "视频监控", "指挥调度", "停车管理", "环境天气" };
		ArrayList<ImageIcon> menuIcon = new ArrayList<ImageIcon>();

		menuIcon.add(Util.getImageIcon("datav_main.png", 38, 38));
		menuIcon.add(Util.getImageIcon("datav_monitor.png", 38, 38));
		menuIcon.add(Util.getImageIcon("datav_dispatch.png", 38, 38));
		menuIcon.add(Util.getImageIcon("datav_parking.png", 38, 38));
		menuIcon.add(Util.getImageIcon("datav_weather.png", 38, 38));

		for (int i = 0; i < menuItem.length; i++) {
			JLabel item = new JLabel(menuItem[i], menuIcon.get(i), JLabel.CENTER);
			item.setHorizontalAlignment(JLabel.CENTER);
			item.setVerticalAlignment(JLabel.CENTER);
			item.setHorizontalTextPosition(SwingConstants.CENTER);
			item.setVerticalTextPosition(SwingConstants.BOTTOM);
			item.setForeground(Color.white);
			item.setFont(new Font("微软雅黑", 1, 12));
			item.setBounds(0, 100 * i, 80, 100);
			item.setCursor(new Cursor(12));
			item.setName(menuItem[i]);
			item.addMouseListener(this);
			if (i == 0) {
				item.setOpaque(true);
				item.setBackground(new Color(18, 25, 88));
			}
			menuPanel.add(item);
		}

		return menuPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int menuLabelCount = menuPanel.getComponentCount();
		for (int i = 0; i < menuLabelCount; i++) {
			JLabel menuItem = (JLabel) menuPanel.getComponent(i);
			if (menuItem.equals(e.getComponent())) {
				menuItem.setOpaque(true);
				menuItem.setBackground(new Color(18, 25, 88));
			} else {
				menuItem.setOpaque(false);
				menuItem.setBackground(new Color(8, 38, 113));
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

	public interface WeaterCallback {
		void complete(JPanel weatherPanel);
	}

}

class GetWeater extends Thread {
	private WeaterCallback callback;

	public GetWeater(WeaterCallback callback) {
		this.callback = callback;
	}

	public void run() {
		String url = "https://www.tianqiapi.com/api/?version=v6&appid=74669544&appsecret=L4uImetj&cityid=101270114&lng=105.826194&lat=32.432276";
		try {
			String rSet = Http.get(url);
			JSONObject result = JSON.parseObject(rSet);
			JLabel today = new JLabel();
			today.setText("今日天气 ");
			today.setForeground(Color.white);
			today.setFont(new Font("微软雅黑", 1, 14));
			today.setHorizontalAlignment(SwingConstants.RIGHT);
			today.setVerticalAlignment(SwingConstants.CENTER);
			today.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
			today.setPreferredSize(new Dimension(68, 60));

			StringBuffer buffer = new StringBuffer();
			buffer.append(result.getString("wea"));
			buffer.append(result.getString("tem2"));
			buffer.append("~");
			buffer.append(result.getString("tem1"));

			JLabel weather = new JLabel();
			weather.setIcon(Util.getImageIcon("weather_" + result.get("wea_img") + ".png", 38, 38));
			weather.setText(buffer.toString() + "℃ ");
			weather.setForeground(Color.WHITE);
			weather.setFont(new Font("微软雅黑", 1, 14));
			weather.setHorizontalAlignment(SwingConstants.RIGHT);
			weather.setVerticalAlignment(SwingConstants.CENTER);
			weather.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
			weather.setPreferredSize(new Dimension(160, 60));

			JPanel weatherPanel = new JPanel();
			weatherPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
			weatherPanel.add(today);
			weatherPanel.add(weather);
			weatherPanel.setOpaque(false);
			callback.complete(weatherPanel);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
