package com.dao;

/**
 * 客流量统计接口
 * @author xiebing
 */
public interface TouristsNumberDao{
	/**
	 * 添加客流数据
	 * @param num 数量
	 * @param in 进
	 * @param out 出
	 * @param time 时间
	 * @return
	 */
	public int insert(int num, int in, int out, String time);
}
