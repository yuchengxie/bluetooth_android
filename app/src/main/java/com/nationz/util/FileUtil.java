/**
 * @classname FileUtil
 * @copyright Copyright (C), 2000-2014, Nationz Tech. Co., Ltd.
 * @description �ļ�������
 * @history
 * lu.yi 2014.8.27 10:24 1.0 ����
 * 
 * @author lu.yi
 * @version 1.0 2014.8.27 16:24
 */

package com.nationz.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.os.Environment;
import android.util.Log;

public final class FileUtil {

	private final static String TAG = FileUtil.class.getSimpleName();

	private final static int READ_FINISH = -1;

	public final static String DEFAULT_PATH = "/NationzBleSim/";

	private FileWriter mFw;
	private PrintWriter out;
	private FileReader mFr;

	private File mAudioFile;
	private File mSourceFile;

	private String mFilePath;

	public FileUtil(String FilePath) {
		mFilePath = FilePath;
	}

	public boolean openFile() {
		String mPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + DEFAULT_PATH;

		Log.e(TAG, "mPath:" + mPath);
		mAudioFile = new File(mPath);
		mSourceFile = new File(mPath + mFilePath);

		try {
			if (!mAudioFile.exists()) {
				mAudioFile.mkdirs();
			}

			if (!mSourceFile.exists()) {
				mSourceFile.createNewFile();
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "file open error");
		}

		return false;
	}

	public void closeFile() {
		try {
			if (mFw != null) {
				mFw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "file close error");
		}
	}

	public void writeFile(String mInfo) {
		try {
			mFw = new FileWriter(mSourceFile, true);
			out = new PrintWriter(mFw);

			out.println(mInfo);
			mFw.flush();
			out.close();
			// mFw.write(mInfo);

			mFw.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "file wrie error");
		}
	}

	public String readFile() {
		String mReadStr = null;
		int mReadCh;

		try {
			mFr = new FileReader(mSourceFile);

			while ((mReadCh = (mFr.read())) != READ_FINISH) {
				mReadStr = Integer.toString(mReadCh);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, "file found error");
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "file read error");
		}

		return mReadStr;
	}
}
