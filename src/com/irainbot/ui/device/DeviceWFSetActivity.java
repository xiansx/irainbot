package com.irainbot.ui.device;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;

import com.irainbot.R;
import com.irainbot.action.iface.ControllerInitialSetup;
import com.irainbot.action.parse.DataParse;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.UserInfo;
import com.irainbot.entity.WifiInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.adapter.WifiListAdapter;
import com.irainbot.ui.main.MainActivity;
import com.irainbot.utils.ToolUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 设备wifi
 */
public class DeviceWFSetActivity extends BaseActivity {
	private ListView lv_wifi_list; // wifi列表
	private ArrayList<WifiInfo> wifiInfolist; // wifi 数据
	private WifiListAdapter wifiListAdapter; // 数据适配
	private TextView tv_empty; // 空数据
	private TextView tv_wifi_lable; // 标签
	private TextView tv_wifi_list_title; // 列表标题

	private String ip; // 热点ip
	private String ssid; // 热点ssid

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_add);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.device_wifi_choose));

		lv_wifi_list = (ListView) findViewById(R.id.lv_device_list);
		tv_empty = (TextView) findViewById(R.id.tv_empty);
		tv_wifi_lable = (TextView) findViewById(R.id.tv_device_lable);
		tv_wifi_list_title = (TextView) findViewById(R.id.tv_device_list_title);

		tv_wifi_lable.setText(R.string.device_choose_wifi_lable);
		tv_wifi_list_title.setText(R.string.device_wifi_available);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		try {
			ip = getIntent().getStringExtra("ip");
			ssid = getIntent().getStringExtra("ssid");
		} catch (Exception e) {
		}

		// listView
		wifiInfolist = new ArrayList<WifiInfo>();
		wifiListAdapter = new WifiListAdapter(this, wifiInfolist);
		lv_wifi_list.setAdapter(wifiListAdapter);
		lv_wifi_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (wifiInfolist != null && wifiInfolist.size() > 0) {
					WifiInfo wifiInfo = wifiInfolist.get(position);
					if (wifiInfo != null) {
						showAlertDialog(wifiInfo);
					}
				}
			}
		});

		lv_wifi_list.setEmptyView(tv_empty);

		// 获取数据
		getData();
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		if (!ToolUtils.isEmpty(ip)) {
			showDialog("", getString(R.string.app_loading), true);
			ControllerInitialSetup.getWfList(ip, wifi_callback);
		}
		// 模拟数据
		// ModelData modelData = new ModelData();
		// updateUI(modelData.getWifiInfoList());
	}

	private Handler handler = new Handler();

	/**
	 * 发送设置到设备
	 */
	private void doSetupWifi(String ssid, String password, String deviceName,
			String zipcode) {
		showDialog(getString(R.string.device_wifi_setuping_title),
				getString(R.string.device_wifi_setuping_msg), true);

		UserInfo userinfo = UserManagers.getInstance().getUserInfo();
		if (userinfo != null) {
			String userId = userinfo.getUserId();
			if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(ip)) {
				showDialog("", getString(R.string.app_loading), true);
				// 设置wifi
				ControllerInitialSetup.setupWiFi(ip, ssid, password,
						deviceName, zipcode, userId, setup_callback);
			}
		}

		// 模拟状态，正式数据删掉
		// handler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// hideDialog();
		// showToast("", R.string.device_wifi_setup_success);
		// Intent intent = new Intent(DeviceWFSetActivity.this,
		// MainActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(intent);
		// }
		// }, 3000);
	}

	/**
	 * 核对联网状态
	 */
	private void checkWifiStatus() {
		if (!ToolUtils.isEmpty(ip)) {
			ControllerInitialSetup.getWfSetupStatus(ip, wifistatus_callback);
		}
	}

	/**
	 * 回调 wifistatus检测
	 */
	private AjaxCallBack<String> wifistatus_callback = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			showToast("", R.string.app_network_error);
			hideDialog();
		}

		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			hideDialog();
			showToast("", R.string.device_wifi_setup_success);
			Intent intent = new Intent(DeviceWFSetActivity.this,
					MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

	};

	/**
	 * 回调 wifi设置
	 */
	private AjaxCallBack<String> setup_callback = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			showToast("", R.string.app_network_error);
			hideDialog();
		}

		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			checkWifiStatus();
		}

	};

	/**
	 * 回调 wifi列表
	 */
	private AjaxCallBack<String> wifi_callback = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			showToast("", R.string.app_network_error);
			hideDialog();
		}

		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			hideDialog();
			DataParse dataParse = new DataParse();
			ArrayList<WifiInfo> listtemp = dataParse.parseWifiInfoList(t);
			updateUI(listtemp);
		}

	};

	/**
	 * 更新ui
	 * 
	 * @param list
	 *            wifiInfo列表
	 */
	private void updateUI(ArrayList<WifiInfo> list) {
		if (list != null && list.size() > 0) {
			wifiInfolist.clear();
			wifiInfolist.addAll(list);
			wifiListAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 弹出选择提示框
	 */
	public void showAlertDialog(WifiInfo wifiInfo) {
		View wifiSetView; // 弹出框中的输入框
		final EditText et_wifi_pwd; // wifi 密码
		final EditText et_device_name; // 设备名称
		final EditText et_device_zipcode; // 邮编

		LayoutInflater inflater = LayoutInflater.from(this);
		wifiSetView = inflater.inflate(R.layout.device_wifi_set, null);
		et_wifi_pwd = (EditText) wifiSetView.findViewById(R.id.et_wifi_pwd);
		et_device_name = (EditText) wifiSetView
				.findViewById(R.id.et_device_name);
		et_device_zipcode = (EditText) wifiSetView
				.findViewById(R.id.et_device_zipcode);

		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.device_wifi_set);
		builder.setView(wifiSetView);
		builder.setNegativeButton(R.string.app_cancel,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				});
		builder.setPositiveButton(R.string.device_sendtodevice,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// 校对pincode处理
						String wifiPwd = et_wifi_pwd.getText().toString();
						String deviceName = et_device_name.getText().toString();
						String zipCode = et_device_zipcode.getText().toString();

						doSetupWifi(ssid, wifiPwd, deviceName, zipCode);
					}
				});
		builder.create().show();
	}

}
