package com.sunlue.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.sunlue.env_monitor.Server;

public class East {
	public static JFrame jf;
	private static JTextArea contentArea;
	
	private static JTextField clientMaxTxt;
	private static JTextField portTxt;
	
	private static JPanel northPanel;
	private static JScrollPane leftPanel;
	private static JScrollPane rightPanel;
	private static JSplitPane centerSplit;
	private static JButton btnStart;
	private static JButton btnStop;
	private static JButton btnClear;
	private static JList userList;
	private static JList allUserList;
	private static DefaultListModel allListModel;
	
	private static Server server;

	public static JPanel init(JFrame jf) {
		East.jf = jf;
		return init();
	}

	public static JPanel init() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(Frame.width / 2 - 5 / 2, Frame.height));
		panel.setBackground(Color.black);
		panel.setLayout(new BorderLayout());

		JLabel cliectMaxlablel = new JLabel("上限:");
		cliectMaxlablel.setBounds(10, 0, 42, 30);
		cliectMaxlablel.setFont(new Font("微软雅黑",1,16));
		clientMaxTxt = new JTextField("0");
		clientMaxTxt.setBounds(60, 0, 50, 30);
		clientMaxTxt.setPreferredSize(new Dimension(30,30));
		
		JLabel portLabel=new JLabel("端口:");
		portLabel.setBounds(115, 0, 42, 30);
		portLabel.setFont(new Font("微软雅黑",1,16));
		portTxt = new JTextField("8050");
		portTxt.setBounds(160, 0, 50, 30);
		portTxt.setPreferredSize(new Dimension(30,30));

		btnStart = new JButton("启动");
		btnStart.setBounds(215, 0, 80, 32);
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("start socket");
//				btnStop.setEnabled(true);
//				btnStart.setEnabled(false);
				server = new Server(Integer.parseInt(portTxt.getText()));
				server.start();
			}
		});
		btnStop = new JButton("停止");
		btnStop.setBounds(300, 0, 80, 32);
//		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("stop socket");
//				btnStop.setEnabled(false);
//				btnStart.setEnabled(true);
				insert("服务已停止");
			}
		});
		btnClear = new JButton("清空接收");
		btnClear.setBounds(390, 0, 100, 32);
		btnClear.setPreferredSize(new Dimension(100,20));
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentArea.setText("");
			}
		});
		
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(Frame.width/2-5/2,30));
		northPanel.setLayout(null);
		northPanel.add(cliectMaxlablel);
		northPanel.add(clientMaxTxt);
		northPanel.add(portLabel);
		northPanel.add(portTxt);
		northPanel.add(btnStart);
		northPanel.add(btnStop);
		northPanel.add(btnClear);

		panel.add(northPanel, BorderLayout.NORTH);
		

		allListModel = new DefaultListModel();
		userList = new JList(allListModel);
		leftPanel = new JScrollPane(userList);
		leftPanel.setBorder(new TitledBorder("环境检测仪客户端列表"));
		
		contentArea = new JTextArea();
		contentArea.setEditable(false);
		contentArea.setForeground(Color.blue);
		rightPanel = new JScrollPane(contentArea);
		rightPanel.setBorder(new TitledBorder("消息显示"));
		
		centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		centerSplit.setDividerLocation(150);

		panel.add(centerSplit,BorderLayout.CENTER);
		
		
		return panel;
	}
	
	public static void insert(String text) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		contentArea.insert("【"+date + "】" + text + "\r\n", 0);
	}

	public static void append(String text) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		contentArea.append("【"+date + "】" + ":" + text + "\r\n");
	}

}
