/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
/**zhai.yuehui
 * 2016-6-22
 * 上午10:14:13
 */
package com.nationz.bean;
/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-6-22 上午10:14:13
 * ble操作事件
 */
public class BleActionEvent {
	private int actionType;//0:connect;1：close;2:closeBle
	
	private int bleParamsFrom;//在发起连接时，需要知道蓝牙参数的来源，包括后台0、拨号1、以往成功连接过的2
	

	/**
	 * @return the bleParamsFrom
	 */
	public int getBleParamsFrom() {
		return bleParamsFrom;
	}

	/**
	 * @param bleParamsFrom the bleParamsFrom to set
	 */
	public void setBleParamsFrom(int bleParamsFrom) {
		this.bleParamsFrom = bleParamsFrom;
	}

	public BleActionEvent(int type) {
		this.actionType = type;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	
	private String name;
	private String pin;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	
	private int timeout;

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
}
