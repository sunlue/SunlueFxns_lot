package com.view.datav.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * 
 * @author xiebing
 *
 */
public class Map extends JPanel {

	private static final long serialVersionUID = 1L;

	public Map(int width, int height) {
		setBorder(BorderFactory.createLineBorder(new Color(35, 36, 109), 4));
		setOpaque(false);
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
	}
}
