package com.util;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

public class CyFont {

	public static String Bold = "Bold";
	public static String Heavy = "Heavy";
	public static String Light = "Light";
	public static String Medium = "Medium";
	public static String Regular = "Regular";

	static Font AlibabaPuHuiTi = null;

	public static Font PuHuiTi(String name, float fontSize) {
//		String fontFileName = CyFont.class.getResource("/Alibaba-PuHuiTi-" + name + ".ttf").getPath();
//		try {
//			File file = new File(fontFileName);
//			FileInputStream input = new FileInputStream(file);
//			AlibabaPuHuiTi = Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(fontSize);
//			input.close();
//			System.out.println(input);
//			return AlibabaPuHuiTi;
//		} catch (Exception e) {
//			return new java.awt.Font("宋体", Font.PLAIN, 12);
//		}
		return new java.awt.Font("宋体", Font.PLAIN, 12);
	}

}
















