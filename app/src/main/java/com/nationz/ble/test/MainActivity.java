package com.nationz.ble.test;

import java.util.ArrayList;
import java.util.List;

import com.example.bluetooth.le.DeviceControlActivity;
import com.nationz.bean.BleActionEvent;
import com.nationz.bean.BleEvent;
import com.nationz.bean.BleStateEvent;
import com.nationz.fragment.ConnectFragment;
import com.nationz.fragment.SimKeyFragment;
import com.nationz.ca.R;
import com.nationz.util.Constant;

import de.greenrobot.event.EventBus;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";
	private ImageView buleStateIV;
	private ViewPager mViewPager;
	private List<Fragment> mFragments;
	private TextView textTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_main);
		EventBus.getDefault().register(this);
		initView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		Log.e(TAG, "--- ON DESTROY ---");
	}

	public void onEventMainThread(BleEvent event) {
		Log.e(TAG, "onEventMainThread" + event.bleState);
		if (16 == event.bleState) {
			buleStateIV.setImageResource(R.drawable.bt_on);
		} else {
			buleStateIV.setImageResource(R.drawable.bt_off);
		}
	}
	
	public void onEventMainThread(BleStateEvent event) {
		Log.e(TAG, "onEventMainThread" + event.bleState);
		if (16 == event.bleState) {
			buleStateIV.setImageResource(R.drawable.bt_on);
		} else {
			buleStateIV.setImageResource(R.drawable.bt_off);
		}
	}

	private void initView() {

		String name = getIntent().getStringExtra(
				DeviceControlActivity.EXTRAS_DEVICE_NAME);
		Log.e(TAG, "name:" + name);

		SharedPreferences mSp = getSharedPreferences(Constant.SP_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mSp.edit();
		editor.putString(Constant.BLE_NAME_SUC, name);
		editor.commit();

		textTitle = (TextView) findViewById(R.id.tv_title);
		textTitle.setText("连接蓝牙卡");
		buleStateIV = (ImageView) findViewById(R.id.common_main_title_right_image);
		buleStateIV.setImageResource(R.drawable.bt_off);

		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mFragments = new ArrayList<Fragment>();

		Fragment mConnectFragment = new ConnectFragment();

		Fragment mSimKeyFragment = new SimKeyFragment();
		
		mFragments.add(mConnectFragment);
		
		mFragments.add(mSimKeyFragment);

		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int pos) {
				return mFragments.get(pos);
			}
		});

		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * tab改变监听器
	 * 
	 * @author zhai.yuehui
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				textTitle.setText("连接蓝牙卡");
				break;
			case 1:
				textTitle.setText("sim key测试");
				break;
			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}

		@Override
		public void onPageScrollStateChanged(int arg0) {}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		BleActionEvent mBleActionEvent = new BleActionEvent(1);
		EventBus.getDefault().post(mBleActionEvent);
	}

}
