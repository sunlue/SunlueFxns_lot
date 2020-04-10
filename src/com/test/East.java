package com.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.BindException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.sunlue.env_monitor.Server;
import com.sunlue.gui.Frame;

public class East {
	public static JFrame jf;
	public static JTextArea contentArea;

	public static JTextField clientMaxTxt;
	public static JTextField portTxt;

	public static JPanel northPanel;
	public static JScrollPane leftPanel;
	public static JScrollPane rightPanel;
	public static JSplitPane centerSplit;
	public static JButton btnStart;
	public static JButton btnClear;
	public static JList userList;
	public static JList allUserList;
	public static DefaultListModel allListModel;

	private static Server server;

	private static boolean isStart = false;

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
		cliectMaxlablel.setFont(new Font("微软雅黑", 1, 16));
		clientMaxTxt = new JTextField("0");
		clientMaxTxt.setBounds(60, 0, 50, 30);
		clientMaxTxt.setPreferredSize(new Dimension(30, 30));

		JLabel portLabel = new JLabel("端口:");
		portLabel.setBounds(115, 0, 42, 30);
		portLabel.setFont(new Font("微软雅黑", 1, 16));
		portTxt = new JTextField("8050");
		portTxt.setBounds(160, 0, 50, 30);
		portTxt.setPreferredSize(new Dimension(30, 30));
		portTxt.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				char keyCh = e.getKeyChar();
				if (keyCh < '0' || keyCh > '9') {
					if (keyCh != ' ') {
						e.setKeyChar('\0');
					}
				}
			}
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

		});

		btnStart = new JButton("启动");
		btnStart.setBounds(215, 0, 100, 32);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isStart) {
					JOptionPane.showMessageDialog(jf, "服务器已处于启动状态，不要重复启动！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				btnStart.setEnabled(false);
				btnStart.setText("服务已启动");
				try {
					server = new Server(Integer.parseInt(portTxt.getText()));
					server.start();
					isStart = true;
				} catch (BindException | NumberFormatException e1) {
					e1.printStackTrace();
					isStart = false;
				}
			}
		});

		btnClear = new JButton("清空消息");
		btnClear.setBounds(320, 0, 100, 32);
		btnClear.setPreferredSize(new Dimension(100, 20));
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentArea.setText("");
			}
		});

		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(Frame.width / 2 - 5 / 2, 30));
		northPanel.setLayout(null);
		northPanel.add(cliectMaxlablel);
		northPanel.add(clientMaxTxt);
		northPanel.add(portLabel);
		northPanel.add(portTxt);
		northPanel.add(btnStart);
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

		panel.add(centerSplit, BorderLayout.CENTER);

		return panel;
	}

	public static void insert(String text) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		contentArea.insert("【" + date + "】" + text + "\r\n", 0);
	}

	public static void append(String text) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		contentArea.append("【" + date + "】" + ":" + text + "\r\n");
	}

}
