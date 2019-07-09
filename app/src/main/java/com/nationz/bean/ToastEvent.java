/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
package com.nationz.bean;

/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-3-30 下午5:44:22 类说明
 */
public class ToastEvent {
	private int tag;

	private String text;
	
	public ToastEvent(String tip) {
		text = tip;
	}
	
	public ToastEvent() {
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
