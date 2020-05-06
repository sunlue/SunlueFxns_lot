package com.dao;

/**
 * 环境监测仪接口
 * @author xiebing
 */
public interface EnvMonitorDao{
	/**
	 * 写入环境监测仪心跳数据
	 * @param wendu 温度
	 * @param shidu 湿度
	 * @param yuliang 雨量
	 * @param fengxiang 风向
	 * @param fengsu 风速
	 * @param co 一氧化碳
	 * @param pm25 pm2.5
	 * @param pm10 pm10
	 * @param qiya 气压 
	 * @param fulizi 负离子
	 * @param addTime 添加时间
	 * @param string 
	 * @return
	 */
	public int insert(String wendu,String shidu,String yuliang,String fengxiang,String fengsu,String co,String pm25,String pm10,String qiya,String fulizi,String addTime, String string);
	
}
