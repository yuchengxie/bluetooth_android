/**
 * Copyright (c) 2015-2016 Nationz
 * 
 */
package com.nationz.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import android.os.Environment;
import android.util.Log;

import com.nationz.bean.ToastEvent;
import com.nationz.client.ClientForCaApplet;
import com.nationz.exception.BleStateException;
import com.nationz.exception.AppletStateException;
import com.nationz.exception.PinException;
import com.nationz.util.Logz;
import com.nationz.util.NzBleUtil;
import com.nationz.util.Util;

import de.greenrobot.event.EventBus;

/**
 * @author 作者 E-mail:zhai.yuehui@nationz.com.cn
 * @version 创建时间：2016-3-17 下午6:08:55 类说明
 */
public class TaskSimKey {
	private static final String TAG = "TaskSimKey";

	static String privateKey = "01020304050607080807060504030201";
	static String enText = "0203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203010405060908";
	static String text_128 = "0102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708";
	static String text_256 = "01020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708";
	static String text_32 = "0102030405060708010203040506070801020304050607080102030405060708";
	static String text_1024 = "01020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070801020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070801020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708090001020304050607080900010203040506070809000102030405060708";
	static String text_10 = "01020304050607080900";

	byte[] miwen = null;

	String duiChenEnText = "0203040506070809";
	byte[] duiChenMiWen = null;

	String signDataIn = "01020304050607080900";
	byte[] signDataOut = null;

	public void start(int type){

		ClientForCaApplet ClientForCaApplet = new ClientForCaApplet(NzBleUtil.getNationzSim());
		try {
			ClientForCaApplet.selectCaApplet();
		} catch (AppletStateException e) {
			e.printStackTrace();
		} catch (BleStateException e) {
			e.printStackTrace();
		}
		ToastEvent mToastEvent = new ToastEvent();
		StringBuilder sb = new StringBuilder();
		switch (type) {
		case 0:
			Logz.i(TAG, "generateKeyPair begin");
			Map<String, Object> map0 = null;
			try {
				map0 = ClientForCaApplet.generateKeyPair(2);
			} catch (AppletStateException e) {
				e.printStackTrace();
			} catch (BleStateException e) {
				e.printStackTrace();
			}

			if ((Integer) map0.get("ret") == 0) {
				sb.append("index:");
				sb.append(map0.get("index"));
				sb.append("\n publicKey:");
				sb.append(map0.get("publicKey"));
			} else {
				sb.append("errInfo:");
				sb.append(map0.get("errInfo"));
			}

			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);
			break;
		case 1:
			Logz.i(TAG, "importSM2PrivateKey begin");
			String privateKeyIn = "C9F615508862E302378989EEF381BA3589CCA39E3C994DB73C12DC94EAEFFC10";
			int indexPriKey = 0;
			try {
				indexPriKey = ClientForCaApplet.importSM2PrivateKey(Util.hexToBytes(privateKeyIn));
			} catch (AppletStateException e) {
				e.printStackTrace();
			} catch (BleStateException e) {
				e.printStackTrace();
			}
			sb.append("indexPriKey:");
			sb.append(String.valueOf(indexPriKey));
			
			String publicKeyIn = "44F2B83A258314A3C90C34A2A70F90D521997E83EB0BC222A014B7D8CF273D1F66F1BA6FD474EFC1D8F1632011E378C636AF95F8C0ECE8A1CF373DD9B83ED103";
			int indexPubKey = 0;
			try {
				indexPubKey = ClientForCaApplet.importSM2PublicKey(Util.hexToBytes(publicKeyIn),indexPriKey);
			} catch (AppletStateException e) {
				e.printStackTrace();
			} catch (BleStateException e) {
				e.printStackTrace();
			}
			sb.append("\n");
			sb.append("publicKeyIn:");
			sb.append(String.valueOf(indexPubKey));
			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);
			break;
		case 2:
			Logz.i(TAG, "importCert begin");
			String certStr = "MIICMTCCAdagAwIBAgIUciW4FRGzaLvQW76KmUcYgTp4REowDAYIKoEcz1UBg3UFADCBiTEvMC0GA1UEAwwm5aSp5aiB6K+a5L+hUk9PQS1TTTLor4HkuabvvIjmtYvor5XvvIkxJjAkBgNVBAsMHei/kOe7tOmDqFJPT0EtU00y77yI5rWL6K+V77yJMSEwHwYDVQQKDBjlpKnlqIHor5rkv6HvvIjmtYvor5XvvIkxCzAJBgNVBAYTAkNOMB4XDTE2MDQyNjA3MDgwOFoXDTE2MDUyNjA3MDgwOFowSDEYMBYGA1UEAwwPdGVzdDIwMTUxMjEzMDIzMRUwEwYDVQQLDAznp7vliqjova/ku7YxFTATBgNVBAoMDOWbveawkeaKgOacrzBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABCzg+5G513CyK2KNxpzRW9RZU4mbPYmz+vDdRBBG+imkw+LKWajrqN7mx0pflcVGt9cu0+IXdZ9Hl16C/z88/L6jWjBYMAkGA1UdEwQCMAAwCwYDVR0PBAQDAgbAMB8GA1UdIwQYMBaAFKFGVzjz9O7+5qTGAEd5SEYn3JsaMB0GA1UdDgQWBBTVroWojItEfFV02i7FrigICCoD1DAMBggqgRzPVQGDdQUAA0cAMEQCILM/d0pYMvJazI9PnWnMD5Lg+bJvPXhYoXGZ75P2KYt2AiBnW+f9GzLj/M3Ohqq/B0KUu2HrGP2DptgstgrA2cqDhw==";

