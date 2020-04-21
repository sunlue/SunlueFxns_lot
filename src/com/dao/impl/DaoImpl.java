package com.dao.impl;

import com.db.Db;
import com.util.Util;

public class DaoImpl {
	String defaultDb = Util.getIni("jdbc.ini", false).get("use", "default");
	Db db = new Db(defaultDb).init();
}
