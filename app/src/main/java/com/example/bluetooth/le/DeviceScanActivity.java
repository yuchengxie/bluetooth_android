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

import java.util.ArrayList;

import com.nationz.ble.test.MainActivity;
import com.nationz.ca.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
@SuppressLint("NewApi")
public class DeviceScanActivity extends ListActivity {
	private static final String TAG = DeviceScanActivity.class.getSimpleName();
	private LeDeviceListAdapter mLeDeviceListAdapter;
	private BluetoothAdapter mBluetoothAdapter;
	private boolean mScanning;
	private Handler mHandler;

	private static final int REQUEST_ENABLE_BT = 1;
	// 10���ֹͣ��������.
	private static final long SCAN_PERIOD = 10000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(R.string.title_devices);
		mHandler = new Handler();

		// ��鵱ǰ�ֻ��Ƿ�֧��ble ����,���֧���˳�����
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
					.show();
			finish();
		}

		// ��ʼ�� Bluetooth adapter,
		// ͨ�������������õ�һ���ο�����������(API����������android4.3�����ϺͰ汾)
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// ����豸���Ƿ�֧������
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.error_bluetooth_not_supported,
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		// TODO ����״̬�ı䡢�����豸�����״̬�ı䡢ɨ�����ɨ�迪ʼ
		IntentFilter mFilterBlue = new IntentFilter();
		mFilterBlue.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		mFilterBlue.addAction(BluetoothDevice.ACTION_FOUND);
		mFilterBlue.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		mFilterBlue.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		mFilterBlue.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		registerReceiver(mReceiver, mFilterBlue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		if (!mScanning) {
			menu.findItem(R.id.menu_stop).setVisible(false);
			menu.findItem(R.id.menu_scan).setVisible(true);
			menu.findItem(R.id.menu_refresh).setActionView(null);
		} else {
			menu.findItem(R.id.menu_stop).setVisible(true);
			menu.findItem(R.id.menu_scan).setVisible(false);
			menu.findItem(R.id.menu_refresh).setActionView(
					R.layout.actionbar_indeterminate_progress);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_scan:
			mLeDeviceListAdapter.clear();
			scanLeDevice(true);
			break;
		case R.id.menu_stop:
			scanLeDevice(false);
			break;
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}

		// Initializes list view adapter.
		mLeDeviceListAdapter = new LeDeviceListAdapter();
		setListAdapter(mLeDeviceListAdapter);
		scanLeDevice(true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// User chose not to enable Bluetooth.
		if (requestCode == REQUEST_ENABLE_BT
				&& resultCode == Activity.RESULT_CANCELED) {
			finish();
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		super.onPause();
		scanLeDevice(false);
		mLeDeviceListAdapter.clear();
	}

	/*
	 * @Override protected void onListItemClick(ListView l, View v, int
	 * position, long id) { final BluetoothDevice device =
	 * mLeDeviceListAdapter.getDevice(position); if (device == null) return;
	 * final Intent intent = new Intent(this, DeviceControlActivity.class);
	 * intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME,
	 * device.getName());
	 * intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS,
	 * device.getAddress()); if (mScanning) {
	 * mBluetoothAdapter.stopLeScan(mLeScanCallback); mScanning = false; }
	 * startActivity(intent); }
	 */

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
		if (device == null)
			return;
		// final Intent intent = new Intent(this, DeviceControlActivity.class);
		final Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME,
				device.getName());
		intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS,
				device.getAddress());
		if (mScanning) {
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
			mScanning = false;
		}
		
		startActivity(intent);
	}

	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					invalidateOptionsMenu();
				}
			}, SCAN_PERIOD);

			mScanning = true;
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
		invalidateOptionsMenu();
	}

	// Adapter for holding devices found through scanning.
	private class LeDeviceListAdapter extends BaseAdapter {
		private ArrayList<BluetoothDevice> mLeDevices;
		private LayoutInflater mInflator;

		public LeDeviceListAdapter() {
			super();
			mLeDevices = new ArrayList<BluetoothDevice>();
			mInflator = DeviceScanActivity.this.getLayoutInflater();
		}

		public void addDevice(BluetoothDevice device) {
			if (!mLeDevices.contains(device)) {
				mLeDevices.add(device);
			}
		}

		public BluetoothDevice getDevice(int position) {
			return mLeDevices.get(position);
		}

		public void clear() {
			mLeDevices.clear();
		}

		@Override
		public int getCount() {
			return mLeDevices.size();
		}

		@Override
		public Object getItem(int i) {
			return mLeDevices.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			ViewHolder viewHolder;
			// General ListView optimization code.
			if (view == null) {
				view = mInflator.inflate(R.layout.listitem_device, null);
				viewHolder = new ViewHolder();
				viewHolder.deviceAddress = (TextView) view
						.findViewById(R.id.device_address);
				viewHolder.deviceName = (TextView) view
						.findViewById(R.id.device_name);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			BluetoothDevice device = mLeDevices.get(i);
			final String deviceName = device.getName();
			if (deviceName != null && deviceName.length() > 0)
				viewHolder.deviceName.setText(deviceName);
			else
				viewHolder.deviceName.setText(R.string.unknown_device);
			viewHolder.deviceAddress.setText(device.getAddress());

			return view;
		}
	}

	// Device scan callback.
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice device, int rssi,
				byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Log.e(TAG, "device.getName()111111:" + device.getName());
					Log.e(TAG, "device.getAddress1111:" + device.getAddress());
					mLeDeviceListAdapter.addDevice(device);
					mLeDeviceListAdapter.notifyDataSetChanged();
				}
			});
		}
	};

	static class ViewHolder {
		TextView deviceName;
		TextView deviceAddress;
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, "****onReceive****[action:]" + action);
			if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				int currentState = intent.getIntExtra(
						BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
				switch (currentState) {
				case BluetoothAdapter.STATE_ON: // �����򿪳ɹ��¼�֪ͨ
					Log.i(TAG, "[onReceive] current state = ON");
					// �������ɹ�������� 1��ɨ�� 2������Ƿ����
					/*
					 * boolean isStartDiscovery =
					 * mBluetoothAdapter.startDiscovery(); if(isStartDiscovery){
					 * Log.i(TAG, "Suc to startDiscovery"); }else{ Log.i(TAG,
					 * "Fail to startDiscovery"); }
					 */

					scanLeDevice(true);

					break;
				case BluetoothAdapter.STATE_OFF:
					Log.i(TAG, "[onReceive] current state = OFF");
					break;
				case BluetoothAdapter.STATE_TURNING_ON:
					Log.i(TAG, "[onReceive] current state = TURNING_ON");
					break;
				case BluetoothAdapter.STATE_TURNING_OFF:
					Log.i(TAG, "[onReceive] current state = TURNING_OFF");
					break;
				}
			} else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				Log.d(TAG, "****onReceive****[BluetoothDevice.ACTION_FOUND]");
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Log.e(TAG, "device.getName():" + device.getName());
				Log.e(TAG, "device.getAddress:" + device.getAddress());

			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				// TODO ֹͣscan����δ����ƥ��key
				Toast.makeText(getApplicationContext(),
						"û�з������¼�˻�ƥ�������KEY��", Toast.LENGTH_LONG).show();
			} else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				// TODO ��ʼscan����δ��ƥ��key��hasNoKey��Ϊtrue
			} else if (action
					.equals(android.bluetooth.BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
				BluetoothDevice btDevices = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				if (BluetoothDevice.BOND_BONDED == btDevices.getBondState()) {
					Log.e(TAG, "��Գɹ���" + btDevices.getAddress());
				}

			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "--- ON DESTROY ---");
		unregisterReceiver(mReceiver);
	}
}