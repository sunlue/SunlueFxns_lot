package com.sunlue.util;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.sunlue.Index;

public class Util {

	private static JFrame currFrame;

	public static JFrame getCurrFrame() {
		return currFrame;
	}

	public static void setCurrFrame(JFrame currFrame) {
		Util.currFrame = currFrame;
	}

	/**
	 * 读取配置问文�?
	 * 
	 * @param folder
	 * @param filename
	 * @return
	 */
	public static Properties config(String filename) {
		Properties properties = new Properties();
		InputStream iniPath = Util.class.getResourceAsStream("/" + filename);
		try {
			properties.load(iniPath);
			return properties;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 生成资源路径
	 * 
	 * @return
	 */
	public static URL getResource() {
		return Util.class.getResource("/");
	}

	/**
	 * 生成资源路径
	 * 
	 * @param name
	 * @return
	 */
	public static URL getResource(String name) {
		return Util.class.getResource("/" + name);
	}

	/**
	 * 获取图片图标
	 * 
	 * @return
	 */
	public static Image getImageIcon(String name) {
		return new ImageIcon(Util.getResource(name)).getImage();
	}
	
	/**
	 * 获取图片图标
	 * 
	 * @return
	 */
	public static ImageIcon getImageIcon(String name,int width,int height) {
		ImageIcon imageIcon = new ImageIcon(Util.getResource(name));
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return imageIcon;
	}

	/**
	 * 获取logo图标
	 * 
	 * @return
	 */

	public static Image getLogoIcon() {
		return Util.getImageIcon("logo.square.transparent.png");
	}

	/**
	 * 获取logo图标
	 * 
	 * @return
	 */

	public static Image getLogoIcon(String name) {
		return Util.getImageIcon(name);
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String md5encode(String str) {
		byte[] secretBytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			secretBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法�?");
		}

		// 将加密后的数据转换为16进制数字
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，�?要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	/**
	 * 生成密码
	 * 
	 * @param account
	 * @param password
	 * @param key
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static String setPassword(String account, String password, String key) {
		@SuppressWarnings("rawtypes")
		List arr = new ArrayList();
		arr.add("0=" + account);
		arr.add("1=" + password);
		arr.add("2=" + key);
		String q = String.join("&", arr);
		return md5encode(URLDecoder.decode(q)).toUpperCase();
	}

	// 生成日志
	public static int LogInfo = 0;
	public static int LogError = 1;
	public static int LogWarn = 2;
	public static int LogDebug = 3;

	public static void log(String info, int type) {
		Logger logger = Logger.getLogger(Index.class);
		switch (type) {
		case 0:
			logger.info(info);
			break;
		case 1:
			logger.error(info);
			break;
		case 2:
			logger.warn(info);
			break;
		case 3:
			logger.debug(info);
			break;
		}
	}
}
