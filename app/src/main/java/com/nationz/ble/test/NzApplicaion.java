/**zhai.yuehui
 * 2016-5-3
 * 下午7:12:50
 */
package com.nationz.ble.test;

import com.nationz.bean.BleActionEvent;
import com.nationz.util.NzBleUtil;

import de.greenrobot.event.EventBus;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

/**
 * @author zhai.yuehui
 */
public class NzApplicaion extends Application {
	
	private static final String TAG = "NzApplicaion";
	
	private NzBleUtil mNzBleUtil;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "--- onCreate ---"+NzApplicaion.this);
		EventBus.getDefault().register(this);
		mNzBleUtil = NzBleUtil.getInstance(this);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		EventBus.getDefault().unregister(this);
	}
	
	public void onEventMainThread(BleActionEvent event) {
		if(event.getActionType() == 0){
			toConnectNzBleSim(event.getTimeout(),event.getName(),event.getPin());
		}
		
		if(event.getActionType() == 1){
			toDisconnectNzBleSim();
		}
		
		if(event.getActionType() == 2){
			toCloseBle();
		}
	}
	
	private void toConnectNzBleSim(int timeout, String name, String pin){
		if (name == null || name.length() != 12 || pin == null
				|| pin.length() != 6) {
			return;
		}
		
		Toast.makeText(NzApplicaion.this, "正在连接蓝牙卡，请稍候",
				Toast.LENGTH_LONG).show();
		if(mNzBleUtil!=null){
			mNzBleUtil.connect(name, pin, timeout);
		}
		
	}
	
	private void toDisconnectNzBleSim(){
		if(mNzBleUtil!=null){
			mNzBleUtil.close();
		}
	}
	
	private void toCloseBle(){
		if(mNzBleUtil!=null){
			mNzBleUtil.closeBle();
		}
	}
	
}