			boolean importCertFlag = false;
			try {
				importCertFlag = ClientForCaApplet.importCert(0, certStr);
				sb.append("importCert suc");
			} catch (AppletStateException e) {
				e.printStackTrace();
				sb.append("importCert fail errInfo:");
				sb.append(e.getMessage());
			} catch (BleStateException e) {
				e.printStackTrace();
				sb.append("importCert fail errInfo:");
				sb.append(e.getMessage());
			}
			
			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);
			break;
		case 3:
			Logz.i(TAG, "exportCert begin");
			try {
				ClientForCaApplet.verifyUserPin("000000");
			} catch (AppletStateException e4) {
				e4.printStackTrace();
			} catch (BleStateException e4) {
				e4.printStackTrace();
			} catch (PinException e4) {
				e4.printStackTrace();
				sb.append("exportCert fail errInfo:");
				sb.append(e4.getMessage());
				sb.append("\n 还可以尝试:"+e4.getCanTryTimes()+"次");
			}
			String exCertStr = null;
			try {
				exCertStr = ClientForCaApplet.exportCert(0);
			} catch (AppletStateException e) {
				e.printStackTrace();
				sb.append("exportCert fail errInfo:");
				sb.append(e.getMessage());
			} catch (BleStateException e) {
				e.printStackTrace();
				sb.append("exportCert fail errInfo:");
				sb.append(e.getMessage());
			}
			if (exCertStr != null) {
				sb.append("exportCert suc");
				sb.append("\n CertStr:");
				sb.append(exCertStr);
			}
			
			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);
			break;
		case 4:
			Logz.i(TAG, "encryptMessage begin");
			try {
				ClientForCaApplet.verifyUserPin("000000");
			} catch (AppletStateException e4) {
				e4.printStackTrace();
			} catch (BleStateException e4) {
				e4.printStackTrace();
			} catch (PinException e4) {
				e4.printStackTrace();
				sb.append("exportCert fail errInfo:");
				sb.append(e4.getMessage());
				sb.append("\n 还可以尝试:"+e4.getCanTryTimes()+"次");
			}
			try {
				miwen = ClientForCaApplet.encryptMessage(0, Util.hexToBytes(text_128));
			} catch (AppletStateException e) {
				e.printStackTrace();
				sb.append("encryptMessage fail errInfo:");
				sb.append(e.getMessage());
			} catch (BleStateException e) {
				e.printStackTrace();
				sb.append("encryptMessage fail errInfo:");
				sb.append(e.getMessage());
			}
			if (miwen != null) {
				Log.e(TAG, "miwen.length():" + miwen.length);
				sb.append("encryptMessage suc");
				sb.append("\n encryptMessage:");
				sb.append(Util.bytes2Hex(miwen));
			} 
			
			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);

			Logz.i(TAG, "miwen:" + miwen);
			Logz.i(TAG, "decryptMessage begin");
			
			if(miwen == null)return;
			
			byte[] mingwen = null;
			try {
				mingwen = ClientForCaApplet.decryptMessage(0, miwen);
			} catch (AppletStateException e) {
				e.printStackTrace();
				sb.append("\ndecryptMessage fail errInfo:");
				sb.append(e.getMessage());
			} catch (BleStateException e) {
				e.printStackTrace();
				sb.append("\ndecryptMessage fail errInfo:");
				sb.append(e.getMessage());
			}
			if (mingwen != null) {
				sb.append("\ndecryptMessage suc");
				sb.append("\n decryptMessage:");
				sb.append(Util.bytes2Hex(mingwen));
			}
			
			Logz.i(TAG, "mingwen:" + mingwen);
			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);
			break;
		case 5:
			Logz.i(TAG, "symmetricEncry begin");
			try {
				duiChenMiWen = ClientForCaApplet.symmetricEncry(Util.hexToBytes(duiChenEnText),
						Util.hexToBytes(privateKey), 1, Util.hexToBytes("0011223344556677"));
			} catch (AppletStateException e) {
				e.printStackTrace();
				sb.append("symmetricEncry fail errInfo:");
				sb.append(e.getMessage());
			} catch (BleStateException e) {
				e.printStackTrace();
				sb.append("symmetricEncry fail errInfo:");
				sb.append(e.getMessage());
			}
			if (duiChenMiWen != null) {
				sb.append("symmetricEncry suc");
				sb.append("\n symmetricEncry:");
				sb.append(Util.bytes2Hex(duiChenMiWen));
			} 
			
			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);

			Logz.i(TAG, "symmetricDecry begin");
			byte[] duiChenMingWen = null;
			try {
				duiChenMingWen = ClientForCaApplet.symmetricDecry(
						duiChenMiWen, Util.hexToBytes(privateKey), 1, Util.hexToBytes("0011223344556677"));
			} catch (AppletStateException e) {
				e.printStackTrace();
				sb.append("\n symmetricDecry fail errInfo:");
				sb.append(e.getMessage());
			} catch (BleStateException e) {
				e.printStackTrace();
				sb.append("\n symmetricDecry fail errInfo:");
				sb.append(e.getMessage());
			}
			if (duiChenMiWen != null) {
				sb.append("\n symmetricDecry suc");
				sb.append("\n symmetricDecry:");
				sb.append(Util.bytes2Hex(duiChenMingWen));
			} 

			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);

			break;
		case 6:
			Logz.i(TAG, "signMessage begin");
			try {
				ClientForCaApplet.verifyUserPin("000000");
			} catch (AppletStateException e4) {
				e4.printStackTrace();
			} catch (BleStateException e4) {
				e4.printStackTrace();
			} catch (PinException e4) {
				e4.printStackTrace();
				sb.append("exportCert fail errInfo:");
				sb.append(e4.getMessage());
				sb.append("\n 还可以尝试:"+e4.getCanTryTimes()+"次");
			}
			String text_orign = "test_msg";
			Util.printHexbyte("test_msg", text_orign.getBytes());
			
			try {
				signDataOut = ClientForCaApplet.signMessage(1,text_orign.getBytes());
			} catch (AppletStateException e) {
				e.printStackTrace();
				sb.append("\n signMessage fail errInfo:");
				sb.append(e.getMessage());
			} catch (BleStateException e) {
				e.printStackTrace();
				sb.append("\n signMessage fail errInfo:");
				sb.append(e.getMessage());
			}
			if (signDataOut != null) {
				Log.e(TAG, "signDataOut length:" + signDataOut.length);
				sb.append("signMessage suc");
				sb.append("\n signMessage:");
				sb.append(Util.bytes2Hex(signDataOut));
			}
			
			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);
			Log.e(TAG, "signDataOut:"+Util.bytes2Hex(signDataOut));
			
			Logz.i(TAG, "verifyMessage begin");
			boolean verifyOut = false;
			try {
				verifyOut = ClientForCaApplet.verifyMessage(1,text_orign.getBytes(), signDataOut);
			} catch (AppletStateException e) {
				e.printStackTrace();
				sb.append("\n verifyMessage fail errInfo:");
				sb.append(e.getMessage());
			} catch (BleStateException e) {
				e.printStackTrace();
				sb.append("\n verifyMessage fail errInfo:");
				sb.append(e.getMessage());
			}
			if (verifyOut) {
				sb.append("\n verifyMessage suc");
			} 
			
			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);
			break;
		case 7:

			Logz.i(TAG, "verifyUserPin begin");
			boolean veriPin = false;
			try {
				veriPin = ClientForCaApplet.verifyUserPin("000000");
			} catch (AppletStateException e1) {
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (BleStateException e1) {
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (PinException e1) {
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			}
			if (veriPin) {
				sb.append("verifyUserPin suc");
			} 

			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);

			Logz.i(TAG, "changeUserPin begin");
			boolean changePin = false;
			try {
				changePin = ClientForCaApplet.changeUserPin("000000", "123456");
			} catch (AppletStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (BleStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (PinException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			}
			if (changePin) {
				sb.append("\n changeUserPin suc");
			} 

			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);

			Logz.i(TAG, "verifyUserPin begin");
			boolean veriPin2 = false;
			try {
				veriPin2 = ClientForCaApplet.verifyUserPin("123456");
			} catch (AppletStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (BleStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (PinException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			}
			if (veriPin2) {
				sb.append("\n verifyUserPin suc");
			} 

			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);

			Logz.i(TAG, "reloadPin begin");
			boolean reloadPin = false;
			try {
				reloadPin = ClientForCaApplet.reloadPin();
			} catch (AppletStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (BleStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			}

			if (reloadPin) {
				sb.append("\n reloadPin suc");
			} 

			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);

			break;
		case 8:
			Logz.i(TAG, "exportPublicKey begin");
			Map<String, Object> map99 = null;
			try {
				map99 = ClientForCaApplet.exportPublicKey(0);
			} catch (AppletStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (BleStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			}
			if ((Integer) map99.get("ret") == 0) {
				sb.append("\n exportPublicKey suc");
				sb.append("index:");
				sb.append(map99.get("index"));
				sb.append("\n publicKey:");
				sb.append(map99.get("publicKey"));
				sb.append("\n e:");
				sb.append(map99.get("e"));
				sb.append("\n n:");
				sb.append(map99.get("n"));
			} 
			
			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);
			break;
		case 9:
			Logz.i(TAG, "deleteCert begin");
			
			try {
				List<Integer> list = ClientForCaApplet.getAllCertIndex();
				for (Integer index : list) {
					Log.e(TAG, "will delete cert: " + index);
					if (ClientForCaApplet.deleteCert(index, false)) {
						sb.append("\n "+"delete cert: " + index + " suc");
						Log.e(TAG, "delete cert: " + index + " suc");
					} else {
						sb.append("\n "+"delete cert: " + index + " fail");
						Log.e(TAG, "delete cert: " + index + " fail");
					}
				}
			} catch (BleStateException e3) {
				e3.printStackTrace();
			} catch (AppletStateException e3) {
				e3.printStackTrace();
			}
			
			Logz.i(TAG, "deleteKey begin");
			try {
				List<Integer> list = ClientForCaApplet.getAllKeyIndex();
				for (Integer index : list) {
					Log.e(TAG, "will delete key: " + index);
					if (ClientForCaApplet.deleteKey(index)) {
						sb.append("\n "+"delete key: " + index + " suc");
						Log.e(TAG, "delete key: " + index + " suc");
					} else {
						sb.append("\n "+"delete key: " + index + " fail");
						Log.e(TAG, "delete key: " + index + " fail");
					}
				}
			} catch (BleStateException e3) {
				e3.printStackTrace();
			} catch (AppletStateException e3) {
				e3.printStackTrace();
			}

			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);

			break;
		case 10:
			Logz.i(TAG, "encryptBySm1 begin");
			String sm1Key = "11223344556677881122334455667788";
			String sm1EnText = "11223344556677881122334455667788";
			byte[] MiWenSm1 = null;
			try {
				MiWenSm1 = ClientForCaApplet.encryptBySm1(Util.hexToBytes(sm1EnText),
						Util.hexToBytes(sm1Key), 1, Util.hexToBytes("00112233445566777766554433221100"));
			} catch (AppletStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (BleStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			}
			if (MiWenSm1 != null) {
				sb.append("encryptBySm1 suc");
				sb.append("\n encryptBySm1:");
				sb.append(Util.bytes2Hex(MiWenSm1));
			} 

			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);

			Logz.i(TAG, "decryptBySm1 begin");
			byte[] MingWenSm1 = null;
			try {
				MingWenSm1 = ClientForCaApplet.decryptBySm1(
						MiWenSm1, Util.hexToBytes(sm1Key), 1, Util.hexToBytes("00112233445566777766554433221100"));
			} catch (AppletStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			} catch (BleStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				sb.append("errInfo:");
				sb.append(e1.getMessage());
			}
			if (MingWenSm1 != null) {
				sb.append("\n decryptBySm1 suc");
				sb.append("\n decryptBySm1:");
				sb.append(Util.bytes2Hex(MingWenSm1));
			} 

			mToastEvent.setText(sb.toString());
			EventBus.getDefault().post(mToastEvent);
			

			break;
		case 11:
			//测试证书20160918签名  测试证书20160918加密
			/*String DEFAULT_PATH = "/nzca/测试证书20160918签名.pfx";
			String mPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + DEFAULT_PATH;
			
			ReadPFX mReadPFX = new ReadPFX();
			RSAPrivateKey priKey = (RSAPrivateKey)mReadPFX.getPvkformPfx(mPath, "123456");
			
			Log.e("RSAPrivateKey getModulus", priKey.getModulus().toString());
			Log.e("RSAPrivateKey getPrivateExponent", priKey.getPrivateExponent().toString());
			
			Util.printHexbyte("RSAPrivateKey getModulus", priKey.getModulus().toByteArray());
			Util.printHexbyte("RSAPrivateKey getPrivateExponent", priKey.getPrivateExponent().toByteArray());
			*/
			
			try {
				importPfxCert(ClientForCaApplet);
			} catch (AppletStateException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (BleStateException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
		case 12:
			//int index = importKey(ClientForCaApplet);
			
			String strPfs = "strPfs";
			try {
				ClientForCaApplet.verifyUserPin("000000");
			} catch (AppletStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (BleStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (PinException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			byte[] bStrPfs = null;
			try {
				bStrPfs = ClientForCaApplet.signMessage(5, strPfs.getBytes());
			} catch (AppletStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BleStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				ClientForCaApplet.verifyMessage(5, strPfs.getBytes(), bStrPfs);
			} catch (AppletStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BleStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		case 13:
			
			try {
				importKey(ClientForCaApplet);
			} catch (AppletStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BleStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}
	
	private int importKey(ClientForCaApplet ClientForCaApplet) throws AppletStateException,
	BleStateException {
		String priKey = "cfc42490d745183312a8267b39367fc5376e51984715d770434524a4803c806a40debac21b0ab56d8aa185dd9934ec56823205b1ec1bdbf82ba69699490810c72a2fde4817762d0678d9f58c19a21a04f53b352008b7e63713716610b6f235e8e92f347cd188da13f894dc2a40f3db6c00510c54a57d4e24bfaaba940fb83dfb370000820b4ab2364af9127eca4d80137032170d8dbbdd754d898f42b0e3d346cd7768cf1ba38115c77e2cd6d5c8e9cb98d9af224393b86e80f7afa4526dfd2a916b7b7ea89092ade5677bdb36f65d27b56cb984b21085320f423e0be0ff2213319aea3ceabdf0e521fa467d6e76295169d6ad21039f0fb30374536ba183c141";
		String pubKey = "cfc42490d745183312a8267b39367fc5376e51984715d770434524a4803c806a40debac21b0ab56d8aa185dd9934ec56823205b1ec1bdbf82ba69699490810c72a2fde4817762d0678d9f58c19a21a04f53b352008b7e63713716610b6f235e8e92f347cd188da13f894dc2a40f3db6c00510c54a57d4e24bfaaba940fb83dfb00010001";
		
		int index = ClientForCaApplet.importRsa1024PrivateKey(Util.hexToBytes(priKey));
		ClientForCaApplet.importRsa1024PublicKey(Util.hexToBytes(pubKey), (byte)index);
		
		return index;
	}
	
	public void importPfxCert(ClientForCaApplet ClientForCaApplet) throws AppletStateException,
	BleStateException {
		
		String DEFAULT_PATH = "/nzca/测试证书20160918签名.pfx";
		String mPath = Environment.getExternalStorageDirectory()
		.getAbsolutePath() + DEFAULT_PATH;
		
		getPvkformPfx(ClientForCaApplet, mPath, "123456");
		
	}
	
	public PrivateKey getPvkformPfx(ClientForCaApplet ClientForCaApplet, String strPfx, String strPassword) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(strPfx);
			
			
			//byte[] byt = new byte[fis.available()];
			//fis.read(byt);
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			char[] nPassword = null;
			if ((strPassword == null) || strPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = strPassword.toCharArray();
			}
			
			ks.load(fis, nPassword);
			
			fis.close();
			System.out.println("keystore type=" + ks.getType());
			// Now we loop all the aliases, we need the alias to get keys.
			// It seems that this value is the "Friendly name" field in the
			// detals tab <-- Certificate window <-- view <-- Certificate
			// Button <-- Content tab <-- Internet Options <-- Tools menu
			// In MS IE 6.
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
				System.out.println("alias=[" + keyAlias + "]");
			}
			// Now once we know the alias, we could get the keys.
			System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();
			System.out.println("cert class = " + cert.getClass().getName());
			System.out.println("cert = " + cert);
			System.out.println("public key = " + pubkey);
			System.out.println("private key = " + prikey);
			
			
			RSAPrivateKey priKeyRsa = (RSAPrivateKey)prikey;
			RSAPublicKey pubKeyRsa = (RSAPublicKey)pubkey;
			byte[] privateKey = new byte[256];
			byte[] publicKey = new byte[128 + 4];
			
			byte[] priN = priKeyRsa.getModulus().toByteArray();
			byte[] priD = priKeyRsa.getPrivateExponent().toByteArray();
			
			Util.printHexbyte("priN", priN);
			Util.printHexbyte("priD", priD);
			
			System.arraycopy(priN, 1, privateKey, 0, priN.length - 1);
			System.arraycopy(priD, 0, privateKey, priN.length - 1, priD.length);
			
			int index = ClientForCaApplet.importRsa1024PrivateKey(privateKey);
			
			byte[] pubN = pubKeyRsa.getModulus().toByteArray();
			byte[] pubE = pubKeyRsa.getPublicExponent().toByteArray();
			byte[] pubEE = new byte[4];
			if(pubE != null && pubE.length == 3){
				System.arraycopy(pubE, 0, pubEE, 1, 3);
			}else{
				pubEE = pubE;
			}
			Util.printHexbyte("pubN", pubN);
			Util.printHexbyte("pubE", pubE);
			Util.printHexbyte("pubEE", pubEE);
			System.arraycopy(pubN, 1, publicKey, 0, pubN.length - 1);
			System.arraycopy(pubEE, 0, publicKey, pubN.length - 1, pubEE.length);
			
			ClientForCaApplet.importRsa1024PublicKey(publicKey, (byte)index);
			
			
			Log.e("cert.toString()", cert.toString());
			Util.printHexbyte("cert.getEncoded()", cert.getEncoded());
			//ClientForCaApplet.importCert(index, cert.getEncoded());
			FileInputStream fis1 = new FileInputStream(strPfx);
			
			
			byte[] byt = new byte[fis1.available()];
			fis1.read(byt);
			fis.close();
			Log.e("byt.length", byt.length+"");
			ClientForCaApplet.importCert(index, byt);
			
			byte[] certByte = ClientForCaApplet.exportCertByte(index);
			Log.e("certByte.length", certByte.length+"");
			
			/*Certificate certExport = CertificateFactory
				     .getInstance("X509")
				     .generateCertificate(new ByteArrayInputStream(certByte));
			
			Log.e("certExport.toString()", certExport.toString());
			
			Util.printHexbyte("certExport", ((RSAPublicKey)certExport.getPublicKey()).getModulus().toByteArray());
			Util.printHexbyte("certExport", ((RSAPublicKey)certExport.getPublicKey()).getPublicExponent().toByteArray());
			Util.printHexbyte("certExport.getEncoded()", certExport.getEncoded());*/
			
			getCert(certByte);
			
			return prikey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private Certificate getCert(byte[] certFile){
		
		String DEFAULT_PATH = "/nzca/test1.pfx";
		String mPath = Environment.getExternalStorageDirectory()
		.getAbsolutePath() + DEFAULT_PATH;
		
		File file = new File(mPath);
	    OutputStream output = null;
		try {
			output = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
	    try {
			bufferedOutput.write(certFile);
			bufferedOutput.flush();
			bufferedOutput.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    String strPassword = "123456";
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(mPath);
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			char[] nPassword = null;
			if ((strPassword == null) || strPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = strPassword.toCharArray();
			}
			
			ks.load(fis, nPassword);
			
			fis.close();
			System.out.println("keystore type=" + ks.getType());
			// Now we loop all the aliases, we need the alias to get keys.
			// It seems that this value is the "Friendly name" field in the
			// detals tab <-- Certificate window <-- view <-- Certificate
			// Button <-- Content tab <-- Internet Options <-- Tools menu
			// In MS IE 6.
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
				System.out.println("alias=[" + keyAlias + "]");
			}
			// Now once we know the alias, we could get the keys.
			System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();
			System.out.println("cert class = " + cert.getClass().getName());
			System.out.println("cert = " + cert);
			System.out.println("public key = " + pubkey);
			System.out.println("private key = " + prikey);
			
			return cert;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
	}

}
