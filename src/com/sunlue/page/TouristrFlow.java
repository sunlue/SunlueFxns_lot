package com.sunlue.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.sunlue.Container;
import com.sunlue.Main;
import com.sunlue.tourists_flow.Client;
import com.sunlue.tourists_flow.ClientThread;
import com.sunlue.tourists_flow.Server;
import com.sunlue.util.CyFont;
import com.sunlue.util.Layer;
import com.sunlue.util.Util;

/**
 * 环境检测仪页面
 * 
 * @author xiebing
 *
 */
public class TouristrFlow extends JPanel {
	private static final long serialVersionUID = 1L;
	public static JPanel headPanel;
	public static JPanel clientPanel;
	public static JPanel msgPanel;
	public static JTextArea msgTxtArea;
	public static int width = Container.width;
	public static int height = Container.height - Main.headerHeight;
	public static int clientWidth = 180;

	public TouristrFlow() {
		setLayout(new BorderLayout());
		add(header(), BorderLayout.NORTH);

		JSplitPane center = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, client(), message());
		center.setDividerLocation(clientWidth);
		center.setDividerSize(6);
		center.setContinuousLayout(true);
		add(center, BorderLayout.CENTER);
	}

	private JPanel header() {
		JLabel portTxt = new JLabel("端口");
		portTxt.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		JTextField portNum = new JTextField("8050");
		portNum.setPreferredSize(new Dimension(38, 26));
		portNum.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		portNum.setHorizontalAlignment(SwingConstants.CENTER);
		portNum.setBounds(0, 0, 50, 30);

		JButton startBtn = new JButton("启动");
		startBtn.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		JButton stopBtn = new JButton("停止");
		stopBtn.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		JButton clearBtn = new JButton("清空消息");
		clearBtn.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));

		JLabel HbGapTxt = new JLabel("心跳时间");
		HbGapTxt.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		JTextField HbGapNum = new JTextField("30");
		HbGapNum.setPreferredSize(new Dimension(38, 26));
		HbGapNum.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		HbGapNum.setHorizontalAlignment(SwingConstants.CENTER);
		HbGapNum.setBounds(0, 0, 50, 30);
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

//					startBtn.setEnabled(false);
					startBtn.setText("已启动");
					stopBtn.setText("停止");
					stopBtn.setEnabled(true);
//					Util.log("启动服务器成功", Util.LogInfo);
					insertMsg("启动成功");
				} catch (Exception e1) {
					Util.log("启动服务器错误：" + e1.getMessage(), Util.LogError);
					Layer.alert(e1.getMessage(), 260, 160);
				}

			}
		});

		stopBtn.setCursor(new Cursor(12));
//		stopBtn.setEnabled(false);
		stopBtn.setFocusPainted(false);
		stopBtn.setPreferredSize(new Dimension(76, 26));
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
					Server.stop();
//					stopBtn.setEnabled(false);
					stopBtn.setText("已停止");
					startBtn.setText("启动");
					startBtn.setEnabled(true);
					insertMsg("已停止");
				} catch (Exception e2) {
//					Util.log("停止服务器错误：" + e2.getMessage(), Util.LogError);
					Layer.alert(e2.getMessage(), 260, 160);
				}
			}
		});

		clearBtn.setCursor(new Cursor(12));
		clearBtn.setFocusPainted(false);
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearMsg("");
			}
		});

		JLabel lastRepTimeTxt = new JLabel("最后上传时间");
		lastRepTimeTxt.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		JTextField lastRepTime = new JTextField();
		lastRepTime.setPreferredSize(new Dimension(128, 26));
		lastRepTime.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		JLabel syncTimeTxt = new JLabel("系统时间");
		syncTimeTxt.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		JTextField syncTime = new JTextField();
		syncTime.setPreferredSize(new Dimension(128, 26));
		syncTime.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		syncTime.setText(Util.getDateTime());
		syncTime.setHorizontalAlignment(SwingConstants.CENTER);
		// 定时器，自动刷新时间
		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				syncTime.setText(Util.getDateTime());
			}
		});
		timer.start();

		headPanel = new JPanel();
		headPanel.setPreferredSize(new Dimension(width, 46));
		headPanel.add(portTxt);
		headPanel.add(portNum);
		headPanel.add(startBtn);
		headPanel.add(stopBtn);
		headPanel.add(clearBtn);
		headPanel.add(HbGapTxt);
		headPanel.add(HbGapNum);
		headPanel.add(lastRepTimeTxt);
		headPanel.add(lastRepTime);
		headPanel.add(syncTimeTxt);
		headPanel.add(syncTime);

		return headPanel;
	}

	private JPanel client() {
		clientPanel = new JPanel();
		clientPanel.setBackground(new Color(236, 233, 231));
		clientPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		clientPanel.setBorder(null);
		return clientPanel;
	}

	private JPanel message() {

		msgPanel = new JPanel();
		msgTxtArea = new JTextArea();

		msgTxtArea.setEditable(false);
		msgTxtArea.setMargin(new Insets(5, 5, 5, 5));
		msgTxtArea.setFont(CyFont.PuHuiTi(CyFont.Regular, 12));
		msgTxtArea.setForeground(new Color(51, 51, 51));
		msgTxtArea.setText(Util.getMsg("touristrFlow"));
		msgTxtArea.setBackground(Color.white);
		JScrollPane scrollPanel = new JScrollPane(msgTxtArea);

		msgPanel.setLayout(new BorderLayout());
		msgPanel.add(scrollPanel, BorderLayout.CENTER);

		return msgPanel;
	}

	public static void insertMsg(String msg) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		msgTxtArea.insert("【" + date + "】" + msg + "\r\n", 0);
		Util.setMsg("touristrFlow", msgTxtArea.getText());
	}

	public static void clearMsg(String msg) {
		msgTxtArea.setText("");
		Util.setMsg("touristrFlow", msgTxtArea.getText());
	}

	public static void addClient(ClientThread client) {
		String name = client.getClient().getName();
		insertMsg(name + "上线了");
		clientPanel.add(clientItem(name));
		clientPanel.revalidate();
	}

	public static void removeClient(Client client) {
		System.out.println(client.getName() + "下线了");
		String name = client.getName();
		insertMsg(name + "下线了!");
		for (int i = 0; i < clientPanel.getComponentCount(); i++) {
			clientPanel.remove(clientPanel.getComponent(i));
		}
	}

	protected static Panel clientItem(String name) {
		Panel panel = new Panel();
		panel.setPreferredSize(new Dimension(clientWidth, 32));
		panel.setLayout(null);
		panel.setCursor(new Cursor(12));
		panel.setBackground(new Color(236, 233, 231));
		panel.setName(name);

		ImageIcon icon = new ImageIcon(Util.getResource("alarm.png"));
		icon.setImage(icon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
		JLabel iconLabel = new JLabel(icon);
		iconLabel.setBounds(0, 0, 32, 32);

		JLabel label = new JLabel(name);
		label.setBounds(32, 0, width - 32, 32);
		label.setFont(CyFont.PuHuiTi(CyFont.Bold, 12));
		label.setVerticalAlignment(SwingConstants.CENTER);

		panel.add(iconLabel);
		panel.add(label);
		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				Content.cutPage(label.getText(),new EnvMonitor().view());
				for (int i = 0; i < clientPanel.getComponentCount(); i++) {
					clientPanel.getComponent(i).setBackground(new Color(236, 233, 231));
				}
				panel.setBackground(new Color(222, 222, 222));
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		System.out.println(name);
		return panel;
	}

}
