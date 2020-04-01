package com.sunlue;

/**
 * 环境监测实体类
 * 
 * @author hyh
 *
 */
public class SocketEntity {
	private String wendu;
	private String shidu;
	private String yuliang;
	private String fengxiang;
	private String fengsu;
	private String co;
	private String pm25;
	private String pm10;
	private String qiya;
	private String fulizi;
	private String original;
	private String addTime;

	public String getWendu() {
		return wendu;
	}

	public String getShidu() {
		return shidu;
	}

	public String getYuliang() {
		return yuliang;
	}

	public String getFengxiang() {
		return fengxiang;
	}

	public String getFengsu() {
		return fengsu;
	}

	public String getCo() {
		return co;
	}

	public String getPm25() {
		return pm25;
	}

	public String getPm10() {
		return pm10;
	}

	public String getQiya() {
		return qiya;
	}

	public String getFulizi() {
		return fulizi;
	}

	public String getOriginal() {
		return original;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setWendu(String wendu) {
		this.wendu = wendu;
	}

	public void setShidu(String shidu) {
		this.shidu = shidu;
	}

	public void setYuliang(String yuliang) {
		this.yuliang = yuliang;
	}

	public void setFengxiang(String fengxiang) {
		this.fengxiang = fengxiang;
	}

	public void setFengsu(String fengsu) {
		this.fengsu = fengsu;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	public void setQiya(String qiya) {
		this.qiya = qiya;
	}

	public void setFulizi(String fulizi) {
		this.fulizi = fulizi;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getString() {
		return "温度" + wendu + "℃;" + "湿度" + shidu + "%;" + "雨量" + yuliang + ";" + "风向" + fengxiang + ";" + "风速" + fengsu
				+ ";" + "一氧化碳" + co + ";" + "PM2.5" + pm25 + ";" + "PM10" + pm10 + ";" + "气压" + qiya + ";" + "负离子"
				+ fulizi + ";";
	}

}
