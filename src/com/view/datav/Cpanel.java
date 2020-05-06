package com.view.datav;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author xiebing
 */
public class Cpanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public Cpanel(JPanel mainPanel) {
		this.handle("", "", mainPanel);
	}

	public Cpanel(String tit, String subTit, JPanel mainPanel) {
		this.handle(tit, subTit, mainPanel);
	}

	private void handle(String tit, String subTit, JPanel mainPanel) {
		JLabel titleLabel = new JLabel(tit);
		titleLabel.setForeground(Color.white);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		JLabel subTitLabel = new JLabel(subTit);
		subTitLabel.setForeground(new Color(255, 255, 255, 80));
		subTitLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
		subTitLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
		JPanel headerPanel = new JPanel();
		headerPanel.setOpaque(false);
		headerPanel.setLayout(new GridLayout(2, 1));
		headerPanel.add(titleLabel);
		headerPanel.add(subTitLabel);
		setBorder(BorderFactory.createLineBorder(new Color(145, 233, 255, 80), 2, true));
//		setOpaque(false);
		setBackground(new Color(7, 10, 85));
		setLayout(new BorderLayout());
		add(headerPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
	}

}
