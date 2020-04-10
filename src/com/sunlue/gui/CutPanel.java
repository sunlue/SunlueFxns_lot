package com.sunlue.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * @ClassName CutPanel
 * @Description 面板切换的示例类
 * @author Kilper blog.163.com/kilper
 * @date 2010-7-20 下午12:40:54
 *
 */
public class CutPanel extends JFrame {
	public JToolBar toolBar;
	public PanelAction panelAction = new PanelAction(); // 内部类，实现事件响应
	public JPanel panel;

	public CutPanel(String title) {
		this.setTitle(title);
		this.setSize(400, 500);
		this.setLocationRelativeTo(null); // 设置窗体在屏幕中央显示
		this.add(buildToolBar(), BorderLayout.NORTH);
		this.add(buildPanel("面板容器"), BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JToolBar buildToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout());
			toolBar.add(buildButton("按钮1"));
			toolBar.add(buildButton("按钮2"));
			toolBar.setEnabled(false); // 设置toolBar的可移动性
		}
		return toolBar;
	}

	public JPanel buildPanel(String title) {
		if (panel == null) {
			panel = new JPanel();
			panel.add(new Label("这是默认的面板"), BorderLayout.CENTER);
			panel.setBorder(BorderFactory.createTitledBorder(title));
		}
		return panel;
	}

	public JButton buildButton(String name) {
		JButton btn = new JButton(name);
		btn.addActionListener(panelAction);
		return btn;
	}

	public static void main(String[] args) {
		CutPanel cut = new CutPanel("面板切换示例");
		cut.setVisible(true);
	}

	private class PanelAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String num = e.getActionCommand().substring(2);
			CutPanel cp = CutPanel.this;
			cp.panel.removeAll();
			cp.panel.add(new Label("这是通过按钮切换的面板" + num));
			cp.panel.validate();
		}
	}
}