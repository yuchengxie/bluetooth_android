/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
package com.nationz.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nationz.bean.BleActionEvent;
import com.nationz.bean.BleStateEvent;
import com.nationz.bean.CTimeEvent;
import com.nationz.ca.R;
import com.nationz.util.Constant;
import com.nationz.util.Util;

import de.greenrobot.event.EventBus;

/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-2-25 下午7:56:58 类说明
 */
public class ConnectFragment extends EventFragment {

	private static final String TAG = "ConnectFragment";
	private View rootView;
	private EditText editBleName;
	private EditText editBlePwd;
	private Button btnConnect;
	private TextView texTime;
	private TextView guochengTime;

	public void onEventMainThread(BleStateEvent event) {
		Log.e(TAG, "onEventMainThread" + event.bleState);
		StringBuilder sb = new StringBuilder(guochengTime.getText().toString());
				
		if(23==event.bleState){
			// 找到设备
			sb.append("\n");
			sb.append("找到设备");
		}
		
		if(16==event.bleState){
			// 连接成功
			sb.append("\n");
			sb.append("连接成功");
		}
		
		guochengTime.setText(sb.toString());
	}

	public void onEventMainThread(CTimeEvent event) {
		Log.e(TAG, "onEventMainThread" + event.ConTime);
		
		texTime.setText("连接时间： " + event.ConTime + " ms");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frg_connect, null, false);

		initView();

		return rootView;
	}

	private void initView() {
		editBleName = (EditText) rootView.findViewById(R.id.edit_ble_name);
		editBlePwd = (EditText) rootView.findViewById(R.id.edit_ble_pwd);
		btnConnect = (Button) rootView.findViewById(R.id.btn_connect);
		texTime = (TextView) rootView.findViewById(R.id.text_time);
		guochengTime = (TextView) rootView.findViewById(R.id.text_guochen_time);

		SharedPreferences mSp = getActivity().getSharedPreferences(
				Constant.SP_NAME, Activity.MODE_PRIVATE);
		editBleName.setText(mSp.getString(Constant.BLE_NAME_SUC, ""));
		if (Util.haveBleParams(getActivity())) {
			editBlePwd.setText(mSp.getString(Constant.BLE_PIN_SUC, ""));
		}

		btnConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String bleName = editBleName.getText().toString();
				String blePwd = editBlePwd.getText().toString();

				if (bleName == null || bleName.length() != 12 || blePwd == null
						|| blePwd.length() != 6) {
					Toast.makeText(getActivity(), "蓝牙连接参数格式不正确",
							Toast.LENGTH_LONG).show();
				} else {
					texTime.setText("");
					guochengTime.setText("");
					
					toConnect(bleName, blePwd);
				}

			}
		});

	}
	
	private void toConnect(String name, String pin) {
		BleActionEvent mBleActionEvent = new BleActionEvent(0);
		mBleActionEvent.setName(name);
		mBleActionEvent.setPin(pin);
		mBleActionEvent.setTimeout(30);
		EventBus.getDefault().post(mBleActionEvent);
	}
	
}
