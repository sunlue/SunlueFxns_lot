package com.dao;

public class Factory {
	public static Object dao(String name) {
		try {
			Class c = Class.forName(name);
			return c.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
