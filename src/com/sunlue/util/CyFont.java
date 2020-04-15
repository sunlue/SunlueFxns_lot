package com.sunlue.util;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

public class CyFont {

	public static String Bold = "Bold";
	public static String Heavy = "Heavy";
	public static String Light = "Light";
	public static String Medium = "Medium";
	public static String Regular = "Regular";

	public static Font PuHuiTi(String name, float fontSize) {
		String fontFileName = CyFont.class.getResource("/Alibaba-PuHuiTi-" + name + ".ttf").getPath();
		try {
			File file = new File(fontFileName);
			FileInputStream aixing = new FileInputStream(file);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
			Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
			aixing.close();
			return dynamicFontPt;
		} catch (Exception e) {
			e.printStackTrace();
			return new java.awt.Font("宋体", Font.PLAIN, 12);
		}
	}

}
