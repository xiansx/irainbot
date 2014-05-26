package com.irainbot.action.user;

import com.irainbot.R;
import com.irainbot.utils.EmailValidator;
import com.irainbot.utils.ToolUtils;

/**
 * 输入校验
 */
public class CheckInput {
	/**
	 * 修改密码验证
	 * @param pwdold 旧密码
	 * @param pwdnew 新密码
	 * @param pwdnew2 确认密码
	 * @return
	 */
	public static int checkEditPwd(String pwdold, String pwdnew, String pwdnew2){
		int resid = -1;
		if(ToolUtils.isEmpty(pwdold)) resid = R.string.user_input_pwd_old_notempty;
		else if(ToolUtils.isEmpty(pwdnew)) resid = R.string.user_input_pwd_new_notempty;
		else if(ToolUtils.isEmpty(pwdnew2)) resid = R.string.user_input_pwd_confirm_notempty;
		else if(!pwdnew.equals(pwdnew2)) resid = R.string.user_input_pwd_confirm_wrong;
		return resid;
	}
	
	/**
	 * 重置密码验证
	 * @param email 邮箱
	 * @return
	 */
	public static int checkFindPass(String email){
		int resid = checkEmail(email);
		return resid;
	}
	
	/**
	 * 登录校验
	 * @param email 邮箱
	 * @param password 登录密码
	 * @return
	 */
	public static int checkLogin(String email, String password){
		int resid = checkEmail(email);
		if(resid == -1) resid = checkPassword(password);
		return resid;
	}
	
	/**
	 * 修改设备校验
	 * @param deviceName 设备名
	 * @param zipCode 邮编
	 * @return
	 */
	public static int checkEditDevice(String deviceName, String zipCode){
		int resid = checkDeviceName(deviceName);
		if(resid == -1) resid = checkDeviceZipcode(zipCode);
		return resid;
	}
	
	/**
	 * 区域数据校验
	 * @param zoneName 区域名称
	 * @param vegetation 
	 * @param sunshine
	 * @param slope
	 * @param soil
	 * @return
	 */
	public static int checkEditZone(String zoneName, String vegetation, 
			String sunshine, String slope, String soil){
		int resid = -1;
		if(ToolUtils.isEmpty(zoneName)) resid = R.string.zone_input_name;
		else if(ToolUtils.isEmpty(vegetation)) resid = R.string.zone_input_vegetation;
		else if(ToolUtils.isEmpty(sunshine)) resid = R.string.zone_input_sunshine;
		else if(ToolUtils.isEmpty(slope)) resid = R.string.zone_input_slope;
		else if(ToolUtils.isEmpty(soil)) resid = R.string.zone_input_soil;
		return resid;
	}
	
	/**
	 * 程序数据校验
	 * @param programName 程序名称
	 * @param months 
	 * @param frequency
	 * @param starttime
	 * @return
	 */
	public static int checkEditProgram(String programName, String months, 
			String frequency, String starttime){
		int resid = -1;
		if(ToolUtils.isEmpty(programName)) resid = R.string.program_edit_name_input;
		else if(ToolUtils.isEmpty(months)) resid = R.string.program_edit_months_input;
		else if(ToolUtils.isEmpty(frequency)) resid = R.string.program_edit_frequency_input;
		else if(ToolUtils.isEmpty(starttime)) resid = R.string.program_edit_starttime_input;
		return resid;
	}

	/**
	 * 注册校验
	 * @param email
	 * @param password
	 * @param confirmpwd
	 * @param name
	 * @return
	 */
	public static int checkReg(String email, String password, String confirmpwd, 
			String name){
		int resid = checkEmail(email);
		if(resid == -1) resid = checkName(name);
		if(resid == -1) resid = checkPassword(password);
		
		if(resid == -1) {
			if(ToolUtils.isEmpty(confirmpwd)) resid = R.string.user_input_pwd_confirm_notempty;
			else if(!password.equals(confirmpwd)) resid = R.string.user_input_pwd_confirm_wrong;
		}
		return resid;
	}
	
	/***********************核对方法*********************/
	/**
	 * 核对email
	 * @param email
	 * @return
	 */
	public static int checkEmail(String email){
		int resid = -1;
		EmailValidator emailValidator = new EmailValidator();
		if(ToolUtils.isEmpty(email) || !emailValidator.validate(email)) resid = R.string.user_input_email_collect;
		return resid;
	}
	
	/**
	 * 核对登录密码
	 * @param password
	 * @return
	 */
	private static int checkPassword(String password){
		int resid = -1;
		if(ToolUtils.isEmpty(password)) resid = R.string.user_input_pwd_notempty;
		return resid;
	}
	
	/**
	 * 核对昵称
	 * @param name
	 * @return
	 */
	public static int checkName(String name){
		int resid = -1;
		if(ToolUtils.isEmpty(name)) resid = R.string.user_input_name_notempty;
		return resid;
	}
	
	/**
	 * 核对设备名
	 * @param deviceName
	 * @return
	 */
	public static int checkDeviceName(String deviceName){
		int resid = -1;
		if(ToolUtils.isEmpty(deviceName)) resid = R.string.device_input_name_notempty;
		return resid;
	}
	
	/**
	 * 核对邮编
	 * @param deviceZipcode
	 * @return
	 */
	public static int checkDeviceZipcode(String deviceZipcode){
		int resid = -1;
		if(ToolUtils.isEmpty(deviceZipcode)) resid = R.string.device_input_zipcode_notempty;
		return resid;
	}
	
}
