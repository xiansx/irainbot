package com.irainbot;

import android.app.Application;

/**
 * 应用
 */
public class IrainbotApplication extends Application {
	private static IrainbotApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static IrainbotApplication getInstance() {
		if (instance == null) {
			instance = new IrainbotApplication();
		}
		return instance;
	}

}
