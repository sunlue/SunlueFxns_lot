package com.sunlue;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Sgui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JPanel contentPane;
	public static JTextArea textArea;
	public static JScrollPane scrollPane;

	public Sgui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 800);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(204, 0, 0));
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("芳香南山环境检测仪套接字服务器");

		textArea = new JTextArea();
		textArea.setBounds(5, 5, 574, 750);
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(5, 5, 574, 750);

		contentPane.add(scrollPane);
	}

	public static void insert(String text) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		textArea.insert("【"+date + "】" + text + "\r\n", 0);
	}

	public static void append(String text) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		textArea.append("【"+date + "】" + ":" + text + "\r\n");
	}
}
