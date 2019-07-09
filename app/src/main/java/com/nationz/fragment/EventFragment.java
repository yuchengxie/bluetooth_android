/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
package com.nationz.fragment;

import de.greenrobot.event.EventBus;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-2-24 下午5:50:04 类说明
 */
public class EventFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 注册EventBus
		EventBus.getDefault().register(this);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 注销EventBus
		EventBus.getDefault().unregister(this);
		Log.e("EventFragment", "--- ON DESTROY ---");
	}

}
