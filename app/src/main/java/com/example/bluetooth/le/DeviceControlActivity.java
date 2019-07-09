/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluetooth.le;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nationz.ca.R;

/**
 * For a given BLE device, this Activity provides the user interface to connect,
 * display data, and display GATT services and characteristics supported by the
 * device. The Activity communicates with {@code BluetoothLeService}, which in
 * turn interacts with the Bluetooth LE API.
 */
@SuppressLint("NewApi")
public class DeviceControlActivity extends Activity {
	private final static String TAG = DeviceControlActivity.class
			.getSimpleName();

	public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
	public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

	private TextView mConnectionState;
	private TextView mDataField;
	private String mDeviceName;
	private String mDeviceAddress;
	private ExpandableListView mGattServicesList;
	private BluetoothLeService mBluetoothLeService;
	private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
	private boolean mConnected = false;
	private BluetoothGattCharacteristic mNotifyCharacteristic;

	private final String LIST_NAME = "NAME";
	private final String LIST_UUID = "UUID";

	private Handler mHandler;

	// Add by zhai
	private EditText mTimesSendEdit;
	private EditText mContentSendEdit;
	private int mTimesSend = 100; // default 100
	private String mContentSend = "0123456789abcdefghil"; // 20 bytes
	private TextView mHaveSendT;
	private TextView mHaveGetSucT;
	private TextView mHaveGetErrT;
	private TextView mTimeConSuming;
	private boolean isTesting = false;

	private Long sTime;
	private Long eTime;
	private boolean isFirstPag = true;
	private Long time_FirstPag;

