package com.irainbot;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.ProgramInfo;
import com.irainbot.entity.RunPlanInfo;
import com.irainbot.entity.WifiInfo;
import com.irainbot.entity.ZoneInfo;
import com.irainbot.utils.MyLog;

/**
 * 获取模拟数据
 */
public class ModelData {

	/**
	 * 获取runplan列表 模拟
	 * 
	 * @return
	 */
	public static ArrayList<RunPlanInfo> getRunPlanInfoList() {
		ArrayList<RunPlanInfo> list = new ArrayList<RunPlanInfo>();
		for (int i = 0; i < 5; i++) {
			RunPlanInfo runPlanInfo = new RunPlanInfo();
			runPlanInfo.setDate("Sat 4/12");
			runPlanInfo.setStartTime("9:00 AM");
			runPlanInfo.setStopTime("10:00 AM");
			runPlanInfo.setStatus("finished");
			runPlanInfo.setDuration(60);
			runPlanInfo.setFinished(60);
			runPlanInfo.setProgram(40);
			runPlanInfo.setSmart(10);
			runPlanInfo.setManual(10);
			runPlanInfo.setWeatherCondition("sunny");
			runPlanInfo.setWeatherTempMax("25");
			runPlanInfo.setWeatherTempMin("20");

			list.add(runPlanInfo);
		}
		return list;
	}

	/**
	 * 获取device列表 模拟
	 * 
	 * @return
	 */
	public static ArrayList<DeviceInfo> getDeviceInfoList() {
		ArrayList<DeviceInfo> list = new ArrayList<DeviceInfo>();
		for (int i = 0; i < 5; i++) {
			DeviceInfo reviceInfo = new DeviceInfo();
			reviceInfo.setDeviceName("device" + i);
			reviceInfo.setZipcode("518017" + i);

			list.add(reviceInfo);
		}
		return list;
	}

	/**
	 * 获取wifi列表 模拟
	 * 
	 * @return
	 */
	public static ArrayList<WifiInfo> getWifiInfoList() {
		ArrayList<WifiInfo> list = new ArrayList<WifiInfo>();
		for (int i = 0; i < 5; i++) {
			WifiInfo wifiInfo = new WifiInfo();
			wifiInfo.setWifiName("wifi" + i);
			wifiInfo.setLevel(5 + i);

			list.add(wifiInfo);
		}
		return list;
	}

	/**
	 * 获取zone列表 模拟
	 * 
	 * @return
	 */
	public static ArrayList<ZoneInfo> getZoneInfoList() {
		ArrayList<ZoneInfo> list = new ArrayList<ZoneInfo>();
		for (int i = 0; i < 12; i++) {
			ZoneInfo zoneInfo = new ZoneInfo();
			zoneInfo.setZoneId((i + 1) + "");
			zoneInfo.setZoneName("zone" + (i + 1));
			zoneInfo.setZoneEnable(true);
			zoneInfo.setVegation("vegation" + i);
			zoneInfo.setSunshine("sunshine" + i);
			zoneInfo.setSlope("slope" + i);
			zoneInfo.setSoil("soil" + i);

			zoneInfo.setDuration(250 + i);
			zoneInfo.setFinished(150 + i);

			zoneInfo.setProgram(220 + i);
			zoneInfo.setSmart(20 + i);
			zoneInfo.setManual(10 + i);

			zoneInfo.setMasterZoneEnable(true);
			zoneInfo.setMasterZoneId("1");

			list.add(zoneInfo);
		}
		return list;
	}

	/**
	 * 获取program列表 模拟
	 * 
	 * @return
	 */
	public static ArrayList<ProgramInfo> getProgramInfoList() {
		ArrayList<ProgramInfo> list = new ArrayList<ProgramInfo>();
		for (int i = 0; i < 12; i++) {
			ProgramInfo programInfo = new ProgramInfo();
			programInfo.setProgramId((i + 1) + "");
			programInfo.setName("program" + i);
			programInfo.setWeatherSmart(true);
			programInfo.setActiveMonths("4,5,6");
			programInfo.setEveryNDays("Every Day");
			programInfo.setStartTime("21:30 00");
			programInfo.setDuration((20 + i) + "");

			list.add(programInfo);
		}
		return list;
	}

	/**
	 * 增加一台设备
	 * 
	 * @param callback
	 */
	public static void addDebugDevice(AjaxCallBack<String> callback) {
		String url = AppData.BASE_URL + "debug/addDevice";
		FinalHttp finalHttp = new FinalHttp();
		AjaxParams ap = new AjaxParams();
		finalHttp.post(url, ap, callback);
		MyLog.i("addDebugDevice : " + url + "?" + ap.getParamString());
	}

	/**
	 * 链接用户到一台设备
	 * 
	 * @param callback
	 */
	public static void linkDeviceAndUser(String userid, String deviceid,
			AjaxCallBack<String> callback) {
		String url = AppData.BASE_URL + "debug/linkUserDevice";
		FinalHttp finalHttp = new FinalHttp();
		AjaxParams ap = new AjaxParams();
		ap.put("userid", userid);
		ap.put("deviceid", deviceid);
		finalHttp.post(url + "?" + ap.getParamString(), null, callback);
		MyLog.i("linkDeviceAndUser : " + url + "?" + ap.getParamString());
	}
	
}
