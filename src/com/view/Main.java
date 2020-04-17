package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


import com.util.CyFont;
import com.util.Layer;
import com.util.Util;
import com.util.Layer.LayerCallback;

public class Main {
	public static int tempX;
	public static int tempY;
	public static int winX;
	public static int winY;
	public static int oldX;
	public static int oldY;
	public static JFrame jf;
	public static JPanel container;
	public static int width = Frame.width - Module.width;
	public static int headerHeight = 26;

	public static JPanel init(JFrame jf) {
		Main.jf = jf;
		return init();
	}

	private static JPanel init() {
		container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(header(), BorderLayout.NORTH);
		container.add(new Container("hardware"), BorderLayout.CENTER);

		Border b1 = BorderFactory.createEmptyBorder(1, 0, 1, 1);
		Border b2 = BorderFactory.createLineBorder(new Color(210, 210, 210), 0);
		container.setBorder(BorderFactory.createCompoundBorder(b1, b2));

		return container;
	}

	private static Panel header() {

		ImageIcon minIcon = new ImageIcon(Util.getResource("min.png"));
		minIcon.setImage(minIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));

		ImageIcon pressedMinIcon = new ImageIcon(Util.getResource("_min.png"));
		pressedMinIcon.setImage(pressedMinIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));

		JButton minBtn = new JButton(minIcon);
		minBtn.setContentAreaFilled(false);
		minBtn.setBorderPainted(false);
		minBtn.setBounds(width - 26 - 26, 0, 26, 26);
		minBtn.setFocusPainted(false);
		minBtn.setCursor(new Cursor(12));
		minBtn.setHorizontalAlignment(SwingConstants.CENTER);
		minBtn.setVerticalAlignment(SwingConstants.CENTER);
		minBtn.setPressedIcon(pressedMinIcon);
		minBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jf.setExtendedState(JFrame.ICONIFIED);
			}
		});

		ImageIcon icon = new ImageIcon(Util.getResource("close.png"));
		icon.setImage(icon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));

		ImageIcon pressedIcon = new ImageIcon(Util.getResource("_close.png"));
		pressedIcon.setImage(pressedIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));

		JButton closeBtn = new JButton(icon);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setBorderPainted(false);
		closeBtn.setBounds(width - 26, 0, 26, 26);
		closeBtn.setFocusPainted(false);
		closeBtn.setCursor(new Cursor(12));
		closeBtn.setHorizontalAlignment(SwingConstants.CENTER);
		closeBtn.setVerticalAlignment(SwingConstants.CENTER);
		closeBtn.setPressedIcon(pressedIcon);
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Layer.confirm("确认要退出并关闭程序吗?", 240, 160, new LayerCallback() {
					@Override
					public void clickBtn(String btn) {
					}

					@Override
					public void clickOkBtn(boolean confirm) {
						String DIR_PATH = System.getProperty("ROOT_PATH") + "\\msg";
						System.out.println(DIR_PATH);
						File file = new File(DIR_PATH);
						file.delete();
						System.exit(0);
					}
					@Override
					public void clickCancelBtn() {

					}
				});
			}
		});

		JLabel title = new JLabel(Frame.jf.getTitle());
		title.setBounds(5, 0, width - 100, headerHeight);
		title.setFont(CyFont.PuHuiTi(CyFont.Medium, 14));

		Panel header = new Panel();
		header.setSize(width, headerHeight);
		header.setBackground(new Color(236, 233, 231));
		header.setCursor(new Cursor(12));
		header.setLayout(null);
		header.add(title);
		header.add(closeBtn);
		header.add(minBtn);

		header.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				tempX = (int) point.getX();
				tempY = (int) point.getY();
				oldX = (int) point.getX();
				oldY = (int) point.getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		header.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {

				Point point = e.getPoint();
				Rectangle rec = jf.getBounds();

				winX = (int) rec.getX();
				winY = (int) rec.getY();
				int x = (int) point.getX();
				int y = (int) point.getY();
				tempX = x - oldX;
				tempY = y - oldY;

				jf.setLocation((int) (winX + tempX), (int) (winY + tempY));

			}
		});

		return header;
	}

}
