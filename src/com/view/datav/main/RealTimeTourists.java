package com.view.datav.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.util.CyFont;

/**
 * 
 * @author xiebing
 *
 */
public class RealTimeTourists {
	private static String[] numArr;
	private static JPanel panel = new JPanel();

	public RealTimeTourists(String number) {
		numArr = calculate(number);
	}

	public JPanel handle() {
		panel.setOpaque(false);
		for (int i = 0; i < numArr.length; i++) {
			JLabel label = new JLabel();
			label.setForeground(Color.WHITE);
			label.setText(numArr[i]);
			if (!Character.isDigit(numArr[i].charAt(0))) {
				label.setPreferredSize(new Dimension(20, 80));
				label.setFont(CyFont.puHuiTi(CyFont.Medium, 48));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.BOTTOM);
				label.setOpaque(false);
			} else {
				label.setPreferredSize(new Dimension(66, 80));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setFont(CyFont.puHuiTi(CyFont.Medium, 66));
				label.setOpaque(true);
				label.setBackground(new Color(7, 10, 85));
				label.setBorder(BorderFactory.createLineBorder(new Color(250, 250, 250, 20), 2, true));
			}
			panel.add(label);
		}
		JLabel unit = new JLabel("人");
		unit.setForeground(Color.white);
		unit.setFont(CyFont.puHuiTi(CyFont.Medium, 14));
		unit.setHorizontalAlignment(SwingConstants.CENTER);
		unit.setVerticalAlignment(SwingConstants.BOTTOM);
		unit.setPreferredSize(new Dimension(20, 80));
		panel.add(unit);
		return panel;
	}

	private static String[] calculate(String num) {
		if (num.length() < 6) {
			int size = 6 - num.length();
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < size; i++) {
				stringBuilder.append("0");
			}
			num = stringBuilder.append(num).toString();
		}
		num = num.substring(0, 3) + "," + num.substring(3, 6);
		return num.split("");
	}

}

class ChangeTourists extends Thread {
	private static JPanel element;
	private static String[] numArr;

	public ChangeTourists(JPanel flop, String number) {
		element = flop;
		numArr = calculate(number);
	}

	@Override
	public void run() {
		for (int i = 0; i < numArr.length; i++) {
			JLabel label = (JLabel) element.getComponent(i);
			label.setText(numArr[i]);
		}
	}

	private static String[] calculate(String num) {
		if (num.length() < 6) {
			int size = 6 - num.length();
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < size; i++) {
				stringBuilder.append("0");
			}
			num = stringBuilder.append(num).toString();
		}
		num = num.substring(0, 3) + "," + num.substring(3, 6);
		return num.split("");
	}
}
