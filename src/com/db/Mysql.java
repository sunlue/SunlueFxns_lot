package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ini4j.Profile.Section;

import com.util.Log;
import com.util.Util;
/**
 * MYSQL数据库操作类
 * @author xiebing
 */
public class Mysql {

	String host = "";
	String port = "3306";
	String database = "";
	String username = "";
	String password = "";
	Boolean loadConf = false;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	public Mysql() {
		Section ini = Util.getIni("jdbc.ini", false).get("mysql");
		host = ini.get("host");
		port = ini.get("port");
		database = ini.get("database");
		username = ini.get("username");
		password = ini.get("password");
	}

	public Connection connection() {
		String url = "jdbc:mysql://" + host + ":" + port + "/" + database
				+ "?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Log.write("init[com.mysql.jdbc.Driver初始化失败]", Log.Error);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.write("init[MySQL驱动程序初始化失败]", Log.Error);
		}
		return conn;
	}

	public void setConfig(String host, String port, String database, String username, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		connection();
	}

}
