/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
package com.nationz.bean;

/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-2-24 上午11:39:33 类说明
 */
public class BleEvent {
	public int bleState;

	public BleEvent(int state) {
		this.bleState = state;
	}
	
	public int eventTime;

	public void setEventTime(int time) {
		this.eventTime = time;
	}
	
	public int getEventTime(){
		return this.eventTime;
	}
}
