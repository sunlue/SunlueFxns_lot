package com.view.hardware.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.ini4j.Profile.Section;

import com.server.Client;
import com.server.Server;
import com.util.CyFont;
import com.util.Layer;
import com.util.Log;
import com.util.Util;
import com.view.Frame;
import com.view.Main;
import com.view.Module;

/**
 * 环境检测仪视图
 * 
 * @author xiebing
 *
 */
public class EnvMonitorView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static Section ini = Util.getIni().get("env_monitor");
	
	public static JPanel headPanel;
	public static JPanel clientPanel;
	public static Box clientBox;
	public static JPanel msgPanel;
	public static JTextArea msgTxtArea;
	public static int width = Frame.width - Module.width;
	public static int height = Frame.height - Main.headerHeight;
	public static int clientWidth = 180;

	public static JLabel portTxt;
	public static JTextField portNum;
	public static JButton startBtn;
	public static JButton stopBtn;
	public static JButton clearBtn;
	public static JLabel syncTimeTxt;
	public static JTextField syncTimeVal;
	
	public static Map<String, Panel> clientComponent = new HashMap<String, Panel>();
	public static Map<String, Map<String, String>> update = new HashMap<String, Map<String, String>>();

	public EnvMonitorView() {
		setLayout(new BorderLayout());
		add(header(), BorderLayout.NORTH);
		JSplitPane center = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, client(), message());
		center.setDividerLocation(clientWidth);
		center.setDividerSize(6);
		center.setContinuousLayout(true);
		add(center, BorderLayout.CENTER);
	}

	private JPanel header() {
		portTxt = new JLabel("端口");
		portNum = new JTextField(ini.get("port","8020"));
		startBtn = new JButton("启动");
		stopBtn = new JButton("停止");
		clearBtn = new JButton("清空消息");
		syncTimeTxt = new JLabel("系统时间");
		
		portTxt.setFont(CyFont.puHuiTi(CyFont.Regular, 12));
		portNum.setPreferredSize(new Dimension(38, 26));
		portNum.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		portNum.setHorizontalAlignment(SwingConstants.CENTER);
		portNum.setBounds(0, 0, 50, 30);
		
		startBtn.setFont(CyFont.puHuiTi(CyFont.Regular, 12));
		startBtn.setCursor(new Cursor(12));
		startBtn.setFocusPainted(false);
		startBtn.setPreferredSize(new Dimension(76, 26));
		startBtn.setVisible(!Boolean.parseBoolean(ini.get("isStart")));
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int port;

				try {
					try {
						port = Integer.parseInt(portNum.getText());
					} catch (Exception el) {
						throw new Exception("端口号为正整数");
					}

					if (port <= 0) {
						throw new Exception("端口号为正整数");
					}
					new Server(port).start();
					startBtn.setEnabled(false);
					startBtn.setText("已启动");
					startBtn.setVisible(false);
					stopBtn.setText("停止");
					stopBtn.setEnabled(true);
					stopBtn.setVisible(true);
					Log.write("启动服务器成功");
					insertMsg("启动成功");
				} catch (Exception e1) {
					Log.write("启动服务器错误：" + e1.getMessage(), Log.Error);
					Layer.alert(e1.getMessage(), 260, 160);
				}

			}
		});

		stopBtn.setFont(CyFont.puHuiTi(CyFont.Regular, 12));
		stopBtn.setCursor(new Cursor(12));
		stopBtn.setFocusPainted(false);
		stopBtn.setPreferredSize(new Dimension(76, 26));
		stopBtn.setVisible(Boolean.parseBoolean(ini.get("isStart")));
		stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int port;
				try {
					try {
						port = Integer.parseInt(portNum.getText());
					} catch (Exception el) {
						throw new Exception("端口号为正整数");
					}

					if (port <= 0) {
						throw new Exception("端口号为正整数");
					}
//					Server.stop();
					stopBtn.setEnabled(false);
					stopBtn.setText("已停止");
					stopBtn.setVisible(false);
					startBtn.setText("启动");
					startBtn.setEnabled(true);
					startBtn.setVisible(true);
					insertMsg("已停止");
				} catch (Exception e2) {
					Log.write("停止服务器错误：" + e2.getMessage(), Log.Error);
					Layer.alert(e2.getMessage(), 260, 160);
				}
			}
		});
		
		clearBtn.setFont(CyFont.puHuiTi(CyFont.Regular, 12));
		clearBtn.setCursor(new Cursor(12));
		clearBtn.setFocusPainted(false);
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearMsg("");
			}
		});
		
		syncTimeTxt.setFont(CyFont.puHuiTi(CyFont.Regular, 12));
		syncTimeVal = new JTextField();
		syncTimeVal.setPreferredSize(new Dimension(138, 26));
		syncTimeVal.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		syncTimeVal.setText(Util.getDateTime());
		syncTimeVal.setHorizontalAlignment(SwingConstants.CENTER);
		syncTimeVal.setEnabled(false);
		syncTimeVal.setBackground(Color.white);
		syncTimeVal.setForeground(Color.BLACK);
		// 定时器，自动刷新时间
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				syncTimeVal.setText(Util.getDateTime());
			}
		});
		timer.start();

		headPanel = new JPanel();
		headPanel.setPreferredSize(new Dimension(width, 36));
		headPanel.setBackground(new Color(220, 220, 220));
		headPanel.add(portTxt);
		headPanel.add(portNum);
		headPanel.add(startBtn);
		headPanel.add(stopBtn);
		headPanel.add(clearBtn);
		headPanel.add(syncTimeTxt);
		headPanel.add(syncTimeVal);

		return headPanel;
	}

	private JScrollPane client() {
		clientPanel = new JPanel();
		clientPanel.setBackground(new Color(245, 245, 245));
		clientBox = Box.createVerticalBox();
		clientPanel.add(clientBox);
		JScrollPane scrollPane = new JScrollPane(clientPanel);
		return scrollPane;
	}

	private JPanel message() {

		msgPanel = new JPanel();
		msgTxtArea = new JTextArea();

		msgTxtArea.setEditable(false);
		msgTxtArea.setMargin(new Insets(5, 5, 5, 5));
		msgTxtArea.setFont(CyFont.puHuiTi(CyFont.Regular, 12));
		msgTxtArea.setForeground(new Color(51, 51, 51));
		msgTxtArea.setText(Util.getMsg("envMonitor"));
		msgTxtArea.setBackground(new Color(245,245,245));
		msgTxtArea.setLineWrap(true);
		JScrollPane scrollPanel = new JScrollPane(msgTxtArea);

		msgPanel.setLayout(new BorderLayout());
		msgPanel.add(scrollPanel, BorderLayout.CENTER);

		return msgPanel;
	}

	public static void insertMsg(String msg) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		msgTxtArea.insert("【" + date + "】" + msg + "\r\n", 0);
		Util.setMsg("envMonitor", msgTxtArea.getText());
	}

	public static void clearMsg(String msg) {
		msgTxtArea.setText("");
		Util.setMsg("envMonitor", msgTxtArea.getText());
	}

	public static void addClient(Client client) {
		String name = client.getName();
		insertMsg(name + "上线了!");
		Panel clientPanel = clientItem(name);
		clientComponent.put(name, clientPanel);
		clientBox.add(clientPanel);
		SwingUtilities.updateComponentTreeUI(clientBox);
	}

	public static void removeClient(Client client) {
		String name = client.getName();
		insertMsg(name + "下线了!");
		Panel clientPanel = clientComponent.get(name);
		clientBox.remove(clientPanel);
		clientComponent.remove(name);
		SwingUtilities.updateComponentTreeUI(clientBox);
	}

	protected static Panel clientItem(String name) {
		JLabel label = new JLabel(name);
		label.setBounds(5, 0, width - 10, 32);
		label.setFont(CyFont.puHuiTi(CyFont.Bold, 12));
		label.setVerticalAlignment(SwingConstants.CENTER);
		Panel panel = new Panel();
		panel.setName(name);
		panel.setCursor(new Cursor(12));
		panel.add(label);
		return panel;
	}

}
