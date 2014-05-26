package com.irainbot.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.irainbot.R;
import com.irainbot.action.user.UserManagers;
import com.irainbot.ui.device.AddDeviceActivity;
import com.irainbot.ui.device.SwitchDeviceActivity;
import com.irainbot.ui.user.ChangePwdActivity;
import com.irainbot.ui.user.LoginActivity;
import com.irainbot.ui.widget.PopMenu;
import com.irainbot.utils.MyLog;
import com.irainbot.utils.ToolUtils;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 视图基类
 */
public class BaseActivity extends Activity {
	// 销毁activity的广播接收信息
	public static final String FINISHACTIVITY = "com.irainbot.finishActivity";
	private TextView tv_title; // 标题
	protected ProgressDialog dialog; // 进度框
	private ImageView iv_back; // 返回键
	private ImageView iv_Menu; // 菜单键

	private PopMenu popMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISHACTIVITY);
		// 注册广播
		registerReceiver(mFinishReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 销毁广播
		if (mFinishReceiver != null) {
			unregisterReceiver(mFinishReceiver);
		}
	}

	/**
	 * 核对登录状态
	 */
	public void checkLogin() {
		UserManagers userManagers = UserManagers.getInstance();
		// 判断是否登录，否则跳转到登录页面
		if (!userManagers.isLogin()) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (FINISHACTIVITY.equals(intent.getAction())) { // 关闭所有页面
				MyLog.i("I am " + getLocalClassName()
						+ ",now finishing myself...");
				finish();
			}
		}
	};

	/**
	 * 发送关闭广播
	 */
	protected void sendFinishBroadcast() {
		sendBroadcast(new Intent(FINISHACTIVITY));
	}

	/**
	 * 后退键
	 * 
	 * @param view
	 */
	public void backClick(View view) {
		finish();
	}

	/**
	 * 菜单键
	 * 
	 * @param view
	 */
	public void menuClick(View view) {
		if (view != null)
			popMenu.showAsDropDown(view);
	}

	/**
	 * 初始化菜单信息
	 */
	public void initMenu() {
		iv_Menu = (ImageView) findViewById(R.id.iv_Menu);
		iv_Menu.setVisibility(View.VISIBLE);
		setBackVisibility(View.GONE);
		popMenu = new PopMenu(this);
		// 菜单项点击监听器
		popMenu.setOnItemClickListener(popmenuItemClickListener);
		popMenu.addItems(getResources().getStringArray(R.array.app_menu));
	}

	/**
	 * 弹出菜单监听器
	 */
	private OnItemClickListener popmenuItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			popMenu.dismiss();
			switch (position) {
			case 0: // change pwd
				gotoAct(ChangePwdActivity.class);
				break;
			case 1: // add device
				gotoAct(AddDeviceActivity.class);
				break;
			case 2: // switch device
				gotoAct(SwitchDeviceActivity.class);
				break;
			case 3: // logout
				finish();
				UserManagers.getInstance().logout();
				break;
			}
		}
	};

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		if (tv_title == null) {
			tv_title = (TextView) findViewById(R.id.tv_title);
		}
		tv_title.setText(title);
	}

	/**
	 * 设置后退键
	 * 
	 * @param visibility
	 *            是否显示
	 */
	public void setBackVisibility(int visibility) {
		if (iv_back == null) {
			iv_back = (ImageView) findViewById(R.id.iv_back);
		}
		iv_back.setVisibility(visibility);
	}

	/**
	 * 吐司提示
	 * 
	 * @param msg
	 */
	public void showToast(String msg, int resId) {
		if (!ToolUtils.isEmpty(msg)) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		} else if (resId > -1) {
			Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 弹框提示 显示
	 * 
	 * @param msg
	 */
	public void showDialog(String title, String msg, boolean isCancel) {
		dialog = new ProgressDialog(this);
		if (!ToolUtils.isEmpty(title)) {
			dialog.setTitle(title);
		}
		dialog.setMessage(msg);
		dialog.setCancelable(isCancel);
		dialog.show();
	}

	/**
	 * 弹框提示 隐藏
	 * 
	 * @param msg
	 */
	public void hideDialog() {
		try {
			if (dialog != null) {
				dialog.dismiss();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 隐藏输入法
	 * 
	 * @param v
	 *            控件
	 */
	public void hideInputMethod(View v) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	/**
	 * 隐藏输入法 全屏
	 */
	public void hideInputMethod() {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {

		}
	}

	/**
	 * 显示输入法
	 * 
	 * @param v
	 *            控件
	 */
	public void showInputMethod(final View v) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) getApplication()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(v, 0);
			}
		}, 300);
	}

	/**
	 * 页面跳转
	 * 
	 * @param c
	 * @param z
	 */
	public void gotoAct(Class z) {
		Intent i = new Intent();
		i.setClass(this, z);
		startActivity(i);
	}

}
