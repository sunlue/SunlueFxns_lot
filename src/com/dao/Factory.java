package com.dao;

/**
 * dao层工厂类
 * @author xiebing
 */
public class Factory {
	public static Object dao(String name) {
		try {
			Class<?> c = Class.forName(name);
			return c.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
