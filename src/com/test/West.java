package com.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

import com.sunlue.gui.Frame;

public class West {
	public static JFrame jf;
	private static JTextArea contentArea;
	
	private static JTextField clientMaxTxt;
	private static JTextField portTxt;
	
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

	@SuppressWarnings("unchecked")
	public static JPanel init() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(Frame.width / 2 - 5 / 2, Frame.height));
		panel.setBackground(Color.black);
		panel.setLayout(new BorderLayout());

		JLabel cliectMaxlablel = new JLabel("上限:");
		cliectMaxlablel.setBounds(10, 0, 42, 30);
		cliectMaxlablel.setFont(new Font("微软雅黑",1,16));
		clientMaxTxt = new JTextField("20");
		clientMaxTxt.setBounds(60, 0, 50, 30);
		clientMaxTxt.setPreferredSize(new Dimension(30,30));
		
		JLabel portLabel=new JLabel("端口:");
		portLabel.setBounds(115, 0, 42, 30);
		portLabel.setFont(new Font("微软雅黑",1,16));
		portTxt = new JTextField("502");
		portTxt.setBounds(160, 0, 50, 30);
		portTxt.setPreferredSize(new Dimension(30,30));

		btnStart = new JButton("启动");
		btnStart.setBounds(215, 0, 80, 32);
		btnStop = new JButton("停止");
		btnStop.setBounds(300, 0, 80, 32);
		btnStop.setEnabled(false);
		btnClear = new JButton("清空接收");
		btnClear.setBounds(390, 0, 100, 32);
		btnClear.setPreferredSize(new Dimension(100,20));
		
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
		leftPanel.setBorder(new TitledBorder("客流统计客户端列表"));
		
		contentArea = new JTextArea();
		contentArea.setEditable(false);
		contentArea.setForeground(Color.blue);
		rightPanel = new JScrollPane(contentArea);
		rightPanel.setBorder(new TitledBorder("消息显示"));
		
		centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		centerSplit.setDividerLocation(150);

		panel.add(centerSplit,BorderLayout.CENTER);
		
		
		southPanel=new JPanel();
		southPanel.setLayout(new GridLayout(1,6));
		txtHbGap = new JTextField("30");
		txtLastRepTime = new JTextField();
		txtSyncTime = new JTextField();
		
		long timemillis = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		txtSyncTime.setText(df.format(new Date(timemillis)));
		String dateTime = readLastRepTime();
		if (dateTime.length() > 0) {
			txtLastRepTime.setText(dateTime);
		} else {
			txtLastRepTime.setText(txtSyncTime.getText());
		}
		
		southPanel.add(new JLabel("心跳时间间隔"));
		southPanel.add(txtHbGap);
		southPanel.add(new JLabel("最后上传时间"));
		southPanel.add(txtLastRepTime);
		southPanel.add(new JLabel("系统同步时间"));
		southPanel.add(txtSyncTime);
		
		
		panel.add(southPanel,BorderLayout.SOUTH);
		
		
		return panel;
	}

	private static final String FILE_NAME = "lastRepTime.txt";
	// 读取最后上传时间
	public static String readLastRepTime() {
		String dateTime = "";
		try {
			File filename = new File(FILE_NAME);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			dateTime = br.readLine();
			System.out.println("dateTime: " + dateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
	}

	// 写最后上传时间
	public void writeLastRepTime(String lastRepTime) {
		File writename = new File(FILE_NAME); // 相对路径，如果没有则要建立一个新的output。txt文件
		try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(lastRepTime + "\r\n"); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (IOException e) {
			e.printStackTrace();
		} // 创建新文件
	}
	
}




















