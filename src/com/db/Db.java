package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ini4j.Profile.Section;

import com.util.Log;
import com.util.Util;

public class Db {
	public enum DbType {
		MYSQL, SQLITE
	};

	static Connection conn = null;
	private MySQL mysql;
	private Sqlite sqlite;
	private DbType useDbType;

	public Db(DbType type) {
		this.useDbType = type;
	}

	public Db(String type) {
		this.useDbType = (type == "mysql" ? DbType.MYSQL : DbType.SQLITE);
	}

	public Db(DbType type, boolean autoInit) {
		this.useDbType = type;
		if (autoInit) {
			init();
		}
	}

	public Db config(String host, String port, String database, String username, String password) {
		if (this.useDbType.equals(DbType.MYSQL)) {
			this.mysql.setConfig(host, port, database, username, password);
		}
		return this;
	}

	public Db config(String database) {
		if (this.useDbType.equals(DbType.SQLITE)) {
			this.sqlite.setConfig(database);
		}
		return this;
	}

	public Db init() {
		if (this.useDbType.equals(DbType.MYSQL)) {
			this.mysql = new MySQL();
			conn = this.mysql.connection();
		} else if (this.useDbType.equals(DbType.SQLITE)) {
			this.sqlite = new Sqlite();
			conn = this.sqlite.connection();
		}
		return this;
	}

	static PreparedStatement ps = null;
	static ResultSet rs = null;

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

	public static ResultSet selectSql(String sql, DbType dbType) {
		try {
			ps = conn.prepareStatement(sql);
			if (dbType.equals(DbType.MYSQL)) {
				rs = ps.executeQuery(sql);
			} else if (dbType.equals(DbType.SQLITE)) {
				rs = ps.executeQuery();
			}
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

	private String name = "";
	private String field = "*";
	private String where = "";
	private String order = "";
	private String group = "";
	private String limit = "";
	private String data = "";

	private String sql;

	public Db name(String name) {
		Section prop = Util.getIni("jdbc.ini", false).get("sqlite");
		String prefix = prop.get("prefix");
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
	 * 增加数据
	 * 
	 * @return 执行成功条数
	 */
	public int update(Map<String, String> data) {
		ArrayList<String> arr = new ArrayList<String>();
		for (String key : data.keySet()) {
			arr.add(key + "='" + data.get(key).toString() + "'");
		}
		String str = String.join(",", arr);
		sql = "update " + name + " set " + str + " " + where;
		return Db.exce(sql);
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public ResultSet select() {
		return Db.selectSql(fetchSql(), this.useDbType);
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public ResultSet find() {
		this.limit = " limit 0,1";
		return Db.selectSql(fetchSql(), this.useDbType);
	}

	/************** 聚合函数 ********************/
	public int count() {
		this.field = "count(*) as count";
		this.limit = "";
		ResultSet rSet = selectSql(fetchSql(), this.useDbType);
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

	public Connection getConn() {
		return conn;
	}

}
