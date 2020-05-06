package com.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 日志处理类
 * 
 * @author xiebing
 *
 */
public class Log {

	public static int Info = 0;
	public static int Error = 1;
	public static int Warn = 2;
	public static int Debug = 3;
	
	public Log() {
		System.setProperty("ROOT_PATH", System.getProperty("user.dir"));
		Properties properties = Util.config("log4j.ini");
		PropertyConfigurator.configure(properties);
	}

	public static void write(String info) {
		write(info, Info);
	}

	public static void write(String info, int type) {
		new Log();
		info = "[--------------message-------------]\r\n " + info + "\r\n" + "[--------------message-------------]";
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
			default:
				break;
		}
	}
}
