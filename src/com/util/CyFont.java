package com.util;

import java.awt.Font;
/**
 * 字体扩展类
 * @author xiebing
 */
public class CyFont {

	public static String Bold = "Bold";
	public static String Heavy = "Heavy";
	public static String Light = "Light";
	public static String Medium = "Medium";
	public static String Regular = "Regular";

	static Font IconFont = null;

	public static Font puHuiTi(String name, float fontSize) {
//		String fontFileName = CyFont.class.getResource("/Alibaba-PuHuiTi-" + name + ".ttf").getPath();
//		try {
//			java.io.File file = new java.io.File(fontFileName);
//			java.io.FileInputStream input = new java.io.FileInputStream(file);
//			AlibabaPuHuiTi = Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(fontSize);
//			input.close();
//			System.out.println(input);
//			return AlibabaPuHuiTi;
//		} catch (Exception e) {
//			return new java.awt.Font("宋体", Font.PLAIN, 12);
//		}
		return new java.awt.Font("微软雅黑", Font.PLAIN, (int) fontSize);
	}

	public static Font icon(float fontSize) {
		String fontFileName = CyFont.class.getResource("/iconfont.woff2").getPath();
		try {
			java.io.File file = new java.io.File(fontFileName);
			java.io.FileInputStream input = new java.io.FileInputStream(file);
			IconFont = Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(fontSize);
			input.close();
			return IconFont;
		} catch (Exception e) {
		}
		return IconFont;
	}

}
