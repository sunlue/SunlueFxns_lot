package com.dao.impl;

import com.db.Db;
import com.util.Util;

/**
 * dao层实现基类
 * @author xiebing
 */
public class DaoImpl {
	String defaultDb = Util.getIni("jdbc.ini", false).get("use", "default");
	Db db = new Db(defaultDb).init();
}
