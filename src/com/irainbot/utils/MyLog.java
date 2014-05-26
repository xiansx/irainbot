package com.irainbot.utils;

import com.irainbot.AppData;

import android.util.Log;

/**
 * 对Log进行简单的封装.不需要Log时,把boolean对象赋值为false即可
 */
public final class MyLog {
	public static final String TAG = "irainbot";

	public static void d(String msg) {
		if (AppData.openLog) {
			Log.d(TAG, msg);
		}
	}

	public static void v(String msg) {
		if (AppData.openLog) {
			Log.v(TAG, msg);
		}
	}

	public static void e(String msg) {
		if (AppData.openLog) {
			Log.e(TAG, msg);
		}
	}

	public static void i(String msg) {
		if (AppData.openLog) {
			Log.i(TAG, msg);
		}
	}

	public static void w(String msg) {
		if (AppData.openLog) {
			Log.w(TAG, msg);
		}
	}
}