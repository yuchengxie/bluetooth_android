/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
package com.nationz.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-2-19 上午9:41:29 类说明
 */
public class BleService extends IntentService {

	private static final String TAG = "BleService";

	public static final int TASK_SIM_KEY = 5;// 测试sim key

	public BleService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		int task_action = intent.getIntExtra("task_action", 100);
		Log.e(TAG, "receive task" + task_action);

		switch (task_action) {
		case TASK_SIM_KEY:
			int type = intent.getIntExtra("sim_key_test_type", 0);
			TaskSimKey mTaskSimKey = new TaskSimKey();
			mTaskSimKey.start(type);
			break;
		default:
			break;
		}
	}
	
}
