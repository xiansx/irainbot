package com.irainbot.ui.device;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.http.AjaxCallBack;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.user.CheckInput;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.utils.ToolUtils;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 修改设备
 */
public class ConfigureDeviceActivity extends BaseActivity implements
		OnClickListener {
	private EditText et_device_name; // 设备名称
	private EditText et_zipcode; // 邮编
	private TextView tv_device_configure_submit; // 提交

	private DeviceInfo deviceInfo; // 当前设备

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_configure);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.settings_configuredevice));

		et_device_name = (EditText) findViewById(R.id.et_device_name);
		et_zipcode = (EditText) findViewById(R.id.et_zipcode);
		tv_device_configure_submit = (TextView) findViewById(R.id.tv_device_configure_submit);

		tv_device_configure_submit.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		deviceInfo = DeviceManagers.getInstance().getDeviceInfo();
		if (deviceInfo != null) {
			String deviceName = deviceInfo.getDeviceName();
			String zipcode = deviceInfo.getZipcode();
			if (!ToolUtils.isEmpty(deviceName)) {
				et_device_name.setText(deviceName);
			}
			if (!ToolUtils.isEmpty(zipcode)) {
				et_zipcode.setText(zipcode);
			}
		}
	}

	/**
	 * 设备修改
	 */
	private void doConfigure() {
		String deviceName = et_device_name.getText().toString();
		String zipCode = et_zipcode.getText().toString();
		int resid = CheckInput.checkEditDevice(deviceName, zipCode);

		if (resid <= -1) {
			if (deviceInfo != null) {
				String deviceId = deviceInfo.getDeviceid();
				UserInfo userInfo = UserManagers.getInstance().getUserInfo();
				if (userInfo != null) {
					String userId = userInfo.getUserId();
					showDialog("", getString(R.string.app_submit_doing), true);
					DataInterface.updateDevice(userId, deviceId, deviceName,
							zipCode, callback);
				}
			}
		} else {
			showToast("", resid);
		}
	}

	/**
	 * 回调
	 */
	private AjaxCallBack<String> callback = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			// TODO Auto-generated method stub
			super.onFailure(t, errorNo, strMsg);
			showToast("", R.string.app_network_error);
			hideDialog();
		}

		@Override
		public void onSuccess(String t) {
			// TODO Auto-generated method stub
			super.onSuccess(t);
			hideDialog();
			String timezone = "";
			try {
				JSONObject jsonObj = new JSONObject(t);
				timezone = jsonObj.optString("timezone");
			} catch (JSONException e) {
			}
			if(deviceInfo != null) {
				String deviceName = et_device_name.getText().toString();
				String zipCode = et_zipcode.getText().toString();
				deviceInfo.setDeviceName(deviceName);
				deviceInfo.setZipcode(zipCode);
				if(!ToolUtils.isEmpty(timezone)) deviceInfo.setTimezone(timezone);
				DeviceManagers.getInstance().setDeviceInfo(deviceInfo);
				showToast("", R.string.device_configure_success);
				finish();
			}
		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_device_configure_submit: // 设备修改提交
			doConfigure();
			break;
		}
	}

}
