package com.irainbot.action.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import com.irainbot.AppData;
import com.irainbot.IrainbotApplication;
import com.irainbot.R;
import com.irainbot.entity.UserInfo;
import com.irainbot.utils.MyLog;
import com.irainbot.utils.ToolUtils;

/**
 * 用户管理
 */
public class UserManagers {
	private Context context;

	// 登录用户
	public static UserInfo loginUserInfo = null;
	private static UserManagers instance;
	public static BasicCookieStore cookieStore;

	private UserManagers() {
		this.context = IrainbotApplication.getInstance();
		cookieStore = new BasicCookieStore();
	}

	/**
	 * 用户单例模式
	 * 
	 * @return
	 */
	public static UserManagers getInstance() {
		if (instance == null) {
			instance = new UserManagers();
		}
		return instance;
	}

	/**
	 * 登录保存用户
	 */
	public void login(UserInfo userinfo) {
		setUserInfo(userinfo);
	}

	/**
	 * 退出账号
	 */
	public void logout() {
		setUserInfo(null);
		if (cookieStore != null)
			cookieStore = null;
	}

	/**
	 * 是否已经登录
	 * 
	 * @return
	 */
	public boolean isLogin() {
		if (getUserInfo() != null) {
			return true;
		} else {
			return false;
		}
	}

	public UserInfo getUserInfo() {
		UserInfo user = AppData.loginUserInfo;
		// 用户email如果为空，表明登录对象已经被回收了
		if (user == null || ToolUtils.isEmpty(user.getEmail())) {
			AppData.loginUserInfo = readUserFromSerializable();
		}

		return AppData.loginUserInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		// 本地临时存储用户信息
		saveUserToSerializable(userInfo);
		AppData.loginUserInfo = userInfo;
	}

	/**
	 * 序列化登录用户到本地
	 * 
	 * @param userInfo
	 */
	public void saveUserToSerializable(UserInfo userInfo) {
		MyLog.i("----------序列化登录用户到本地---------");
		SharedPreferences share = context.getSharedPreferences(
				context.getString(R.string.app_name), Context.MODE_PRIVATE);
		if (userInfo != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(baos);
				// 将Product对象放到OutputStream中
				oos.writeObject(userInfo);
				// 将Product对象转换成byte数组，并将其进行base64编码
				String userInfoBase64 = new String(Base64.encode(
						baos.toByteArray(), Base64.DEFAULT));
				Editor editor = share.edit();
				// 将编码后的字符串写到base64.xml文件中
				editor.putString("userInfo", userInfoBase64);
				editor.commit();
			} catch (Exception e) {
				MyLog.i("用户本地序列化不成功");
			}
		} else {
			// 清除本地信息
			share.edit().clear().commit();
		}

	}

	/**
	 * 读取本地序列化登录用户
	 * 
	 * @return
	 */
	public UserInfo readUserFromSerializable() {
		MyLog.i("----------读取本地序列化登录用户---------");
		SharedPreferences share = context.getSharedPreferences(
				context.getString(R.string.app_name), Context.MODE_PRIVATE);
		String userInfoBase64 = share.getString("userInfo", "");
		// 对Base64格式的字符串进行解码
		byte[] base64Bytes = Base64.decode(userInfoBase64.getBytes(),
				Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(bais);
			// 从ObjectInputStream中读取Product对象
			UserInfo userInfo = (UserInfo) ois.readObject();
			share = null;
			return userInfo;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 打印Cookie
	 */
	private void printCookie() {
		List<Cookie> cookies = UserManagers.cookieStore.getCookies();
		for (int i = 0; i < cookies.size(); i++) {
			MyLog.i("cookie_name " + cookies.get(i).getName());
			MyLog.i("cookie_value " + cookies.get(i).getValue());
		}
	}
}
