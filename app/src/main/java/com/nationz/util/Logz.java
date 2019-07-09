/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
package com.nationz.util;

import android.util.Log;

/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-3-9 下午6:55:31 类说明
 */
public class Logz {
	private static final boolean D = true;

	public static void e(String tag, String msg) {
		if (D)
			Log.e(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (D)
			Log.d(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (D)
			Log.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (D)
			Log.w(tag, msg);
	}

}
