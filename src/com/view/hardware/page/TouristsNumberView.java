package com.view.hardware.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
 * 环境检测仪页面
 * 
 * @author xiebing
 *
 */
public class TouristsNumberView extends JPanel {
	private static final long serialVersionUID = 1L;

	public static Section ini = null;
	public static int width = Frame.width - Module.width;
	public static int height = Frame.height - Main.headerHeight;
	public static int clientWidth = 188;

	public static JPanel headPanel;
	public static JPanel clientPanel;
	public static Box clientBox;
	public static JPanel msgPanel;
	public static JTextArea msgTxtArea;

	public static JLabel portTxt;
	public static JTextField portNum;
	public static JButton startBtn;
	public static JButton stopBtn;
	public static JLabel HbGapTxt;
	public static JTextField HbGapNum;
	public static JButton clearBtn;
	public static JLabel lastRepTimeTxt;
	public static JTextField lastRepTimeVal;
	public static JLabel syncTimeTxt;
	public static JTextField syncTimeVal;

	public static Map<String, Panel> clientComponent = new HashMap<String, Panel>();
	public static Map<String, Map<String, String>> update = new HashMap<String, Map<String, String>>();

	public TouristsNumberView() {
		ini = Util.getIni().get("tourists_number");
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
		portNum = new JTextField(ini.get("port", "8010"));
		startBtn = new JButton("启动");
		stopBtn = new JButton("停止");
		clearBtn = new JButton("清空消息");
		HbGapTxt = new JLabel("心跳时间");
		HbGapNum = new JTextField(ini.get("HbGap", "30"));
		lastRepTimeTxt = new JLabel("最后上传时间");
		lastRepTimeVal = new JTextField();
		syncTimeTxt = new JLabel("系统时间");
		syncTimeVal = new JTextField();

		portTxt.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		portNum.setPreferredSize(new Dimension(38, 26));
		portNum.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		portNum.setHorizontalAlignment(SwingConstants.CENTER);
		portNum.setBounds(0, 0, 50, 30);

		portNum.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					Integer value = Integer.parseInt(portNum.getText());
					Map<String, String> data = new HashMap<String, String>();
					data.put("port", String.valueOf(value));
					update.put("tourists_number", data);
					Util.updateIni(update);
				} catch (Exception e2) {
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					Integer value = Integer.parseInt(portNum.getText());
					Map<String, String> data = new HashMap<String, String>();
					data.put("port", String.valueOf(value));
					update.put("tourists_number", data);
					Util.updateIni(update);
				} catch (Exception e2) {
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		portNum.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyCh = e.getKeyChar();
				if (keyCh < '0' || keyCh > '9') {
					if (keyCh != ' ') {
						e.setKeyChar('\0');
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		HbGapTxt.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		HbGapNum.setPreferredSize(new Dimension(38, 26));
		HbGapNum.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		HbGapNum.setHorizontalAlignment(SwingConstants.CENTER);
		HbGapNum.setBounds(0, 0, 50, 30);

		HbGapNum.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					Integer value = Integer.parseInt(HbGapNum.getText());
					Map<String, String> data = new HashMap<String, String>();
					data.put("HbGap", String.valueOf(value));
					update.put("tourists_number", data);
					Util.updateIni(update);
				} catch (Exception e2) {
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					Integer value = Integer.parseInt(HbGapNum.getText());
					Map<String, String> data = new HashMap<String, String>();
					data.put("HbGap", String.valueOf(value));
					update.put("tourists_number", data);
					Util.updateIni(update);
				} catch (Exception e2) {
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		HbGapNum.addKeyListener(new KeyListener() {
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

		startBtn.setCursor(new Cursor(12));
		startBtn.setFocusPainted(false);
		startBtn.setPreferredSize(new Dimension(76, 26));
		startBtn.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
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

					if (port == Integer.parseInt(ini.get("port")) && ini.get("isStart") == "true") {
						throw new Exception("服务器已启动！");
					}

					new Server(port).start();

					startBtn.setEnabled(false);
					startBtn.setText("已启动");
					startBtn.setVisible(false);
					stopBtn.setText("停止");
					stopBtn.setEnabled(true);
					stopBtn.setVisible(true);
					portNum.setEditable(false);

					Map<String, String> data = new HashMap<String, String>();
					data.put("isStart", "true");
					update.put("tourists_number", data);
					Util.updateIni(update);

					Log.write("启动服务器成功");
					insertMsg("服务器已启动");
				} catch (Exception e1) {
					Log.write("启动服务器错误：" + e1.getMessage(), Log.Error);
					Layer.alert(e1.getMessage(), 260, 160);
				}

			}
		});

		stopBtn.setCursor(new Cursor(12));
		stopBtn.setFocusPainted(false);
		stopBtn.setPreferredSize(new Dimension(76, 26));
		stopBtn.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
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
					new Server(port).stop();
					stopBtn.setEnabled(false);
					stopBtn.setText("已停止");
					stopBtn.setVisible(false);
					startBtn.setText("启动");
					startBtn.setEnabled(true);
					startBtn.setVisible(true);
					portNum.setEditable(true);
					Map<String, String> data = new HashMap<String, String>();
					data.put("isStart", "false");
					update.put("tourists_number", data);
					Util.updateIni(update);
					Log.write("停止服务器成功");
					insertMsg("服务器已停止");
				} catch (Exception e2) {
					Log.write("停止服务器错误：" + e2.getMessage(), Log.Error);
					Layer.alert(e2.getMessage(), 260, 160);
				}
			}
		});

		clearBtn.setCursor(new Cursor(12));
		clearBtn.setFocusPainted(false);
		clearBtn.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearMsg("");
			}
		});

		lastRepTimeTxt.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));

		lastRepTimeVal.setPreferredSize(new Dimension(128, 26));
		lastRepTimeVal.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		lastRepTimeVal.setText(ini.get("lastRepTime", ""));
		lastRepTimeVal.setEditable(false);
		lastRepTimeVal.setBackground(Color.white);
		lastRepTimeVal.setForeground(Color.BLACK);

		syncTimeTxt.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		syncTimeVal.setPreferredSize(new Dimension(128, 26));
		syncTimeVal.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		syncTimeVal.setText(Util.getDateTime());
		syncTimeVal.setHorizontalAlignment(SwingConstants.CENTER);
		syncTimeVal.setEditable(false);
		syncTimeVal.setBackground(Color.white);
		syncTimeVal.setForeground(Color.BLACK);
		// 定时器，自动刷新时间
		Timer timer = new Timer(1000, new ActionListener() {
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
		headPanel.add(HbGapTxt);
		headPanel.add(HbGapNum);
		headPanel.add(lastRepTimeTxt);
		headPanel.add(lastRepTimeVal);
		headPanel.add(syncTimeTxt);
		headPanel.add(syncTimeVal);

		return headPanel;
	}

	private JScrollPane client() {
		clientPanel = new JPanel();
		clientPanel.setBackground(new Color(245, 245, 245));
		clientBox = Box.createVerticalBox();
		clientPanel.add(clientBox);
		JScrollPane ScrollPane = new JScrollPane(clientPanel);
		return ScrollPane;
	}

	private JPanel message() {

		msgPanel = new JPanel();
		msgTxtArea = new JTextArea();

		msgTxtArea.setEditable(false);
		msgTxtArea.setMargin(new Insets(5, 5, 5, 5));
		msgTxtArea.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		msgTxtArea.setForeground(new Color(51, 51, 51));
		msgTxtArea.setText(Util.getMsg("touristrFlow"));
		msgTxtArea.setBackground(new Color(245, 245, 245));
		msgTxtArea.setLineWrap(true);
		JScrollPane scrollPanel = new JScrollPane(msgTxtArea);

		msgPanel.setLayout(new BorderLayout());
		msgPanel.add(scrollPanel, BorderLayout.CENTER);

		return msgPanel;
	}

	public static void insertMsg(String msg) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		msgTxtArea.insert("【" + date + "】" + msg + "\r\n", 0);
		Util.setMsg("touristrNumber", msgTxtArea.getText());
	}

	public static void clearMsg(String msg) {
		msgTxtArea.setText("");
		Util.setMsg("touristrNumber", msgTxtArea.getText());
	}

	/**
	 * 添加一个客户端面板
	 * @param client
	 */
	public static void addClient(Client client) {
		String name = client.getName();
		insertMsg(name + "上线了!");
		Panel clientPanel = clientItem(name);
		clientComponent.put(name, clientPanel);
		clientBox.add(clientPanel);
		SwingUtilities.updateComponentTreeUI(clientBox);
	}

	/**
	 * 删除一个客户端面板
	 * @param client
	 */
	public static void removeClient(Client client) {
		String name = client.getName();
		insertMsg(name + "下线了!");
		Panel clientPanel = clientComponent.get(name);
		clientBox.remove(clientPanel);
		clientComponent.remove(name);
		SwingUtilities.updateComponentTreeUI(clientBox);
	}

	/**
	 * 生成一个客户端面板
	 * @param name
	 * @return
	 */
	protected static Panel clientItem(String name) {
		JLabel label = new JLabel(name);
		label.setBounds(5, 0, width - 10, 32);
		label.setFont(CyFont.PuHuiTi(CyFont.Bold, 12));
		label.setVerticalAlignment(SwingConstants.CENTER);
		Panel panel = new Panel();
		panel.setName(name);
		panel.setCursor(new Cursor(12));
		panel.add(label);
		return panel;
	}

}
