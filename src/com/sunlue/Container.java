package com.sunlue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.sunlue.page.TouristrFlow;


public class Container extends JPanel {

	public static int width = Frame.width - Module.width;
	public static int height = Frame.height - Main.headerHeight;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Container() {
		setPreferredSize(new Dimension(width,height));
		setLayout(new BorderLayout());
		add(MenuList(), BorderLayout.NORTH);
		add(new TouristrFlow(), BorderLayout.CENTER);
	}

	private JPanel MenuList() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width,60));
		panel.setBackground(Color.RED);
		return panel;
	}

}
