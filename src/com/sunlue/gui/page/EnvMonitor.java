package com.sunlue.gui.page;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sunlue.gui.Frame;

/**
 * 环境检测仪页面
 * 
 * @author xiebing
 *
 */
public class EnvMonitor {

	public static JTextArea msgTxtArea;
	public static int width = Frame.width - 200;
	
	public JPanel view() {
		
		JPanel head = new JPanel();
		head.setPreferredSize(new Dimension(width, 60));
		head.setBackground(Color.green);

		msgTxtArea = new JTextArea();
		msgTxtArea.setEditable(false);
		msgTxtArea.setForeground(Color.blue);
		msgTxtArea.setMargin(new Insets(5, 5, 5, 5));
		msgTxtArea.setFont(new Font("微软雅黑", 1, 16));
		JScrollPane scrollPane = new JScrollPane(msgTxtArea);
		
		JPanel content=new JPanel();
		content.setLayout(new BorderLayout());
		content.add(head, BorderLayout.NORTH);
		content.add(scrollPane, BorderLayout.CENTER);
		
		return content;
	}
	

}
