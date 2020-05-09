package com.view;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.util.Util;

/**
 * @author xiebing
 */
public class Index {
	public static void main(String[] args) throws Exception {
		System.setProperty("ROOT_PATH", System.getProperty("user.dir"));
		Properties properties = Util.config("log4j.ini");
		PropertyConfigurator.configure(properties);
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new com.view.datav.DataV();
			}
		});
	}
}
