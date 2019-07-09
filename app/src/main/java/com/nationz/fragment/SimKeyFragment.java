/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
package com.nationz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nationz.bean.ToastEvent;
import com.nationz.service.BleService;
import com.nationz.ca.R;

/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-3-17 下午6:13:02 类说明
 */
public class SimKeyFragment extends EventFragment {
	private static final String TAG = "SimKeyFragment";
	private View rootView;
	private Button btnStart0;
	private TextView log_text;
	private Button btnStart1;
	private Button btnStart2;
	private Button btnStart3;
	private Button btnStart4;
	private Button btnStart5;
	private Button btnStart6;
	private Button btnStart7;
	private Button btnStart8;
	private Button btnStart9;
	private Button btnStart10;

	public void onEventMainThread(ToastEvent event) {
		Log.e(TAG, "ToastEvent:" + event.getTag());
		log_text.setText(event.getText());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frg_sim_key, null, false);

		initView();

		return rootView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		btnStart0 = (Button) rootView.findViewById(R.id.btn_start0);
		btnStart1 = (Button) rootView.findViewById(R.id.btn_start1);
		btnStart2 = (Button) rootView.findViewById(R.id.btn_start2);
		btnStart3 = (Button) rootView.findViewById(R.id.btn_start3);
		btnStart4 = (Button) rootView.findViewById(R.id.btn_start4);
		btnStart5 = (Button) rootView.findViewById(R.id.btn_start5);
		btnStart6 = (Button) rootView.findViewById(R.id.btn_start6);
		btnStart7 = (Button) rootView.findViewById(R.id.btn_start7);
		btnStart8 = (Button) rootView.findViewById(R.id.btn_start8);
		btnStart9 = (Button) rootView.findViewById(R.id.btn_start9);
		btnStart10 = (Button) rootView.findViewById(R.id.btn_start10);

		log_text = (TextView) rootView.findViewById(R.id.log_text);
		log_text.setText("结果显示：");

		btnStart0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(0);
			}
		});
		btnStart1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(1);
			}
		});
		btnStart2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(2);
			}
		});
		btnStart3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(3);
			}
		});
		btnStart4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(4);
			}
		});
		btnStart5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(5);
			}
		});
		btnStart6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(6);
			}
		});
		btnStart7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(7);
				//toBleService(12);
			}
		});

		btnStart8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(8);
			}
		});
		btnStart9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(9);
				
			}
		});
		
		btnStart10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toBleService(10);
				//toBleService(11);
			}
		});
	}

	private void toBleService(int type) {
		// TODO Auto-generated method stub
		Intent service = new Intent(getActivity(), BleService.class);
		service.putExtra("task_action", 5);
		service.putExtra("sim_key_test_type", type);
		getActivity().startService(service);
	}
}
