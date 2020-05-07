package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.util.Util;
import com.view.datav.Cpanel;

/**
 * @author xiebing
 */
public class FeedBack extends JPanel {
	private static final long serialVersionUID = 1L;

	public FeedBack(Dimension preferredSize) {
		int width = (int) (preferredSize.getWidth());
		int height = (int) (preferredSize.getHeight());
		new FeedBack(width, height);
	}

	public FeedBack(int width, int height) {
		/** 意见反馈 **/

		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(width, height));
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new GridLayout(6, 1));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 16, 10));
		String[] s = new String[] { "服务", "安全", "交通", "物价", "卫生", "美食" };

		for (int i = 0; i < s.length; i++) {
			JPanel cellPanel = new JPanel();
			cellPanel.setLayout(new BorderLayout());
			JLabel name = new JLabel(s[i]);
			name.setHorizontalAlignment(SwingConstants.CENTER);
			name.setHorizontalAlignment(SwingConstants.CENTER);
			name.setForeground(Color.white);
			name.setPreferredSize(new Dimension(50, 30));
			name.setFont(new Font("微软雅黑", Font.BOLD, 16));
			cellPanel.add(name, BorderLayout.WEST);
			JPanel star = new JPanel(new GridLayout(1, 10));
			star.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			star.setOpaque(false);
			int scoreNum = Util.random(0, 10);
			for (int j = 1; j <= 10; j++) {
				if (j <= scoreNum) {
					star.add(new JLabel(Util.getImageIcon("_star.png", 24, 24)));
				} else {
					star.add(new JLabel(Util.getImageIcon("star.png", 24, 24)));
				}
			}
			cellPanel.add(star, BorderLayout.CENTER);
			JLabel score = new JLabel(scoreNum + "分");
			score.setPreferredSize(new Dimension(50, 30));
			score.setForeground(new Color(245, 166, 35));
			score.setFont(new Font("微软雅黑", Font.BOLD, 16));
			score.setHorizontalAlignment(SwingConstants.CENTER);
			score.setHorizontalAlignment(SwingConstants.CENTER);
			cellPanel.add(score, BorderLayout.EAST);
			cellPanel.setOpaque(false);
			mainPanel.add(cellPanel);
		}

		Cpanel panel = new Cpanel("意见反馈", "Customer feedback", mainPanel);

		setOpaque(false);
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);

	}

}
