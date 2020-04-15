package com.sunlue.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.sunlue.Container;
import com.sunlue.Frame;
import com.sunlue.Main;
import com.sunlue.Module;

/**
 * 环境检测仪页面
 * 
 * @author xiebing
 *
 */
public class EnvMonitor {
	public static JPanel contentPanel;
	public static JPanel headPanel;
	public static JPanel headAction;
	public static JPanel msgPanel;
	public static JTextArea msgTxtArea;
	public static int width = Frame.width - Module.width;
	public static int height = Container.height - Main.headerHeight;

	public JPanel view() {
		contentPanel = new JPanel();
		headPanel = new JPanel();
		headAction = new JPanel();
		msgPanel = new JPanel();
		msgTxtArea = new JTextArea();

		JLabel portTxt = new JLabel("端口");
		JTextField portNum = new JTextField("8060");
		portNum.setPreferredSize(new Dimension(38, 26));
		portNum.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		portNum.setHorizontalAlignment(SwingConstants.CENTER);
		portNum.setBounds(0, 0, 50, 30);

		JButton startBtn = new JButton("已启动");
		JButton stopBtn = new JButton("停止");
		JButton clearBtn = new JButton("清空消息");
		
		startBtn.setCursor(new Cursor(12));
		startBtn.setFocusPainted(false);
		startBtn.setPreferredSize(new Dimension(76,26));
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startBtn.setEnabled(false);
				startBtn.setText("已启动");
				stopBtn.setText("停止");
				stopBtn.setEnabled(true);
				insertMsg("已启动");
			}
		});
		
		
		stopBtn.setCursor(new Cursor(12));
		stopBtn.setEnabled(false);
		stopBtn.setFocusPainted(false);
		stopBtn.setPreferredSize(new Dimension(76,26));
		stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopBtn.setEnabled(false);
				stopBtn.setText("已停止");
				startBtn.setText("启动");
				startBtn.setEnabled(true);
				insertMsg("已停止");
			}
		});

		
		clearBtn.setCursor(new Cursor(12));
		clearBtn.setFocusPainted(false);
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				msgTxtArea.setText("");
			}
		});

		headPanel.setLayout(null);
		headAction.setBounds(5, 5, width - 10, 50);
		headAction.setLayout(new FlowLayout());
		headAction.add(portTxt);
		headAction.add(portNum);
		headAction.add(startBtn);
		headAction.add(stopBtn);
		headAction.add(clearBtn);

		headPanel.setPreferredSize(new Dimension(width, 46));
		headPanel.add(headAction);

		msgTxtArea.setEditable(false);
		msgTxtArea.setForeground(Color.blue);
		msgTxtArea.setMargin(new Insets(5, 5, 5, 5));
		msgTxtArea.setFont(new Font("微软雅黑", 1, 14));
		msgTxtArea.setForeground(new Color(51, 51, 51));
		JScrollPane scrollPanel = new JScrollPane(msgTxtArea);
		scrollPanel.setBorder(null);
		scrollPanel.setBounds(5, 0, width - 10, height - 51);

		msgPanel.setLayout(null);
		msgPanel.add(scrollPanel);

		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(headPanel, BorderLayout.NORTH);
		contentPanel.add(msgPanel, BorderLayout.CENTER);

		return contentPanel;
	}

	public static void insertMsg(String msg) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		msgTxtArea.insert("【" + date + "】" + msg + "\r\n", 0);
	}

}