	// Code to manage Service lifecycle.
	private final ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service) {
			mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
					.getService();
			if (!mBluetoothLeService.initialize()) {
				Log.e(TAG, "Unable to initialize Bluetooth");
				finish();
			}
			// Automatically connects to the device upon successful start-up
			// initialization.
			mBluetoothLeService.connect(mDeviceAddress);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			mBluetoothLeService = null;
		}
	};

	// Handles various events fired by the Service.
	// ACTION_GATT_CONNECTED: connected to a GATT server.
	// ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
	// ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
	// ACTION_DATA_AVAILABLE: received data from the device. This can be a
	// result of read
	// or notification operations.
	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			final String action = intent.getAction();
			System.out.println("action = " + action);
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
				mConnected = true;
				updateConnectionState(R.string.connected);
				invalidateOptionsMenu();
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
					.equals(action)) {
				mConnected = false;
				updateConnectionState(R.string.disconnected);
				invalidateOptionsMenu();
				clearUI();
			} else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
					.equals(action)) {
				// Show all the supported services and characteristics on the
				// user interface.
				displayGattServices(mBluetoothLeService
						.getSupportedGattServices());
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				displayData(intent
						.getStringExtra(BluetoothLeService.EXTRA_DATA));

				// Add by zhai;
				String s = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
				if ("NationzBtKey".equals(s))
					return;
				countResult(s);

			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE_RX
					.equals(action)) {
				displayData(intent
						.getStringExtra(BluetoothLeService.EXTRA_DATA));

				// Add by zhai;
				String s = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
				Integer dataLen = intent.getIntExtra(
						BluetoothLeService.EXTRA_DATA_LEN, 0);
				countResult_RX(s, dataLen);

			}
		}
	};

	// If a given GATT characteristic is selected, check for supported features.
	// This sample
	// demonstrates 'Read' and 'Notify' features. See
	// http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for
	// the complete
	// list of supported characteristic features.
	private final ExpandableListView.OnChildClickListener servicesListClickListner = new ExpandableListView.OnChildClickListener() {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {

			if (mGattCharacteristics != null && !isTesting) {
				final BluetoothGattCharacteristic characteristic = mGattCharacteristics
						.get(groupPosition).get(childPosition);
				final int charaProp = characteristic.getProperties();

				if (characteristic.getUuid().toString()
						.equals("0000fff1-0000-1000-8000-00805f9b34fb")) {

					testing();
					mContentSend = mContentSendEdit.getText().toString();
					characteristic.setValue(mContentSend.getBytes());
					mNotifyCharacteristic = characteristic;

					mBluetoothLeService.setCharacteristicNotification(
							characteristic, true);

					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							myHandler.sendEmptyMessage(MSG_BLE);
						}
					}, 10);

				} else if (characteristic.getUuid().toString()
						.equals("6e400002-b5a3-f393-e0a9-e50e24dcca9e")) {
					testing();
					mContentSend = mContentSendEdit.getText().toString();
					characteristic.setValue(mContentSend.getBytes());
					mNotifyCharacteristic = characteristic;

					mBluetoothLeService.setCharacteristicNotification(
							characteristic, true);

					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							myHandler.sendEmptyMessage(MSG_BLE);
						}
					}, 10);
				} else if (characteristic.getUuid().toString()
						.equals("6e400003-b5a3-f393-e0a9-e50e24dcca9e")) {
					testing();

					mContentSend = mContentSendEdit.getText().toString();
					characteristic.setValue(mContentSend.getBytes());
					mNotifyCharacteristic = characteristic;

					mBluetoothLeService.setCharacteristicNotification(
							characteristic, true);

				} else if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
					// If there is an active notification on a characteristic,
					// clear
					// it first so it doesn't update the data field on the user
					// interface.
					Log.e(TAG, "charaProp1:" + charaProp);
					if (mNotifyCharacteristic != null) {
						mBluetoothLeService.setCharacteristicNotification(
								mNotifyCharacteristic, false);
						mNotifyCharacteristic = null;
					}
					mBluetoothLeService.readCharacteristic(characteristic);
				}

				return true;
			}
			return false;
		}
	};

	private void clearUI() {
		mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
		mDataField.setText(R.string.no_data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gatt_services_characteristics);

		mHandler = new Handler();

		final Intent intent = getIntent();
		mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
		mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

		// Sets up UI references.
		((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
		mGattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
		mGattServicesList.setOnChildClickListener(servicesListClickListner);
		mConnectionState = (TextView) findViewById(R.id.connection_state);
		mDataField = (TextView) findViewById(R.id.data_value);

		getActionBar().setTitle(mDeviceName);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		boolean bll = bindService(gattServiceIntent, mServiceConnection,
				BIND_AUTO_CREATE);
		if (bll) {
			System.out.println("---------------");
		} else {
			System.out.println("===============");
		}

		// Add by zhai
		mTimesSendEdit = (EditText) findViewById(R.id.times_edit);
		mContentSendEdit = (EditText) findViewById(R.id.content_send_20);
		mHaveSendT = (TextView) findViewById(R.id.send_times);
		mHaveGetSucT = (TextView) findViewById(R.id.get_times_suc);
		mHaveGetErrT = (TextView) findViewById(R.id.get_times_err);
		mTimeConSuming = (TextView) findViewById(R.id.times_consuming);
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		if (mBluetoothLeService != null) {
			final boolean result = mBluetoothLeService.connect(mDeviceAddress);
			Log.d(TAG, "Connect request result=" + result);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mGattUpdateReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mServiceConnection);
		mBluetoothLeService = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.gatt_services, menu);
		if (mConnected) {
			menu.findItem(R.id.menu_connect).setVisible(false);
			menu.findItem(R.id.menu_disconnect).setVisible(true);
		} else {
			menu.findItem(R.id.menu_connect).setVisible(true);
			menu.findItem(R.id.menu_disconnect).setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_connect:
			mBluetoothLeService.connect(mDeviceAddress);
			return true;
		case R.id.menu_disconnect:
			mBluetoothLeService.disconnect();
			return true;
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateConnectionState(final int resourceId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mConnectionState.setText(resourceId);
			}
		});
	}

	private void displayData(String data) {
		if (data != null) {
			mDataField.setText(data);
		}
	}

	// Demonstrates how to iterate through the supported GATT
	// Services/Characteristics.
	// In this sample, we populate the data structure that is bound to the
	// ExpandableListView
	// on the UI.
	private void displayGattServices(List<BluetoothGattService> gattServices) {
		if (gattServices == null)
			return;
		String uuid = null;
		String unknownServiceString = getResources().getString(
				R.string.unknown_service);
		String unknownCharaString = getResources().getString(
				R.string.unknown_characteristic);
		ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
		ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
		mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

		// Loops through available GATT Services.
		for (BluetoothGattService gattService : gattServices) {
			HashMap<String, String> currentServiceData = new HashMap<String, String>();
			uuid = gattService.getUuid().toString();
			currentServiceData.put(LIST_NAME,
					SampleGattAttributes.lookup(uuid, unknownServiceString));
			currentServiceData.put(LIST_UUID, uuid);
			gattServiceData.add(currentServiceData);

			ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
			List<BluetoothGattCharacteristic> gattCharacteristics = gattService
					.getCharacteristics();
			ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

			// Loops through available Characteristics.
			for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
				charas.add(gattCharacteristic);
				HashMap<String, String> currentCharaData = new HashMap<String, String>();
				uuid = gattCharacteristic.getUuid().toString();
				currentCharaData.put(LIST_NAME,
						SampleGattAttributes.lookup(uuid, unknownCharaString));
				currentCharaData.put(LIST_UUID, uuid);
				gattCharacteristicGroupData.add(currentCharaData);
			}
			mGattCharacteristics.add(charas);
			gattCharacteristicData.add(gattCharacteristicGroupData);
		}

		SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
				this, gattServiceData,
				android.R.layout.simple_expandable_list_item_2, new String[] {
						LIST_NAME, LIST_UUID }, new int[] { android.R.id.text1,
						android.R.id.text2 }, gattCharacteristicData,
				android.R.layout.simple_expandable_list_item_2, new String[] {
						LIST_NAME, LIST_UUID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		mGattServicesList.setAdapter(gattServiceAdapter);
	}

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE_RX);
		return intentFilter;
	}

	// Add by zhai
	private static final int MSG_BLE = 1;
	@SuppressLint("HandlerLeak")
	Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_BLE:
				Integer t = Integer.parseInt(mTimesSendEdit.getText()
						.toString());
				Integer ts = Integer.parseInt(mHaveSendT.getText().toString());
				if (t > ts) {
					Log.e(TAG, "mContentSend:" + mContentSend);
					mNotifyCharacteristic.setValue(mContentSend.getBytes());

					if (!(mBluetoothLeService == null)) {
						mBluetoothLeService
								.wirteCharacteristic(mNotifyCharacteristic);
					} else {
						Toast.makeText(getApplicationContext(),
								"mBluetoothLeService:null", Toast.LENGTH_SHORT)
								.show();
					}

					byte[] data = mNotifyCharacteristic.getValue();
					String d = new String(data);
					Log.e(TAG, "mNotifyCharacteristic.getValue():" + d);
					ts++;

					mHaveSendT.setText(ts.toString());

				}

				break;
			default:
				break;
			}
		};
	};

	// Add by zhai
	@SuppressLint("ShowToast")
	private void countResult(String data) {

		Integer t_all = Integer.parseInt(mTimesSendEdit.getText().toString());
		Integer t_s = Integer.parseInt(mHaveSendT.getText().toString());

		if (t_all > t_s) {
			myHandler.sendEmptyMessage(MSG_BLE);
		} else {
			stopTest();
		}

		if (mContentSend.equals(data)) {
			String t_g_s = mHaveGetSucT.getText().toString();
			if (t_g_s == null) {
				t_g_s = "0";
			}
			Integer t = Integer.parseInt(t_g_s);
			t++;
			mHaveGetSucT.setText(t.toString());
			Log.e(TAG, "get suc times:" + t + "get data:" + data);
		} else {
			// TODO
			Toast.makeText(this, "back data:" + data, Toast.LENGTH_SHORT)
					.show();
			String t_g_e = mHaveGetErrT.getText().toString();
			if (t_g_e == null) {
				t_g_e = "0";
			}
			Integer t = Integer.parseInt(t_g_e);
			t++;
			mHaveGetErrT.setText(t.toString());
			Log.e(TAG, "get err times:" + t + "get data:" + data);
		}
	}

	private void countResult_RX(String data, Integer dataLen) {

		Integer allLen = Integer.parseInt(mHaveSendT.getText().toString());
		allLen += dataLen;
		mHaveSendT.setText(allLen.toString());

		if (isFirstPag) {
			time_FirstPag = System.currentTimeMillis();
			isFirstPag = false;
		} else {
			Long t = System.currentTimeMillis() - time_FirstPag;
			mTimeConSuming.setText(t.toString() + " ms");
		}

		if (mContentSend.equals(data)) {
			String t_g_s = mHaveGetSucT.getText().toString();
			if (t_g_s == null) {
				t_g_s = "0";
			}
			Integer t = Integer.parseInt(t_g_s);
			t++;
			mHaveGetSucT.setText(t.toString());
			Log.e(TAG, "get suc times:" + t + "get data:" + data);
		} else {
			String t_g_e = mHaveGetErrT.getText().toString();
			if (t_g_e == null) {
				t_g_e = "0";
			}
			Integer t = Integer.parseInt(t_g_e);
			t++;
			mHaveGetErrT.setText(t.toString());
			Log.e(TAG, "get err times:" + t + "get data:" + data);
		}
	}

	private void testing() {
		isTesting = true;
		mTimesSendEdit.setEnabled(false);
		mContentSendEdit.setEnabled(false);

		mHaveSendT.setText("0");
		mHaveGetSucT.setText("0");
		mHaveGetErrT.setText("0");

		sTime = System.currentTimeMillis();
		mTimeConSuming.setText("0 ms");
	}

	private void stopTest() {
		isTesting = false;
		mTimesSendEdit.setEnabled(true);
		mContentSendEdit.setEnabled(true);
		eTime = System.currentTimeMillis();
		Long t = eTime - sTime;
		mTimeConSuming.setText(t.toString() + " ms");
	}

}
