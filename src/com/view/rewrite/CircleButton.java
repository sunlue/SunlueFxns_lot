package com.view.rewrite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;
import javax.swing.JButton;
/**
 * @author xiebing
 */
public class CircleButton extends JButton implements MouseListener {
	private static final long serialVersionUID = 1L;

	private Shape shape = null;

	/**
	 * 按下时的颜色
	 */
	private Color click = new Color(0, 0, 113);
	/**
	 * 离开时颜色
	 */
	private Color quit = new Color(237, 234, 228);

	public CircleButton(String s) {
		super(s);
		addMouseListener(this);
		//是否显示外围矩形区域 选否
		setContentAreaFilled(false);
	}

	public CircleButton() {
		super();
		addMouseListener(this);
	}

	public CircleButton(String text, Icon icon) {
		super(text, icon);
		addMouseListener(this);
	}

	public CircleButton(Icon icon) {
		super(icon);
		addMouseListener(this);
	}

	public void setColor(Color c, Color q) {
		click = c;
		quit = q;
	}

	@Override
	public void paintComponent(Graphics g) {
		// 如果按下则为ＣＬＩＣＫ色 否则为 ＱＵＩＴ色
		if (getModel().isArmed()) {
			g.setColor(click);
		} else {
			g.setColor(quit);
		}
		// 填充圆角矩形区域 也可以为其它的图形
		g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
		// 必须放在最后 否则画不出来
		super.paintComponent(g);
	}
	@Override
	public void paintBorder(Graphics g) {
		// 画边界区域
		g.setColor(click);
		g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
	}
	@Override
	public boolean contains(int x, int y) {
		// 判断点（x,y）是否在按钮内
		if (shape == null || !(shape.getBounds().equals(getBounds()))) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
		}
		return shape.contains(x, y);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouseClicked");
	}
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mousePressed");
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouseReleased");
	}
	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("mouseExited");
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("mouseEntered");
	}
}
