package com.view;

import javax.swing.JPanel;

import com.view.hardware.Hardware;

public class Container extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int width = Frame.width - Module.width;
	public static int height = Frame.height - Main.headerHeight;

	public Container(String name) {
		removeAll();
		switch (name) {
			case "hardware":
				add(new Hardware());
				break;
			default:
				break;
		}
		validate();
	}
}
