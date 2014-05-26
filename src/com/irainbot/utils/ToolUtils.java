package com.irainbot.utils;

import com.irainbot.IrainbotApplication;

import android.content.Intent;
import android.net.Uri;

/**
 * 工具类
 */
public class ToolUtils {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param src
	 *            字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String src) {
		boolean result = true;
		if (src != null && !"".equals(src))
			result = false;
		return result;
	}

	/**
	 * 发送邮件
	 * 
	 * @param email
	 *            邮件
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static void sendEmail(String email, String title, String content) {
		Intent data = new Intent(Intent.ACTION_SENDTO);
		data.setData(Uri.parse("mailto:" + email));
		data.putExtra(Intent.EXTRA_SUBJECT, title);
		data.putExtra(Intent.EXTRA_TEXT, content);
		IrainbotApplication.getInstance().startActivity(data);

	}

}
