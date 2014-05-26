package com.irainbot.ui.device;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.user.CheckInput;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.main.MainActivity;
import com.irainbot.utils.ToolUtils;

/**
 * 分享设备
 */
public class ShareDeviceActivity extends BaseActivity implements
		OnClickListener {
	private TextView tv_device_name; // 设备名称
	private EditText et_email; // 邮箱
	private TextView tv_device_share; // 分享
	private TextView tv_user_role;

	private String[] shareRoles = { "owner", "manager" };
	private DeviceInfo deviceInfo; // 当前设备

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_share);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.settings_sharedevice));

		tv_device_name = (TextView) findViewById(R.id.tv_device_name);
		et_email = (EditText) findViewById(R.id.et_email);
		tv_device_share = (TextView) findViewById(R.id.tv_device_share);
		tv_user_role = (TextView) findViewById(R.id.tv_user_role);

		tv_device_share.setOnClickListener(this);
		tv_user_role.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		deviceInfo = DeviceManagers.getInstance().getDeviceInfo();
		if (deviceInfo != null) {
			String deviceName = deviceInfo.getDeviceName();
			if (!ToolUtils.isEmpty(deviceName)) {
				tv_device_name.setText(deviceName);
			}
		}

	}

	/**
	 * 分享设备
	 */
	private void doShare() {
		String email = et_email.getText().toString();
		String role = tv_user_role.getText().toString();
		int resid = CheckInput.checkEmail(email);

		if (resid <= -1) {
			if (deviceInfo != null) {
				String deviceId = deviceInfo.getDeviceid();
				UserInfo userInfo = UserManagers.getInstance().getUserInfo();
				if (userInfo != null) {
					String userId = userInfo.getUserId();
					showDialog("", getString(R.string.app_submit_doing), true);
					DataInterface.shareDevice(userId, email, deviceId, role,
							callback);
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

			// showToast("", R.string.device_share_failure);
			showToast("", R.string.device_share_success);
			finish();
		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_device_share: // 分享设备
			doShare();
			break;
		case R.id.tv_user_role: // 分享到 owner or manager
			showAlertDialog(tv_user_role, shareRoles);
			break;
		}
	}

	/**
	 * 弹出选择提示框
	 */
	public void showAlertDialog(final TextView itemView, final String[] items) {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.device_share_role_lable);
		builder.setSingleChoiceItems(items, -1,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						itemView.setText(items[which]);
					}
				});
		builder.setNegativeButton(R.string.app_cancel,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				});
		builder.setPositiveButton(R.string.app_ok,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
		builder.create().show();
	}

}
