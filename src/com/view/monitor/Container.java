package com.view.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import com.server.monitor.hikvision.HCNetSDK;
import com.util.CyFont;

/**
 * 视频监控容器类
 * 
 * @author xiebing
 *
 */
public class Container extends JSplitPane implements MouseListener {
	private static final long serialVersionUID = 1L;
	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

	public static JPanel RealplayPanelArea = new JPanel();
	public static Panel RealplayPanel = new Panel();

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
		RealplayPanelArea.setLayout(layout);
		for (int i = 0; i < grid; i++) {
			Panel cellPanel = new Panel();
			cellPanel.setCursor(new Cursor(12));
			cellPanel.setName(String.valueOf(i));
			cellPanel.setBackground(Color.WHITE);
			if (i == 0) {
				JMenuItem menuItem = new JMenuItem("打开云台");
				menuItem.setHorizontalAlignment(SwingConstants.CENTER);
				menuItem.setVerticalAlignment(SwingConstants.CENTER);
				menuItem.setFont(CyFont.PuHuiTi(CyFont.Bold, 12));
				menuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("点击了云台");
					}
				});
				JPopupMenu popup = new JPopupMenu();
				popup.add(menuItem);
				cellPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						if (e.getButton() == MouseEvent.BUTTON3) {
							popup.show(e.getComponent(), e.getX(), e.getY());
						}
					}
				});
				RealplayPanel = cellPanel;
			} else {
				cellPanel.setLayout(new BorderLayout());
				JLabel label = new JLabel("<html><body>暂无视频源或<br>没有视频信号<body></html>");
				label.setFont(CyFont.PuHuiTi(CyFont.Medium, 12));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setBackground(Color.white);
				label.setOpaque(true);
				cellPanel.add(label, BorderLayout.CENTER);
			}

			cellPanel.addMouseListener(this);
			RealplayPanelArea.add(cellPanel);
		}

		return RealplayPanelArea;
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
//				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				window.setSize(800, 600);
				window.setContentPane(RealplayPanelArea);
				window.addMouseListener(new java.awt.event.MouseAdapter() {
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
		int count = RealplayPanelArea.getComponentCount();
		for (int i = 0; i < count; i++) {
			RealplayPanelArea.getComponent(i).setBackground(Color.WHITE);
		}

		RealplayPanel = (Panel) e.getComponent();
		RealplayPanel.setBackground(new Color(51, 51, 51));
		RealplayPanel.removeAll();
		RealplayPanel.setLayout(new FlowLayout());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		/*************************************************
		 * 函数: "播放窗口" 双击响应函数 函数描述: 双击全屏预览当前预览通道
		 *************************************************/
		if (e.getClickCount() == 2) {
//			Hikvision.FullScreen(e);
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
