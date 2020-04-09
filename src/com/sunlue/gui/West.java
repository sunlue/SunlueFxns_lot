package com.sunlue.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class West {
	public static JFrame jf;
	private static JFrame frame;
	private static JTextArea contentArea;
	private static JTextField txtMsg;
	private static JTextField txtClientMax;
	private static JTextField txtPort;
	private static JTextField txtHbGap;
	private static JTextField txtLastRepTime;
	private static JTextField txtSyncTime;
	private static JPanel northPanel;
	private static JPanel southPanel;
	private static JScrollPane leftPanel;
	private static JScrollPane rightPanel;
	private static JSplitPane centerSplit;
	private static JButton btnStart;
	private static JButton btnStop;
	private static JButton btnClear;
	private static JList userList;
	private static JList allUserList;
	private static DefaultListModel allListModel;
	
	public static JPanel init(JFrame jf) {
		West.jf = jf;
		return init();
	}

	public static JPanel init() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(Frame.width / 2 - 5 / 2, Frame.height));
		panel.setBackground(Color.black);
		panel.setLayout(new BorderLayout());

		txtClientMax = new JTextField("20");
		txtPort = new JTextField("502");
		txtHbGap = new JTextField("30");
		txtLastRepTime = new JTextField();
		txtSyncTime = new JTextField();
		btnStart = new JButton("启动");
		btnStop = new JButton("停止");
		btnStop.setEnabled(false);
		btnClear = new JButton("清空接收");
		btnClear.setPreferredSize(new Dimension(100,20));
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(1, 5));
//		northPanel.add(new JLabel("上限:"));
		northPanel.add(txtClientMax);
//		northPanel.add(new JLabel("端口:"));
		northPanel.add(txtPort);
		northPanel.add(btnStart);
		northPanel.add(btnStop);
		northPanel.add(btnClear);

		allListModel = new DefaultListModel();
		userList = new JList(allListModel);
		JScrollPane leftPanel = new JScrollPane(userList);
		leftPanel.setBorder(new TitledBorder("客户端列表"));
		
		contentArea = new JTextArea();
		contentArea.setEditable(false);
		contentArea.setForeground(Color.blue);
		JScrollPane rightPanel = new JScrollPane(contentArea);
		rightPanel.setBorder(new TitledBorder("消息显示"));
		
		
		JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		centerSplit.setDividerLocation(150);

		panel.add(centerSplit,BorderLayout.CENTER);
		panel.add(northPanel, BorderLayout.NORTH);
		return panel;
	}
}




















