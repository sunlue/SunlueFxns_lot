package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Db {

	static String host = "";
	static String port = "3306";
	static String database = "";
	static String username = "";
	static String password = "";
	static Boolean loadConf = false;
	static Connection conn = null;
	static ResultSet rs = null;
	static PreparedStatement ps = null;

	public Db() {
		if (loadConf == false) {
			loadConfig();
		}
		if (conn == null) {
			connection();
		}
	}

	public Db(String host, String port, String database, String username, String password) {
		Db.host = host;
		Db.port = port;
		Db.database = database;
		Db.username = username;
		Db.password = password;
		loadConf = true;
		connection();
	}

	public static void loadConfig() {
		Properties prop = Util.config("jdbc.ini");
		host = prop.getProperty("host");
		port = prop.getProperty("port");
		database = prop.getProperty("database");
		username = prop.getProperty("username");
		password = prop.getProperty("password");
		loadConf = true;
		if (host.equals("") || host == null || port.equals("") || port == null || database.equals("")
				|| database == null || username.equals("") || username == null || password.equals("")
				|| password == null) {
		}
	}

	protected static void connection() {
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
			Log.write("init[SQL驱动程序初始化失败]", Log.Error);
		}

	}

	public static int exce(String sql) {
		int i = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			i = ps.executeUpdate();
			closeConn();
		} catch (SQLException e) {
			Log.write("数据库执行异常:" + e.getMessage() + "\r\n【" + sql + "】", Log.Error);
		}
		return i;
	}

	public static ResultSet selectSql(String sql) {
		connection();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
		} catch (SQLException e) {
			Log.write("数据库查询异常:" + e.getMessage() + "\r\n【" + sql + "】", Log.Error);
		}
		return rs;
	}

	public static void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			Log.write("数据库关闭异常:" + e.getMessage(), Log.Error);
		}
	}

	/****************************** 链式操作 ******************************/
	private String name;
	private String field = "*";
	private String where = "";
	private String order = "";
	private String group = "";
	private String limit = "";
	private String data = "";

	private String sql;

	/**
	 * 表名称（含前缀）
	 * 
	 * @param name
	 * @return
	 */
	public Db name(String name) {
		Properties prop = Util.config("jdbc.ini");
		String prefix = prop.getProperty("prefix");
		if (prefix != null) {
			this.name = prefix + name;
		} else {
			this.name = name;
		}
		return this;
	}

	/**
	 * 表名称（不含前缀）
	 * 
	 * @param table
	 * @return
	 */
	public Db table(String table) {
		this.name = table;
		return this;
	}

	/**
	 * 字段
	 * 
	 * @param field
	 * @return
	 */
	public Db field(String field) {
		this.field = '`' + field + '`';
		return this;
	}

	/**
	 * 要执行得数据
	 * 
	 * @param data
	 * @return
	 */
	public Db data(Map<String, String> data) {
		List<String> fieldArr = new ArrayList<>();
		List<String> valueArr = new ArrayList<>();
		for (String key : data.keySet()) {
			fieldArr.add("`" + key + "`");
			valueArr.add("'" + data.get(key).toString() + "'");
		}
		this.field = String.join(",", fieldArr);
		this.data = String.join(",", valueArr);
		return this;
	}

	public Db where(String where) {
		if (where == null || where.isEmpty()) {
			this.where = "";
		} else {
			this.where = " where " + where;
		}
		return this;
	}

	public Db order(String order) {
		if (order == null || order.isEmpty()) {
			this.order = " ";
		} else {
			this.order = " order by" + order;
		}
		return this;
	}

	public Db group(String group) {
		if (group == null || group.isEmpty()) {
			this.group = " ";
		} else {
			this.order = " group by" + group;
		}
		return this;
	}

	public Db limit(int page, int limit) {
		this.limit = " limit " + (page - 1) * limit + "," + limit;
		return this;
	}

	/**
	 * 生成sql
	 * 
	 * @return
	 */
	public String fetchSql() {
		sql = "select " + field + " from " + name + where + limit + group + order;
		return sql;
	}

	/**
	 * 增加数据
	 * 
	 * @return 执行成功条数
	 */
	public int insert() {
		sql = "insert into " + name + "(" + field + ") values(" + data + ")";
		return Db.exce(sql);
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public ResultSet select() {
		return Db.selectSql(fetchSql());
	}

	/************** 聚合函数 ********************/
	public int count() {
		this.field = "count(*) as count";
		this.limit = "";
		ResultSet rSet = selectSql(fetchSql());
		try {
			if (rSet.next()) {
				int count = Integer.parseInt(rSet.getString("count"));
				return count;
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getLastSql() {
		return sql;
	}

}
