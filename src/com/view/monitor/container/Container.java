package com.view.monitor.container;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import com.util.CyFont;

public class Container extends JSplitPane {
	private static final long serialVersionUID = 1L;

	public Container() {
		setBackground(Color.RED);
		setTopComponent(setting());
		setBottomComponent(video());
		setDividerSize(6);
//		setDividerLocation(100);
		setContinuousLayout(true);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
	}

	private JPanel video() {
		int grid = 16;
		int cell = (int) Math.sqrt(grid);
		GridLayout layout = new GridLayout(cell, cell, 1, 1);
		JPanel panel = new JPanel();
		panel.setLayout(layout);

		for (int i = 0; i < grid; i++) {
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

				JPanel cellPanel = new JPanel();
				cellPanel.setBackground(Color.RED);
				cellPanel.setCursor(new Cursor(12));
				cellPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						if (e.getButton() == MouseEvent.BUTTON3) {
							popup.show(e.getComponent(), e.getX(), e.getY());
						}
					}
				});
				panel.add(cellPanel);
			} else {
				JLabel label = new JLabel("<html><body>暂无视频源或<br>没有视频信号<body></html>");
				label.setFont(CyFont.PuHuiTi(CyFont.Medium, 12));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setBackground(Color.white);
				label.setOpaque(true);
				panel.add(label);
			}
		}

		return panel;
	}

	private JPanel setting() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.add(new JButton("1画面"));
		panel.add(new JButton("4画面"));
		panel.add(new JButton("9画面"));
		panel.add(new JButton("16画面"));
		panel.add(new JButton("25画面"));
		panel.add(new JButton("全屏"));
		return panel;
	}

}
