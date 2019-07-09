package com.nationz.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

public class Util {
	private static final boolean D = true;

	public static void printHexByte(Byte[] bytes) {
		String s = "";
		for (int i = 0; i < bytes.length; i++) {
			s += String.format("%02X ", bytes[i]);
		}
		if (D)
			Log.d("debug", s);
	}

	public synchronized static void printHexbyte(byte[] bytes) {
		String s = "";
		try {
			for (int i = 0; i < bytes.length; i++) {
				s += String.format("%02X ", bytes[i]);
			}
			if (D)
				Log.d("debug", s);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public synchronized static void printHexbyte(String tt, byte[] bytes) {
		String s = "";
		try {
			for (int i = 0; i < bytes.length; i++) {
				s += String.format("%02X ", bytes[i]);
			}
			if (D)
				Log.e(tt, s);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String bytes2Hex(byte[] data) {
		if (data == null || data.length == 0) {
			return null;
		}
		String ret = "";
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(data[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public static byte[] hexToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] data = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			data[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return data;
	}

	public static int strIndexToInt(String str) {
		return Util.charToByte(str.toUpperCase().toCharArray()[0]) << 4
				| Util.charToByte(str.toUpperCase().toCharArray()[1]);
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	// 判断是否执行成功
	public static boolean is90(byte[] data) {
		if (data == null) {
			return false;
		}
		int length = data.length;
		if ((data[length - 1] == (byte) 0x00)
				&& (data[length - 2] == (byte) 0x90)) {
			return true;
		}
		return false;
	}

	public static int get2Byteslength(byte[] len) {
		int dataLen;
		dataLen = (len[0] & 0xFF) << 8;
		dataLen += len[1] & 0xFF;
		return dataLen;
	}

	public static byte[] get2Byteslength(int len) {
		byte[] dataLen = new byte[2];
		dataLen[0] = (byte) (len >> 8);
		dataLen[1] = (byte) len;
		return dataLen;
	}

	public static String getRandomString(int length) {
		String str = "ABCDEF0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(16);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	public static char getRandomChar() {
		String str = "ABCDEF0123456789";
		Random random = new Random();
		int number = random.nextInt(16);
		return str.charAt(number);
	}

	public static boolean checkSum(byte[] data1, byte[] data2) {
		if (getSum(data1) == getSum(data2)) {
			return true;
		}
		return false;
	}

	private static int getSum(byte[] msg) {
		int mSum = 0;
		for (int liv_Count = 0; liv_Count < msg.length; liv_Count++) {
			int mNum = msg[liv_Count];

			if (mNum < 0) {
				mNum += 256;
			}

			mSum += mNum;
		}
		return mSum;
	}

	/**
	 * 数据格式转换相关 字符串格式化，每两个字符间间隔一位 补齐源字符str,fulllength为目标字节数
	 */
	public static String fillStr1(String str, int fullLength) {
		StringBuilder backStr = new StringBuilder();
		int length = str.length();
		backStr.append(str);
		for (int i = 0; i < fullLength * 2 - length; i++) {
			backStr.append("0");
		}
		return backStr.toString();
	}

	public static String fillStr(String str, int fullLength) {
		StringBuilder backStr = new StringBuilder();
		int length = str.length() / 2;
		backStr.append(str);
		for (int i = 0; i < fullLength - length; i++) {
			backStr.append("00");
		}
		return backStr.toString();
	}

	public static String formatStr(String str) {
		StringBuilder backStr = new StringBuilder();
		int length = str.length();
		for (int i = 0; i < length / 2; i++) {
			String item = str.substring(i * 2, i * 2 + 2);
			backStr.append(item + " ");
		}
		return backStr.toString();
	}

	public static boolean haveBleParams(Context context) {
		SharedPreferences mSp = context.getSharedPreferences(Constant.SP_NAME,
				Activity.MODE_PRIVATE);
		String name = mSp.getString(Constant.BLE_NAME_SUC, "");
		String pin = mSp.getString(Constant.BLE_PIN_SUC, "");

		if (name == null || name.length() != 12 || pin == null
				|| pin.length() != 6) {
			return false;
		}

		return true;
	}

	private static BluetoothManager mBluetoothManager;
	private static BluetoothAdapter mBluetoothAdapter;

	@SuppressLint("NewApi")
	private static boolean isBle(Context context) {
		if (!context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(context, "不支持低功耗蓝牙", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) context
					.getSystemService(Context.BLUETOOTH_SERVICE);
			if (mBluetoothManager == null) {
				Toast.makeText(context, "不支持低功耗蓝牙", Toast.LENGTH_SHORT).show();
				return false;
			}
		}

		mBluetoothAdapter = mBluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(context, "不支持低功耗蓝牙", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	public static String getStringFromFile(String fileName, String code) {
		try {
			StringBuffer sBuffer = new StringBuffer();

			FileInputStream fInputStream = new FileInputStream(fileName);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fInputStream, code);
			BufferedReader in = new BufferedReader(inputStreamReader);
			if (!new File(fileName).exists()) {
				return null;
			}
			while (in.ready()) {
				// sBuffer.append(in.readLine() + "\n");
				sBuffer.append(in.readLine() + ";");
			}
			in.close();
			return sBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}