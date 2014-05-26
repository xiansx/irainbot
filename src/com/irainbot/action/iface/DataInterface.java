package com.irainbot.action.iface;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import com.irainbot.AppData;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.ProgramInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.entity.ZoneInfo;
import com.irainbot.utils.MyLog;
import com.irainbot.utils.ToolUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 数据交互
 */
public class DataInterface {
	public static String CONTENTTYPE = "application/x-www-form-urlencoded";

	/********************************** user ***************************************/
	/**
	 * 注册用户 ok
	 * 
	 * @param userinfo
	 *            用户
	 * @return
	 */
	public static void register(UserInfo userinfo, AjaxCallBack<String> callback) {
		if (userinfo != null) {
			String url = AppData.BASE_URL + "register";
			FinalHttp finalHttp = new FinalHttp();
			String email = userinfo.getEmail();
			String password = userinfo.getPassword();
			String name = userinfo.getName();
			try {
				JSONObject param = new JSONObject();
				param.put("name", name); // 名字
				param.put("email", email); // email
				param.put("password", password); // 密码
				StringEntity se = new StringEntity(param.toString());
				finalHttp.post(url, se, CONTENTTYPE, callback);
				MyLog.i("register : " + url + " ---- " + param.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 登录 ok
	 * 
	 * @param email
	 *            邮箱
	 * @param password
	 *            登录密码
	 * @param callback
	 */
	public static void login(String email, String password,
			AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(email) && !ToolUtils.isEmpty(password)) {
			String url = AppData.BASE_URL + "login";
			FinalHttp finalHttp = new FinalHttp();
			try {
				JSONObject param = new JSONObject();
				param.put("email", email); // email
				param.put("password", password); // 密码
				StringEntity se = new StringEntity(param.toString());
				finalHttp.configCookieStore(UserManagers.cookieStore);
				finalHttp.post(url, se, CONTENTTYPE, callback);
				MyLog.i("login : " + url + " ---- " + param.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 找回密码
	 * 
	 * @param email
	 *            邮箱
	 * @param callback
	 */
	public static void findPwd(String email, AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(email)) {
			String url = AppData.BASE_URL + "";
			FinalHttp finalHttp = new FinalHttp();
			AjaxParams ap = new AjaxParams();
			ap.put("email", email); // email
			finalHttp.get(url, ap, callback);
			MyLog.i("findPwd : " + url + "?" + ap.getParamString());
		}
	}

	/**
	 * 修改密码 ok
	 * 
	 * @param userId
	 *            用户id
	 * @param oldPwd
	 *            旧密码
	 * @param newPwd
	 *            新密码
	 * @param callback
	 */
	public static void updatePwd(String userId, String oldPwd, String newPwd,
			AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(oldPwd)
				&& !ToolUtils.isEmpty(newPwd)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/pwd");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			try {
				JSONObject param = new JSONObject();
				param.put("password", oldPwd); // 密码
				param.put("newPassword", newPwd); // 新密码
				StringEntity se = new StringEntity(param.toString());
				finalHttp.configCookieStore(UserManagers.cookieStore);
				finalHttp.post(url, se, CONTENTTYPE, callback);
				MyLog.i("updatePwd : " + url + " ---- " + param.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/********************************** device ***************************************/
	/**
	 * 获取设备列表 ok
	 * 
	 * @param userId
	 *            用户id
	 * @param callback
	 */
	public static void getDeviceList(String userId,
			AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/devices");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			finalHttp.configCookieStore(UserManagers.cookieStore);
			finalHttp.get(url, null, callback);
			MyLog.i("getDeviceList : " + url);
		}
	}

	/**
	 * 分享设备
	 * 
	 * @param userId
	 *            用户id
	 * @param email
	 *            分享到邮箱
	 * @param deviceId
	 *            设备id
	 * @param role
	 *            分享角色
	 * @param callback
	 */
	public static void shareDevice(String userId, String email,
			String deviceId, String role, AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)
				&& !ToolUtils.isEmpty(email)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/device/").append(deviceId).append("/invite");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			try {
				JSONObject param = new JSONObject();
				JSONArray array = new JSONArray();
				array.put(email);
				param.put("email", array.toString()); // email
				param.put("role", role); // role
				StringEntity se = new StringEntity(param.toString());
				finalHttp.configCookieStore(UserManagers.cookieStore);
				finalHttp.post(url, se, CONTENTTYPE, callback);
				MyLog.i("shareDevice : " + url + " ---- " + param.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * 更新设备 ok
	 * 
	 * @param userId
	 *            用户id
	 * @param deviceId
	 *            设备id
	 * @param deviceName
	 *            新设备名称
	 * @param zipcode
	 *            新邮编
	 * @param callback
	 */
	public static void updateDevice(String userId, String deviceId,
			String deviceName, String zipcode, AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/device/").append(deviceId).append("/info");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			try {
				JSONObject param = new JSONObject();
				param.put("name", deviceName); // deviceName
				param.put("zipcode", zipcode); // zipcode
				StringEntity se = new StringEntity(param.toString());
				finalHttp.configCookieStore(UserManagers.cookieStore);
				finalHttp.post(url, se, CONTENTTYPE, callback);
				MyLog.i("updateDevice : " + url + " ---- " + param.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 删除设备 ok
	 * 
	 * @param userId
	 *            用户id
	 * @param deviceId
	 *            设备id
	 * @param callback
	 */
	public static void deleteDevice(String userId, String deviceId,
			AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/device/").append(deviceId);
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			finalHttp.configCookieStore(UserManagers.cookieStore);
			finalHttp.delete(url, callback);
			MyLog.i("deleteDevice : " + url);
		}
	}

	/********************************** zone ***************************************/
	/**
	 * 获取区域列表 ok
	 * 
	 * @param userId
	 *            用户id
	 * @param deviceId
	 *            设备id
	 * @param callback
	 */
	public static void getZoneList(String userId, String deviceId,
			AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/device/").append(deviceId).append("/zones");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			finalHttp.configCookieStore(UserManagers.cookieStore);
			finalHttp.get(url, null, callback);
			MyLog.i("getZoneList : " + url);
		}
	}

	/**
	 * 更新区域 ok
	 * 
	 * @param userId
	 *            用户id
	 * @param deviceId
	 *            设备id
	 * @param zoneInfo
	 *            区域信息
	 * @param callback
	 */
	public static void updateZone(String userId, String deviceId,
			ZoneInfo zoneInfo, AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)
				&& zoneInfo != null) {
			String zoneId = zoneInfo.getZoneId();
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/device/").append(deviceId).append("/zone/")
					.append(zoneId);
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			try {
				JSONObject param = new JSONObject();
				param.put("enabled", zoneInfo.isZoneEnable());
				param.put("name", zoneInfo.getZoneName());
				param.put("slope", zoneInfo.getSlope());
				param.put("soil", zoneInfo.getSoil());
				param.put("sun", zoneInfo.getSunshine());
				param.put("veg", zoneInfo.getVegation());
				StringEntity se = new StringEntity(param.toString());
				finalHttp.configCookieStore(UserManagers.cookieStore);
				finalHttp.post(url, se, CONTENTTYPE, callback);
				MyLog.i("updateZone : " + url + " ---- " + param.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * 更新主区域 ok
	 * 
	 * @param userId
	 *            用户id
	 * @param deviceId
	 *            设备id
	 * @param enabled
	 *            开关
	 * @param callback
	 */
	public static void updateMasterZone(String userId, String deviceId,
			boolean enabled, AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/device/").append(deviceId).append("/mastervalve");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			try {
				JSONObject param = new JSONObject();
				param.put("enabled", enabled);
				StringEntity se = new StringEntity(param.toString());
				finalHttp.configCookieStore(UserManagers.cookieStore);
				finalHttp.post(url, se, CONTENTTYPE, callback);
				MyLog.i("updateMasterZone : " + url + " ---- "
						+ param.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/********************************** program ***************************************/
	/**
	 * 获取程序列表
	 * 
	 * @param userId
	 *            用户id
	 * @param deviceId
	 *            设备id
	 * @param callback
	 */
	public static void getProgramList(String userId, String deviceId,
			AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/device/").append(deviceId).append("/programs");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			AjaxParams ap = new AjaxParams();
			finalHttp.configCookieStore(UserManagers.cookieStore);
			finalHttp.get(url, ap, callback);
			MyLog.i("getProgramList : " + url + "?" + ap.getParamString());
		}
	}

	/**
	 * 修改程序
	 * 
	 * @param userId
	 *            用户id
	 * @param deviceId
	 *            设备id
	 * @param programInfo
	 *            程序
	 * @param callback
	 */
	public static void updateProgram(String userId, String deviceId,
			ProgramInfo programInfo, AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)
				&& programInfo != null) {
			String programId = programInfo.getProgramId();
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/device/").append(deviceId).append("/program/")
					.append(programId);
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			JSONObject param = null;
			try {
				param = new JSONObject();
				param.put("name", programInfo.getName());
				param.put("smart", programInfo.isWeatherSmart());
				param.put("months", programInfo.getActiveMonths());
				param.put("every", programInfo.getEveryNDays());
				param.put("start", programInfo.getStartTime());
				param.put("durations", programInfo.getDuration());
				StringEntity se = new StringEntity(param.toString());
				finalHttp.configCookieStore(UserManagers.cookieStore);
				finalHttp.post(url, se, CONTENTTYPE, callback);
				MyLog.i("updateProgram : " + url + " ---- " + param.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 添加程序
	 * 
	 * @param userId
	 *            用户id
	 * @param deviceId
	 *            设备id
	 * @param programInfo
	 *            程序
	 * @param callback
	 */
	public static void addProgram(String userId, String deviceId,
			ProgramInfo programInfo, AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)
				&& programInfo != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(userId)
					.append("/device/").append(deviceId).append("/programs");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			JSONObject param = null;
			try {
				param = new JSONObject();
				param.put("name", programInfo.getName());
				param.put("smart", programInfo.isWeatherSmart());
				param.put("months", programInfo.getActiveMonths());
				param.put("every", programInfo.getEveryNDays());
				param.put("start", programInfo.getStartTime());
				param.put("durations", programInfo.getDuration());
				StringEntity se = new StringEntity(param.toString());
				finalHttp.configCookieStore(UserManagers.cookieStore);
				finalHttp.post(url, se, CONTENTTYPE, callback);
				MyLog.i("addProgram : " + url + " ---- " + param.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/********************************** runplan ***************************************/
	/**
	 * 获取计划
	 * 
	 * @param email
	 *            邮箱
	 * @param deviceId
	 *            设备id
	 * @param callback
	 */
	public static void getRunNowPlan(String email, String deviceId,
			AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(email) && !ToolUtils.isEmpty(deviceId)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(email)
					.append("/device/").append(deviceId).append("/now");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			AjaxParams ap = new AjaxParams();
			finalHttp.get(url, ap, callback);
			MyLog.i("getRunNowPlan : " + url + "?" + ap.getParamString());
		}
	}

	/**
	 * 修改计划
	 * 
	 * @param email
	 *            邮箱
	 * @param deviceId
	 *            设备id
	 * @param manual
	 *            修改值
	 * @param action
	 *            动作
	 * @param callback
	 */
	public static void updateRunPlan(String email, String deviceId,
			String manual, String action, AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(email) && !ToolUtils.isEmpty(deviceId)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(email)
					.append("/device/").append(deviceId).append("/now");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			AjaxParams ap = new AjaxParams();
			ap.put("manual", manual);
			ap.put("action", action);
			finalHttp.get(url, ap, callback);
			MyLog.i("getRunNowPlan : " + url + "?" + ap.getParamString());
		}
	}

	/********************************** stats ***************************************/
	/**
	 * 获取计划列表
	 * 
	 * @param email
	 *            邮箱
	 * @param deviceId
	 *            设备id
	 * @param from
	 *            开始键
	 * @param to
	 *            结束时间
	 * @param callback
	 */
	public static void getRunPlanList(String email, String deviceId,
			String from, String to, AjaxCallBack<String> callback) {
		if (!ToolUtils.isEmpty(email) && !ToolUtils.isEmpty(deviceId)) {
			StringBuilder sb = new StringBuilder();
			sb.append(AppData.BASE_URL).append("user/").append(email)
					.append("/device/").append(deviceId).append("/stats");
			String url = sb.toString();
			FinalHttp finalHttp = new FinalHttp();
			AjaxParams ap = new AjaxParams();
			ap.put("from", from);
			ap.put("to", to);
			finalHttp.get(url, ap, callback);
			MyLog.i("getRunPlanList : " + url + "?" + ap.getParamString());
		}
	}

}
