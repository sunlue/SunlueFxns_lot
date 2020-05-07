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

/**
 * @author xiebing
 */
public class Access extends JPanel {
	private static final long serialVersionUID = 1L;

	public Access(Dimension preferredSize) {
		int width = (int) (preferredSize.getWidth());
		int height = (int) (preferredSize.getHeight());
		new Access(width, height);
	}

	public Access(int width, int height) {
		/** 网络流量 **/

		AccessChildPanel accessPanel = new AccessChildPanel("官网访问数", accessPanel());
		AccessChildPanel browsePanel = new AccessChildPanel("官网浏览量", browsePanel());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2));
		topPanel.setOpaque(false);
		topPanel.add(accessPanel);
		topPanel.add(browsePanel);

		AccessChildPanel weixinPanel = new AccessChildPanel("微信粉丝数", weixinPanel());

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 1));
		bottomPanel.setOpaque(false);
		bottomPanel.add(weixinPanel);

		setOpaque(false);
		setPreferredSize(new Dimension(width, height));
		setLayout(new GridLayout(2, 1));
		add(topPanel);
		add(bottomPanel);

	}

	private JPanel accessPanel() {
		JLabel lTxtLabel = new JLabel("昨日数");
		lTxtLabel.setForeground(Color.WHITE);
		lTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lTxtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lTxtLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel lNumLabel = new JLabel("0");
		lNumLabel.setForeground(Color.white);
		lNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		lNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lNumLabel.setVerticalAlignment(SwingConstants.CENTER);
		JPanel lItemPan = new JPanel();
		lItemPan.setLayout(new GridLayout(2, 1));
		lItemPan.setOpaque(false);
		lItemPan.add(lTxtLabel);
		lItemPan.add(lNumLabel);

		JLabel rTxtLabel = new JLabel("今日数");
		rTxtLabel.setForeground(Color.WHITE);
		rTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		rTxtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rTxtLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel rNumLabel = new JLabel("24");
		rNumLabel.setForeground(Color.white);
		rNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		rNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rNumLabel.setVerticalAlignment(SwingConstants.CENTER);
		JPanel rItemPan = new JPanel();
		rItemPan.setLayout(new GridLayout(2, 1));
		rItemPan.setOpaque(false);
		rItemPan.add(rTxtLabel);
		rItemPan.add(rNumLabel);

		JPanel mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new GridLayout(1, 2));
		mainPanel.add(lItemPan);
		mainPanel.add(rItemPan);
		return mainPanel;
	}

	private JPanel browsePanel() {
		JLabel lTxtLabel = new JLabel("昨日数");
		lTxtLabel.setForeground(Color.WHITE);
		lTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lTxtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lTxtLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel lNumLabel = new JLabel("0");
		lNumLabel.setForeground(Color.white);
		lNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		lNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lNumLabel.setVerticalAlignment(SwingConstants.CENTER);
		JPanel lItemPan = new JPanel();
		lItemPan.setLayout(new GridLayout(2, 1));
		lItemPan.setOpaque(false);
		lItemPan.add(lTxtLabel);
		lItemPan.add(lNumLabel);

		JLabel rTxtLabel = new JLabel("今日数");
		rTxtLabel.setForeground(Color.WHITE);
		rTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		rTxtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rTxtLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel rNumLabel = new JLabel("27");
		rNumLabel.setForeground(Color.white);
		rNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		rNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rNumLabel.setVerticalAlignment(SwingConstants.CENTER);
		JPanel rItemPan = new JPanel();
		rItemPan.setLayout(new GridLayout(2, 1));
		rItemPan.setOpaque(false);
		rItemPan.add(rTxtLabel);
		rItemPan.add(rNumLabel);

		JPanel mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new GridLayout(1, 2));
		mainPanel.add(lItemPan);
		mainPanel.add(rItemPan);
		return mainPanel;
	}

	private JPanel weixinPanel() {
		JLabel lTxtLabel = new JLabel("昨日新增");
		lTxtLabel.setForeground(Color.WHITE);
		lTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lTxtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lTxtLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel lNumLabel = new JLabel("0");
		lNumLabel.setForeground(Color.white);
		lNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		lNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lNumLabel.setVerticalAlignment(SwingConstants.CENTER);
		JPanel lItemPan = new JPanel();
		lItemPan.setLayout(new GridLayout(2, 1));
		lItemPan.setOpaque(false);
		lItemPan.add(lTxtLabel);
		lItemPan.add(lNumLabel);

		JLabel rTxtLabel = new JLabel("昨日净增");
		rTxtLabel.setForeground(Color.WHITE);
		rTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		rTxtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rTxtLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel rNumLabel = new JLabel("-1");
		rNumLabel.setForeground(Color.white);
		rNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		rNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rNumLabel.setVerticalAlignment(SwingConstants.CENTER);
		JPanel rItemPan = new JPanel();
		rItemPan.setLayout(new GridLayout(2, 1));
		rItemPan.setOpaque(false);
		rItemPan.add(rTxtLabel);
		rItemPan.add(rNumLabel);

		JLabel nTxtLabel = new JLabel("昨日取消关注");
		nTxtLabel.setForeground(Color.WHITE);
		nTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		nTxtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nTxtLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel nNumLabel = new JLabel("1");
		nNumLabel.setForeground(Color.white);
		nNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		nNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nNumLabel.setVerticalAlignment(SwingConstants.CENTER);
		JPanel nItemPan = new JPanel();
		nItemPan.setLayout(new GridLayout(2, 1));
		nItemPan.setOpaque(false);
		nItemPan.add(nTxtLabel);
		nItemPan.add(nNumLabel);

		JLabel wTxtLabel = new JLabel("昨日累计");
		wTxtLabel.setForeground(Color.WHITE);
		wTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		wTxtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wTxtLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel wNumLabel = new JLabel("219");
		wNumLabel.setForeground(Color.white);
		wNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		wNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wNumLabel.setVerticalAlignment(SwingConstants.CENTER);
		JPanel wItemPan = new JPanel();
		wItemPan.setLayout(new GridLayout(2, 1));
		wItemPan.setOpaque(false);
		wItemPan.add(wTxtLabel);
		wItemPan.add(wNumLabel);

		JPanel mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new GridLayout(1, 2));
		mainPanel.add(lItemPan);
		mainPanel.add(rItemPan);
		mainPanel.add(nItemPan);
		mainPanel.add(wItemPan);
		return mainPanel;
	}

}

class AccessChildPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public AccessChildPanel(JPanel mainPanel) {
		this.handle("", mainPanel);
	}

	public AccessChildPanel(String tit, JPanel mainPanel) {
		this.handle(tit, mainPanel);
	}

	private void handle(String tit, JPanel mainPanel) {
		JLabel titleLabel = new JLabel(tit);
		titleLabel.setForeground(Color.white);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel subTitLabel = new JLabel(Util.getImageIcon("accessGif.png", 72, 16));
		subTitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		subTitLabel.setVerticalAlignment(SwingConstants.CENTER);
		JPanel headerPanel = new JPanel();
		headerPanel.setOpaque(false);
		headerPanel.setLayout(new BorderLayout());
		headerPanel.add(titleLabel, BorderLayout.WEST);
		headerPanel.add(subTitLabel, BorderLayout.EAST);

		JPanel linePanel = new JPanel();
		linePanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 2));
		linePanel.setBackground(new Color(145, 233, 255, 80));
		headerPanel.add(linePanel, BorderLayout.SOUTH);

		setBorder(BorderFactory.createLineBorder(new Color(145, 233, 255, 80), 2, true));
//		setOpaque(false);
		setBackground(new Color(7, 10, 85));
		setLayout(new BorderLayout());
		add(headerPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
	}

}
