package com.util;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

/**
 * 工具类
 * 
 * @author xiebing
 */
public class Util {

	private static JFrame currFrame;

	public static JFrame getCurrFrame() {
		return currFrame;
	}

	public static void setCurrFrame(JFrame currFrame) {
		Util.currFrame = currFrame;
	}

	/**
	 * 读取ini配置文件
	 *
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
	 * 读取ini配置文件
	 *
	 * @return
	 */
	public static Ini getIni(String filename, Boolean absolute) {
		if (absolute == false) {
			filename = System.getProperty("user.dir") + File.separator + "config" + File.separator + filename;
		}
		return getIni(filename);
	}

	/**
	 * 读取ini配置文件
	 *
	 * @return
	 */
	public static Ini getIni() {
		String filename = System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.ini";
		return getIni(filename);
	}

	/**
	 * 读取ini配置文件
	 *
	 * @param pathname 文件路径（绝对路径）
	 * @return
	 */
	public static Ini getIni(String pathname) {
		File filename = new File(pathname);
		Ini ini = new Ini();
		try {
			ini.load(filename);
		} catch (InvalidFileFormatException e) {
			Log.write("读取" + pathname + "错误：" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.write("读取" + pathname + "错误：" + e.getMessage());
			e.printStackTrace();
		}
		return ini;
	}

	/**
	 * 修改ini文件
	 *
	 * @param data
	 */
	public static void updateIni(Map<String, Map<String, String>> data) {
		String filename = System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.ini";
		updateIni(filename, data);
	}

	/**
	 * 修改ini文件
	 *
	 * @param pathname
	 * @param data
	 */
	public static void updateIni(String pathname, Map<String, Map<String, String>> data) {
		Ini ini = getIni(pathname);
		Section section = null;
		Map<String, String> dataMap = null;
		for (String sect : data.keySet()) {
			section = ini.get(sect);
			dataMap = data.get(sect);
			for (String key : dataMap.keySet()) {
				section.put(key, dataMap.get(key) == null ? "" : dataMap.get(key));
			}
		}
		try {
			ini.store(new File(pathname));
		} catch (IOException e) {
			e.printStackTrace();
			Log.write("修改" + pathname + "错误：" + e.getMessage());
		}
	}

	public static Image getImage(String name) {
		return new ImageIcon(Util.getResource(name)).getImage();
	}

	public static Image getImage(String name, int width, int height) {
		return getImage(name, width, height, Image.SCALE_DEFAULT);
	}

	public static Image getImage(String name, int width, int height, int hints) {
		ImageIcon imageIcon = new ImageIcon(Util.getResource(name));
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, hints));
		return imageIcon.getImage();
	}

	/**
	 * 获取图片图标
	 *
	 * @return
	 */
	public static ImageIcon getImageIcon(String name) {
		return new ImageIcon(Util.getResource(name));
	}

	/**
	 * 获取图片图标
	 *
	 * @return
	 */
	public static ImageIcon getImageIcon(String name, int width, int height) {
		return getImageIcon(name, width, height, Image.SCALE_DEFAULT);
	}

	public static ImageIcon getImageIcon(String name, int width, int height, int hints) {
		ImageIcon imageIcon = new ImageIcon(Util.getResource(name));
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, hints));
		return imageIcon;
	}

	/**
	 * 获取logo图标
	 *
	 * @return
	 */

	public static Image getLogoIcon() {
		return Util.getImage("logo_200_200.png");
	}

	/**
	 * 获取logo图标
	 *
	 * @return
	 */

	public static Image getLogoIcon(String name) {
		return Util.getImage(name);
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
		String md5code = new BigInteger(1, secretBytes).toString(16);
		int length = 32 - md5code.length();
		// 如果生成数字未满32位，�?要前面补0
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			stringBuilder.append("0");
		}
		stringBuilder.append(md5code);
		return stringBuilder.toString();
	}

	/**
	 * 生成密码
	 *
	 * @param account
	 * @param password
	 * @param key
	 * @return
	 */
	public static String setPassword(String account, String password, String key) {
		List<String> arr = new ArrayList<String>();
		arr.add("0=" + account);
		arr.add("1=" + password);
		arr.add("2=" + key);
		String q = String.join("&", arr);
		return md5encode(URLDecoder.decode(q, StandardCharsets.UTF_8)).toUpperCase();
	}

	/**
	 * 将消息写入文件
	 *
	 * @param filename
	 * @param message
	 */
	public static void setMsg(String filename, String message) {
		String dirName = System.getProperty("user.dir") + File.separator + "msg";
		File dir = new File(dirName);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String fileName = dirName + File.separator + filename + ".log";
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			// \r\n即为换行
			out.write(message);
			// 把缓存区内容压入文件
			out.flush();
			// 最后记得关闭文件
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将消息写入文件
	 *
	 * @param filename
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String getMsg(String filename) {
		String fileName = System.getProperty("user.dir") + File.separator + "msg" + File.separator + filename + ".log";
		StringBuffer message = new StringBuffer();
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return "";
			}
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
			BufferedReader br = new BufferedReader(reader);
			String tempMsg;
			while ((tempMsg = br.readLine()) != null) {
				message.append(tempMsg + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message.toString();
	}

	public static String getDateTime(String pattern) {
		long timeMillis = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date(timeMillis));
	}

	public static String getDateTime() {
		return getDateTime("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 用来判断文件是否删除成功
	 */
	public static int deleteFile(File file) {
		// 判断文件不为null或文件目录存在
		if (file == null || !file.exists()) {
			return 0;
		}
		// 取得这个目录下的所有子文件对象
		File[] files = file.listFiles();
		// 遍历该目录下的文件对象
		for (File f : files) {
			if (f.isDirectory()) {
				deleteFile(f);
			} else {
				f.delete();
			}
		}
		// 删除空文件夹 for循环已经把上一层节点的目录清空。
		file.delete();
		return 1;
	}

	/**
	 * 产生一个随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		Random r = new Random();
		int number = (r.nextInt(max - min + 1) + min);
		return number;
	}

}
