package com.sunlue.util;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

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
	public static ImageIcon getImageIcon(String name, int width, int height) {
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
		return Util.getImageIcon("logo200_200.png");
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
		info = "[ message ] " + info + " [ message ]";
		Logger logger = Logger.getLogger(Util.class);
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

	/**
	 * 将消息写入文件
	 * 
	 * @param filename
	 * @param message
	 */
	public static void setMsg(String filename, String message) {
		String FILE_NAME = System.getProperty("ROOT_PATH") + "/msg/" + filename + ".log";
		File writename = new File(FILE_NAME);
		try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(message); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将消息写入文件
	 * 
	 * @param filename
	 * @param message
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String getMsg(String filename) {
		String FILE_NAME = System.getProperty("ROOT_PATH") + "/msg/" + filename + ".log";
		StringBuffer message = new StringBuffer();
		try {
			File file = new File(FILE_NAME);
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
		long timemillis = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date(timemillis));
	}

	public static String getDateTime() {
		return getDateTime("yyyy-MM-dd HH:mm:ss");
	}

	static int flag = 1;// 用来判断文件是否删除成功

	public static int deleteFile(File file) {
		// 判断文件不为null或文件目录存在
		if (file == null || !file.exists()) {
			flag = 0;
			System.out.println("文件删除失败,请检查文件路径是否正确");
			return flag;
		}
		// 取得这个目录下的所有子文件对象
		File[] files = file.listFiles();
		// 遍历该目录下的文件对象
		for (File f : files) {
			// 打印文件名
			String name = file.getName();
			System.out.println(name);
			// 判断子目录是否存在子目录,如果是文件则删除
			if (f.isDirectory()) {
				deleteFile(f);
			} else {
				f.delete();
			}
		}
		// 删除空文件夹 for循环已经把上一层节点的目录清空。
		file.delete();
		return flag;
	}

}
