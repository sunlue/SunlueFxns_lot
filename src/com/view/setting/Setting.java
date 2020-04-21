package com.view.setting;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.view.Frame;
import com.view.Main;

public class Setting extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int width = Frame.width - com.view.Module.width;
	public static int height = Frame.height - Main.headerHeight;

	public Setting() {
		setPreferredSize(new Dimension(width, height));
	}
}
