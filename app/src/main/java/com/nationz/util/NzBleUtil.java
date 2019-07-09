/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
/**zhai.yuehui
 * 2016-6-23
 * 下午1:38:02
 */
package com.nationz.util;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.nationz.bean.BleStateEvent;
import com.nationz.bean.ConnectFailEvent;
import com.nationz.bean.ToastEvent;
import com.nationz.sim.sdk.NationzSim;
import com.nationz.sim.sdk.NationzSimCallback;

import de.greenrobot.event.EventBus;

/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-6-23 下午1:38:02
 * 类说明
 */
public class NzBleUtil implements NationzSimCallback{
	private static final String TAG = "NzBleUtil";

	private static NationzSim mNationzSim;
	
	public static NationzSim getNationzSim() {
		return mNationzSim;
	}

	private static NzBleUtil mNzBleUtil;

	private static Application mApp;
	
	private NzBleUtil(Application app) {
		mApp = app;
		mNationzSim = NationzSim.initialize(mApp, this);
		NationzSim.setSdkParams(true, true, 3*60);
		NationzSim.setConnectTimeout(30);
		if(mNationzSim == null){
			Toast.makeText(mApp, "您的手机不支持低功耗蓝牙功能", Toast.LENGTH_SHORT).show();
		}
	}

	public static synchronized NzBleUtil getInstance(Application app) {
		if (mNzBleUtil == null) {
			mNzBleUtil = new NzBleUtil(app);
		}
		return mNzBleUtil;
	}
	
	public static int getNzBleState(){
		return NationzSim.getConnectionState();
	}
	
	public void connect(String name, String pin, int timeout){
		if (name == null || name.length() != 12 || pin == null
				|| pin.length() != 6) {
			return;
		}
		
		if (mNationzSim != null) {
			
			SharedPreferences mSp = mApp.getSharedPreferences(Constant.SP_NAME,
					Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = mSp.edit();
			editor.putString(Constant.BLE_NAME_CONNECTING, name);
			editor.putString(Constant.BLE_PIN_CONNECTING, pin);
			editor.commit();
			
			NationzSim.setConnectTimeout(timeout);//onConnectionStateChange返回27的时间
			mNationzSim.connect(name, pin);
			
		}
	}
	
	public void close(){
		if (mNationzSim != null) {
			mNationzSim.close();
		}
	}
	
	public void closeBle(){
		if (mNationzSim != null) {
			mNationzSim.closeBle();
		}
	}

	@Override
	public void onConnectionStateChange(int state) {
		Log.e(TAG, "onConnectionStateChange:" + state);
		//返回27时，请根据情况操作（可以调用close()停止连接过程，不调用的话还是会继续连接的）
		if(state == 27){
			close();
			ConnectFailEvent mConnectFailEvent = new ConnectFailEvent();
			EventBus.getDefault().post(mConnectFailEvent);
		}else if(state == 16){
		//连接成功，则保存正确的连接参数
			SharedPreferences mSp = mApp.getSharedPreferences(Constant.SP_NAME,
					Activity.MODE_PRIVATE);
			String name = mSp.getString(Constant.BLE_NAME_CONNECTING, "");
			String pin = mSp.getString(Constant.BLE_PIN_CONNECTING, "");
			
			SharedPreferences.Editor editor = mSp.edit();
			editor.putString(Constant.BLE_NAME_SUC, name);
			editor.putString(Constant.BLE_PIN_SUC, pin);
			editor.commit();
		}
		
		BleStateEvent mBleEvent = new BleStateEvent(state);
		EventBus.getDefault().post(mBleEvent);
	}

	@Override
	@Deprecated
	public void onMsgWrite(int state) {}

	@Override
	@Deprecated
	public void onMsgBack(byte[] msgBack) {}

	@Override
	public void onMsgBack(String pacName, String appName) {
		Log.e(TAG, pacName+" "+appName+" 已连接");
		Toast.makeText(mApp, pacName+" "+appName+"已连接", Toast.LENGTH_SHORT).show();
	}
	
	public static byte[] sendMsg(byte[] msg) {
		if(NationzSim.getConnectionState() == 16){
			return mNationzSim.wirteSync(msg);
		}
		EventBus.getDefault().post(new ToastEvent("蓝牙卡未连接"));
		return null;
	}

}
