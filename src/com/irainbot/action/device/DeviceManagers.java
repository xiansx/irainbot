package com.irainbot.action.device;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import com.irainbot.AppData;
import com.irainbot.IrainbotApplication;
import com.irainbot.R;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.utils.MyLog;
import com.irainbot.utils.ToolUtils;

/**
 * 设备管理
 */
public class DeviceManagers {
	private Context context;
	private static DeviceManagers instance;

	public DeviceManagers() {
		this.context = IrainbotApplication.getInstance();
	}

	/**
	 * 设备单例模式
	 * 
	 * @return
	 */
	public static DeviceManagers getInstance() {
		if (instance == null) {
			instance = new DeviceManagers();
		}
		return instance;
	}

	public DeviceInfo getDeviceInfo() {
		DeviceInfo deviceInfo = AppData.currentDeviceInfo;
		// 用户email如果为空，表明登录对象已经被回收了
		if (deviceInfo == null || ToolUtils.isEmpty(deviceInfo.getDeviceid())) {
			AppData.currentDeviceInfo = readUserFromSerializable();
		}

		return AppData.currentDeviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		// 本地临时存储用户信息
		saveUserToSerializable(deviceInfo);
		AppData.currentDeviceInfo = deviceInfo;
	}

	/**
	 * 序列化当前设备到本地
	 * 
	 * @param deviceInfo
	 */
	public void saveUserToSerializable(DeviceInfo deviceInfo) {
		MyLog.i("----------序列化当前设备到本地---------");
		SharedPreferences share = context.getSharedPreferences(
				context.getString(R.string.app_name), Context.MODE_PRIVATE);
		if (deviceInfo != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(baos);
				// 将Product对象放到OutputStream中
				oos.writeObject(deviceInfo);
				// 将Product对象转换成byte数组，并将其进行base64编码
				String userInfoBase64 = new String(Base64.encode(
						baos.toByteArray(), Base64.DEFAULT));
				Editor editor = share.edit();
				// 将编码后的字符串写到base64.xml文件中
				editor.putString("deviceInfo", userInfoBase64);
				editor.commit();
			} catch (Exception e) {
				MyLog.i("当前设备本地序列化不成功");
			}
		} else {
			// 清除本地信息
			share.edit().clear().commit();
		}

	}

	/**
	 * 读取本地序列化当前设备
	 * 
	 * @return
	 */
	public DeviceInfo readUserFromSerializable() {
		MyLog.i("----------读取本地序列化当前设备---------");
		SharedPreferences share = context.getSharedPreferences(
				context.getString(R.string.app_name), Context.MODE_PRIVATE);
		String userInfoBase64 = share.getString("deviceInfo", "");
		// 对Base64格式的字符串进行解码
		byte[] base64Bytes = Base64.decode(userInfoBase64.getBytes(),
				Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(bais);
			// 从ObjectInputStream中读取Product对象
			DeviceInfo deviceInfo = (DeviceInfo) ois.readObject();
			share = null;
			return deviceInfo;
		} catch (Exception e) {
		}
		return null;
	}
}
