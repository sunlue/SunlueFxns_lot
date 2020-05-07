package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		mainPanel.setLayout(new GridLayout(1,2,10,0));
		mainPanel.add(monitorPanel("1"));
		mainPanel.add(monitorPanel("2"));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 16, 10));
		
		Cpanel panel = new Cpanel("视频监控", "Daily traffic flow", mainPanel);

		setOpaque(false);
		setPreferredSize(new Dimension(width, height));
//		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);

	}

	private Component monitorPanel(String name) {
		JPanel cellPanel = new JPanel();
		cellPanel.setLayout(new BorderLayout());
		cellPanel.setName(name);
		cellPanel.setOpaque(false);

		JLabel nameLabel = new JLabel();
		nameLabel.setForeground(Color.white);
		nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		JPanel setPanel = new JPanel();
		setPanel.setLayout(new BorderLayout());
		setPanel.setBackground(new Color(51, 51, 51));
//		setPanel.setOpaque(false);
		setPanel.add(nameLabel, BorderLayout.WEST);
		
		JLabel label = new JLabel("<html><body>暂无视频源或<br>没有视频信号<body></html>");
		label.setFont(CyFont.puHuiTi(CyFont.Medium, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setBackground(Color.white);
		label.setOpaque(false);
		
		Panel palyPanel = new Panel();
		palyPanel.setCursor(new Cursor(12));
		palyPanel.setName(name);
		palyPanel.setLayout(new BorderLayout());
		palyPanel.add(label, BorderLayout.CENTER);
		palyPanel.addMouseListener(this);
		cellPanel.add(setPanel, BorderLayout.NORTH);
		cellPanel.add(palyPanel, BorderLayout.CENTER);
		return cellPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	


}

