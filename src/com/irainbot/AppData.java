package com.irainbot;

import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.UserInfo;

/**
 * 应用内存
 */
public class AppData {
	//log开关
	public static boolean openLog = true;
	// 登录用户
	public static UserInfo loginUserInfo = null;
	// 当前设备
	public static DeviceInfo currentDeviceInfo = null;
	// 数据链接
	public static String BASE_URL = "http://qa.irainbot.com:8000/v1/";
}
