package com.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.ini4j.Profile.Section;

import com.util.Log;
import com.util.Util;
/**
 * SQLite数据库操作类
 * @author xiebing
 */
public class Sqlite {

	static Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String database;

	public Sqlite() {
		Section ini = Util.getIni("jdbc.ini", false).get("sqlite");
		this.database = System.getProperty("user.dir") + File.separator + "db" + File.separator + ini.get("database");
	}

	public Connection connection() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + database + ".db");
		} catch (Exception e) {
			Log.write("init[SQLite初始化失败]", Log.Error);
		}
		return conn;
	}

	public void setConfig(String database) {
		this.database = database;
	}

}
