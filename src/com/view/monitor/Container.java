package com.view.monitor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import com.server.monitor.hikvision.HCNetSDK;
import com.server.monitor.hikvision.RealPlay;
import com.sun.jna.NativeLong;
import com.util.CyFont;
import com.util.Layer;

/**
 * 视频监控容器类
 * 
 * @author xiebing
 *
 */
public class Container extends JSplitPane implements MouseListener {
	private static final long serialVersionUID = 1L;
	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

	public static JPanel RealPlayPanelArea = new JPanel();
	public static Panel RealPlayPanel = new Panel();

	public Container() {
		setTopComponent(setting());
		setBottomComponent(video());
		setDividerSize(6);
		setContinuousLayout(true);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
	}

	private JPanel video() {
		int grid = 9;
		int cell = (int) Math.sqrt(grid);
		GridLayout layout = new GridLayout(cell, cell, 1, 1);
		RealPlayPanelArea.setLayout(layout);
		for (int i = 0; i < grid; i++) {
			JPanel cellPanel = new JPanel();
			cellPanel.setLayout(new BorderLayout());
			cellPanel.setName(String.valueOf(i));

			JPanel setPanel = new JPanel();
			setPanel.setLayout(new BorderLayout());
			setPanel.setBackground(new Color(51, 51, 51));

			JLabel nameLabel = new JLabel();
			nameLabel.setForeground(Color.white);
			nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
			setPanel.add(nameLabel, BorderLayout.WEST);

			Panel palyPanel = new Panel();
			palyPanel.setCursor(new Cursor(12));
			palyPanel.setName(String.valueOf(i));
			palyPanel.setBackground(Color.WHITE);
			if (i == 0) {
				RealPlayPanel = palyPanel;
				cellPanel.setBorder(BorderFactory.createLineBorder(new Color(51, 51, 51), 2));
			} else {
				JLabel label = new JLabel("<html><body>暂无视频源或<br>没有视频信号<body></html>");
				label.setFont(CyFont.puHuiTi(CyFont.Medium, 12));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setBackground(Color.white);
				label.setOpaque(true);
				palyPanel.setLayout(new BorderLayout());
				palyPanel.add(label, BorderLayout.CENTER);
			}
			palyPanel.addMouseListener(this);
			cellPanel.add(setPanel, BorderLayout.NORTH);
			cellPanel.add(palyPanel, BorderLayout.CENTER);
			RealPlayPanelArea.add(cellPanel);
		}

		return RealPlayPanelArea;
	}

	public static void console(JPanel parentPanel, NativeLong realHandle) {
		JPanel setPanel = (JPanel) parentPanel.getComponent(0);
		JButton holder = new JButton("云台");
		holder.setContentAreaFilled(false);
		holder.setCursor(new Cursor(12));
		holder.setBorderPainted(false);
		holder.setFocusPainted(false);
		holder.setPreferredSize(new Dimension(40, 20));
		holder.setMargin(new Insets(0, 0, 0, 0));
		holder.setForeground(Color.white);
		setPanel.add(holder, BorderLayout.EAST);
		holder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Layer.alert("暂未开放", 200, 100);
			}
		});
	}

	private JPanel setting() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.add(new JButton("1画面"));
		panel.add(new JButton("4画面"));
		panel.add(new JButton("9画面"));
		panel.add(new JButton("16画面"));
		panel.add(new JButton("25画面"));

		JButton fullScreenBtn = new JButton("全屏");
		fullScreenBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JWindow window = new JWindow();
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				window.setSize(screenSize);
				window.setContentPane(RealPlayPanelArea);
				window.addMouseListener(new java.awt.event.MouseAdapter() {
					@Override
					public void mousePressed(java.awt.event.MouseEvent evt) {
						if (evt.getClickCount() == 2) {
							window.dispose();
						}
					}
				});
				window.setVisible(true);
			}
		});
		panel.add(fullScreenBtn);

		return panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			Panel currPanel = (Panel) e.getComponent();
			JPanel parentPanel = (JPanel) e.getComponent().getParent();
			int count = RealPlayPanelArea.getComponentCount();
			for (int i = 0; i < count; i++) {
				((JPanel) RealPlayPanelArea.getComponent(i)).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			}
			parentPanel.setBorder(BorderFactory.createLineBorder(new Color(51, 51, 51), 2));
			RealPlayPanel = currPanel;
			RealPlayPanel.setBackground(Color.white);
			RealPlayPanel.removeAll();
			RealPlayPanel.setLayout(null);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		/*************************************************
		 * 函数: "播放窗口" 双击响应函数 函数描述: 双击全屏预览当前预览通道
		 *************************************************/
		if (e.getClickCount() == 2) {
			RealPlay.fullScreen(e);
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
