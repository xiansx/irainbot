package com.irainbot.action.iface;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.irainbot.utils.MyLog;

public class ControllerInitialSetup {
	/********************************** Controller Initial Setup ***************************************/
	/**
	 * 获取wifi列表
	 * 
	 * @param ip
	 * @param callback
	 */
	public static void getWfList(String ip, AjaxCallBack<String> callback) {
		String url = "http://" + ip + "/wifiscan";
		FinalHttp finalHttp = new FinalHttp();
		AjaxParams ap = new AjaxParams();
		finalHttp.get(url, ap, callback);
		MyLog.i("getWfList : " + url + "?" + ap.getParamString());
	}

	/**
	 * 设置wifi
	 * 
	 * @param ip
	 * @param ssid
	 * @param password
	 * @param deviceName
	 * @param zipcode
	 * @param userId
	 * @param callback
	 */
	public static void setupWiFi(String ip, String ssid, String password,
			String deviceName, String zipcode, String userId,
			AjaxCallBack<String> callback) {
		String url = "http://" + ip + "/setup";
		FinalHttp finalHttp = new FinalHttp();
		try {
			JSONObject param = new JSONObject();
			param.put("ssid", ssid); // ssid
			param.put("password", password); // password
			param.put("deviceName", deviceName); // 设备名称
			param.put("zipcode", zipcode); // 邮编
			param.put("userId", userId); // 用户id
			StringEntity se = new StringEntity(param.toString());
			finalHttp.post(url, se, DataInterface.CONTENTTYPE, callback);
			MyLog.i("setupWiFi : " + url + " ---- " + param.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * 获取wifi设置状态
	 * 
	 * @param ip
	 * @param callback
	 */
	public static void getWfSetupStatus(String ip, AjaxCallBack<String> callback) {
		String url = "http://" + ip + "/setup";
		FinalHttp finalHttp = new FinalHttp();
		AjaxParams ap = new AjaxParams();
		finalHttp.get(url, ap, callback);
		MyLog.i("getWfSetupStatus : " + url + "?" + ap.getParamString());
	}
}
