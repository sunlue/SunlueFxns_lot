package com.view.datav;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.util.Util;
import com.view.datav.main.Container;

/**
 * 数据可视化底层类
 * @author xiebing
 */
public class DataV extends JWindow {

	private static final long serialVersionUID = 1L;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public DataV() {
		setSize(screenSize);

		setLayout(new BorderLayout());
		add(header(), BorderLayout.NORTH);
		add(menu(), BorderLayout.WEST);
		add(new Container(), BorderLayout.CENTER);
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

		JLabel weather = new JLabel();
		weather.setText("今日天气 : 阴 15-30℃ ");
		weather.setForeground(Color.white);
		weather.setFont(new Font("微软雅黑", 1, 14));
		weather.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

		panel.add(currTime, BorderLayout.WEST);
		panel.add(title, BorderLayout.CENTER);
		panel.add(weather, BorderLayout.EAST);

		return panel;
	}

	private JPanel menu() {

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(80, 100));
		panel.setBackground(new Color(8, 38, 113));
		panel.setLayout(null);
		ImageIcon icon = Util.getImageIcon("hardware.png", 38, 38);

		for (int i = 0; i < 6; i++) {
			JLabel item = new JLabel("综合管理", icon, JLabel.CENTER);
			item.setHorizontalAlignment(JLabel.CENTER);
			item.setVerticalAlignment(JLabel.CENTER);
			item.setHorizontalTextPosition(SwingConstants.CENTER);
			item.setVerticalTextPosition(SwingConstants.BOTTOM);
			item.setForeground(Color.white);
			item.setFont(new Font("微软雅黑", 1, 12));
			item.setBounds(0, 100 * i, 80, 100);
			item.setCursor(new Cursor(12));
			if (i == 0) {
				item.setOpaque(true);
				item.setBackground(new Color(18, 25, 88));
			}
			panel.add(item);
		}

		return panel;
	}

}

