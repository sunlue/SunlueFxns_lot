package com.sunlue;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class Welcome extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Welcome() {
		JLabel label_pic = new JLabel(new ImageIcon("http://www.sunlue.com/templates/main/images/banner1.jpg"));
		getContentPane().add(label_pic, BorderLayout.CENTER);
		setVisible(true);
		setSize(950, 700);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		Welcome win = new Welcome();
		try {
			Thread.sleep(3000);
			win.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
