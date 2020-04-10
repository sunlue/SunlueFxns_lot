package com.sunlue.gui.page;

import java.awt.BorderLayout;

import javax.swing.*;

/**
 * 环境检测仪页面
 * 
 * @author xiebing
 *
 */
public class View extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void view() {
		ViewAction exwpAction = new ViewAction(this);// 提前定义好动作对象
		setLayout(new BorderLayout());// 使用BorderLayout布局
		setSize(400, 400);// 设置界面大小
		this.setTitle("视图1界面");// 设置界面名称
		JButton eb = new JButton("视图2");// 设置切换的按钮eb
		JLabel tip_l = new JLabel("视图1", 0);// 界面的内容，这里用一个标签来替代具体内容，后面的“0”是为了使这个标签居中对齐
		eb.addActionListener(exwpAction);// 为eb这个按钮添加按键监听
		add(tip_l, BorderLayout.CENTER);// 将tip_l这个标签添加到布局的中间
		add(eb, BorderLayout.SOUTH);// 将按钮eb添加到布局的下方
		setVisible(true);// 使视图可视化
	}

	public void view2() {
		ViewAction exwpAction = new ViewAction(this);
		setLayout(new BorderLayout());
		setSize(400, 400);
		this.setTitle("视图2界面");
		JButton eb = new JButton("视图1");
		JLabel tip_l = new JLabel("视图2", 0);
		eb.addActionListener(exwpAction);
		add(tip_l, BorderLayout.CENTER);
		add(eb, BorderLayout.SOUTH);
		setVisible(true);
	}
}
