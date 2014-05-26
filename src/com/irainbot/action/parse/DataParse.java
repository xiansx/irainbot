package com.irainbot.action.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.ProgramInfo;
import com.irainbot.entity.RunPlanInfo;
import com.irainbot.entity.WifiInfo;
import com.irainbot.entity.ZoneInfo;
import com.irainbot.utils.MyLog;
import com.irainbot.utils.ToolUtils;

/**
 * 数据解析
 */
public class DataParse {

	/**
	 * 设备列表
	 * 
	 * @param result
	 * @return
	 */
	public ArrayList<DeviceInfo> parseDeviceInfoList(String result) {
		ArrayList<DeviceInfo> list = null;
		try {
			MyLog.i("设备列表数据返回：" + result);
			JSONArray jsonArray = new JSONArray(result);
			if (jsonArray != null && jsonArray.length() > 0) {
				list = new ArrayList<DeviceInfo>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					String deviceid = obj.optString("id");
					String model = obj.optString("model");
					String softwareVersion = obj.optString("hwRevision");
					int numZones = obj.optInt("numZones");
					boolean status = obj.optBoolean("online");
					String lastSeenTime = obj.optString("linkTime");
					String timezone = obj.optString("timezone");
					String deviceName = obj.optString("name");
					String customName = obj.optString("customName");
					String zipcode = obj.optString("zipcode");
					boolean isOwner = obj.optBoolean("isOwner");

					DeviceInfo deviceInfo = new DeviceInfo(deviceid, model,
							softwareVersion, numZones, status, lastSeenTime,
							timezone, deviceName, zipcode, isOwner, customName);
					if (deviceInfo != null)
						list.add(deviceInfo);
				}
			}
		} catch (Exception e) {
			list = null;
			MyLog.i("获取不到设备列表信息");
		}
		return list;
	}

	/**
	 * 
	 * 区域列表
	 * 
	 * @param result
	 * @param durationArray
	 *            总时间
	 * @param finishedArray
	 *            已经使用时间
	 * @param programArray
	 *            zone的初始时间 即program（program，smart，manual）
	 * @param smartArray
	 * @param manualArray
	 * @return
	 */
	public ArrayList<ZoneInfo> parseZoneInfoList(String result,
			String durationArray, String finishedArray, String programArray,
			String smartArray, String manualArray) {
		ArrayList<ZoneInfo> list = null;
		try {
			MyLog.i("区域列表数据返回：" + result);
			JSONObject jsonObj = new JSONObject(result);
			JSONObject zoneObj = jsonObj.optJSONObject("zones");
			JSONObject o = jsonObj.optJSONObject("masterValve");
			String masterZoneId = o.optString("valveId");
			boolean masterZoneEnable = o.optBoolean("enabled");
			if (zoneObj != null && zoneObj.length() > 0) {
				list = new ArrayList<ZoneInfo>();
				String[] durationtime = null;
				String[] finishedtime = null;
				String[] programtime = null;
				String[] smarttime = null;
				String[] manualtime = null;
				if (!ToolUtils.isEmpty(durationArray)) {
					durationtime = durationArray.split(",");
				}
				if (!ToolUtils.isEmpty(finishedArray)) {
					finishedtime = finishedArray.split(",");
				}
				if (!ToolUtils.isEmpty(programArray)) {
					programtime = programArray.split(",");
				}
				if (!ToolUtils.isEmpty(smartArray)) {
					smarttime = smartArray.split(",");
				}
				if (!ToolUtils.isEmpty(manualArray)) {
					manualtime = manualArray.split(",");
				}
				for (int i = 0; i < zoneObj.length(); i++) {
					JSONObject obj = zoneObj.optJSONObject(i + "");
					String zoneId = i + "";
					boolean zoneEnable = obj.optBoolean("enabled");
					String zoneName = obj.optString("name");
					String vegation = obj.optString("veg");
					String sunshine = obj.optString("sun");
					String slope = obj.optString("slope");
					String soil = obj.optString("soil");

					ZoneInfo zoneInfo = new ZoneInfo(zoneId, zoneName,
							zoneEnable, vegation, sunshine, slope, soil,
							masterZoneId, masterZoneEnable);
					if (zoneInfo != null) {
						try {
							String duration = durationtime[i];
							zoneInfo.setDuration(Integer.valueOf(duration));
						} catch (Exception e) {
						}
						try {
							String finished = finishedtime[i];
							zoneInfo.setFinished(Integer.valueOf(finished));
						} catch (Exception e) {
						}
						try {
							String program = programtime[i];
							zoneInfo.setProgram(Integer.valueOf(program));
						} catch (Exception e) {
						}
						try {
							String smart = smarttime[i];
							zoneInfo.setSmart(Integer.valueOf(smart));
						} catch (Exception e) {
						}
						try {
							String manual = manualtime[i];
							zoneInfo.setManual(Integer.valueOf(manual));
						} catch (Exception e) {
						}
						list.add(zoneInfo);
					}
				}
			}
		} catch (Exception e) {
			list = null;
			MyLog.i("获取不到区域列表信息");
		}
		return list;
	}

	/**
	 * 程序列表
	 * 
	 * @param result
	 * @return
	 */
	public ArrayList<ProgramInfo> parseProgramInfoList(String result) {
		ArrayList<ProgramInfo> list = null;
		try {
			MyLog.i("程序列表数据返回：" + result);
			JSONObject jsonObj = new JSONObject(result);
			if (jsonObj != null && jsonObj.length() > 0) {
				list = new ArrayList<ProgramInfo>();
				Iterator<String> it = jsonObj.keys();
				while (it.hasNext()) { // 遍历JSONObject
					String programId = (String) it.next().toString();
					JSONObject obj = jsonObj.optJSONObject(programId);
					if (obj != null) {
						String name = obj.optString("name");
						boolean weatherSmart = obj.optBoolean("smart");
						String activeMonths = obj.optString("months");
						String everyNDays = obj.optString("every");
						String startTime = obj.optString("start");
						String duration = obj.optString("durations");

						ProgramInfo programInfo = new ProgramInfo(programId,
								name, weatherSmart, activeMonths, everyNDays,
								startTime, duration);
						if (programInfo != null)
							list.add(programInfo);
					}
				}
			}
		} catch (Exception e) {
			list = null;
			MyLog.i("获取不到程序列表信息");
		}
		return list;
	}

	/**
	 * 计划列表
	 * 
	 * @param result
	 * @return
	 */
	public ArrayList<RunPlanInfo> parseRunPlanInfoList(String result) {
		ArrayList<RunPlanInfo> list = null;
		try {
			MyLog.i("程序列表数据返回：" + result);
			JSONObject jsonObj = new JSONObject(result);
			JSONArray jsonArray = jsonObj.optJSONArray("runplans");
			if (jsonArray != null && jsonArray.length() > 0) {
				list = new ArrayList<RunPlanInfo>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.optJSONObject(i);
					RunPlanInfo runPlanInfo = parseRunplan(obj);
					if (runPlanInfo != null)
						list.add(runPlanInfo);
				}
			}
		} catch (Exception e) {
			list = null;
			MyLog.i("获取不到程序列表信息");
		}
		return list;
	}

	/**
	 * 解析 [1,2,3,4] 这样的数组
	 * 
	 * @param array
	 * @return
	 */
	private Map<String, String> formatToTime(JSONArray array) {
		Map<String, String> list = null;
		if (array != null && array.length() > 0) {
			try {
				list = new HashMap<String, String>();
				String timeArray = "";
				int time = 0;
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < array.length(); j++) {
					if (sb.length() > 0) {
						sb.append(",");
					}
					int t = array.getInt(j);
					sb.append(t);
					time += t;
				}
				if (sb.length() > 0) {
					timeArray = sb.toString();
					list.put("timeArray", timeArray);
				}
				list.put("time", String.valueOf(time));
			} catch (JSONException e) {
			}
		}
		return list;
	}

	/**
	 * 解析runplan
	 * 
	 * @param obj
	 * @return
	 */
	public RunPlanInfo parseRunplan(JSONObject obj) {
		if (obj != null) {
			JSONArray tempArray = null;
			Map<String, String> tempMap = null;
			String date = obj.optString("date");
			String startTime = obj.optString("start");
			String stopTime = obj.optString("stop");
			String status = obj.optString("status");

			tempArray = obj.optJSONArray("duration");
			String durationArray = "";
			int duration = 0;
			tempMap = formatToTime(tempArray);
			if (tempMap != null && tempMap.size() > 0) {
				try {
					durationArray = tempMap.get("timeArray");
					duration = Integer.valueOf(tempMap.get("time"));
				} catch (Exception e) {
				}
			}
			tempArray = obj.optJSONArray("finished");
			String finishedArray = "";
			int finished = 0;
			tempMap = formatToTime(tempArray);
			if (tempMap != null && tempMap.size() > 0) {
				try {
					finishedArray = tempMap.get("timeArray");
					finished = Integer.valueOf(tempMap.get("time"));
				} catch (Exception e) {
				}
			}

			tempArray = obj.optJSONArray("program");
			String programArray = "";
			int program = 0;
			tempMap = formatToTime(tempArray);
			if (tempMap != null && tempMap.size() > 0) {
				try {
					programArray = tempMap.get("timeArray");
					program = Integer.valueOf(tempMap.get("time"));
				} catch (Exception e) {
				}
			}

			tempArray = obj.optJSONArray("smart");
			String smartArray = "";
			int smart = 0;
			tempMap = formatToTime(tempArray);
			if (tempMap != null && tempMap.size() > 0) {
				try {
					smartArray = tempMap.get("timeArray");
					smart = Integer.valueOf(tempMap.get("time"));
				} catch (Exception e) {
				}
			}

			tempArray = obj.optJSONArray("manual");
			String manualArray = "";
			int manual = 0;
			tempMap = formatToTime(tempArray);
			if (tempMap != null && tempMap.size() > 0) {
				try {
					manualArray = tempMap.get("timeArray");
					manual = Integer.valueOf(tempMap.get("time"));
				} catch (Exception e) {
				}
			}

			String weatherCondition = obj.optString("condition");
			JSONObject o = obj.optJSONObject("temp");
			String weatherTempMax = "";
			String weatherTempMin = "";
			if (o != null) {
				weatherTempMax = o.optString("high");
				weatherTempMin = o.optString("low");
			}
			RunPlanInfo runPlanInfo = new RunPlanInfo(date, startTime,
					stopTime, status, duration, finished, program, smart,
					manual, weatherCondition, weatherTempMax, weatherTempMin,
					durationArray, finishedArray, programArray, smartArray,
					manualArray);
			return runPlanInfo;
		}
		return null;
	}

	/**
	 * wifi列表
	 * 
	 * @param result
	 * @return
	 */
	public ArrayList<WifiInfo> parseWifiInfoList(String result) {
		ArrayList<WifiInfo> list = null;
		try {
			MyLog.i("wifi列表数据返回：" + result);
			JSONObject jsonObj = new JSONObject(result);
			JSONArray jsonArray = jsonObj.optJSONArray("networks");
			if (jsonArray != null && jsonArray.length() > 0) {
				list = new ArrayList<WifiInfo>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					String wifiName = obj.optString("name");
					String mac = obj.optString("mac");
					String capability = obj.optString("capability");
					int frequency = obj.optInt("frequency");
					int level = obj.optInt("level");

					WifiInfo wifiInfo = new WifiInfo(wifiName, mac, capability,
							frequency, level);
					if (wifiInfo != null)
						list.add(wifiInfo);
				}
			}
		} catch (Exception e) {
			list = null;
			MyLog.i("获取不到wifi列表信息");
		}
		return list;
	}
}
