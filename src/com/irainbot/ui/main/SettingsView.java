package com.irainbot.ui.main;

import net.tsz.afinal.http.AjaxCallBack;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.device.ConfigureDeviceActivity;
import com.irainbot.ui.device.ShareDeviceActivity;
import com.irainbot.ui.program.ProgramsActivity;
import com.irainbot.ui.zone.ConfigureZonesActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 * settings 配置
 */
public class SettingsView {
	private MainActivity activity;
	private View view;
	private RelativeLayout rl_configure_device, rl_configure_zones,
			rl_share_device, rl_delete_device, rl_program_info;

	public SettingsView(MainActivity activity) {
		this.activity = activity;
		LayoutInflater inflater = LayoutInflater.from(activity);
		view = inflater.inflate(R.layout.nav_settings, null);
		initView();
	}

	public View getView() {
		return view;
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		rl_configure_device = (RelativeLayout) view
				.findViewById(R.id.rl_configure_device);
		rl_configure_zones = (RelativeLayout) view
				.findViewById(R.id.rl_configure_zones);
		rl_share_device = (RelativeLayout) view
				.findViewById(R.id.rl_share_device);
		rl_delete_device = (RelativeLayout) view
				.findViewById(R.id.rl_delete_device);
		rl_program_info = (RelativeLayout) view
				.findViewById(R.id.rl_program_info);

		rl_configure_device.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.gotoAct(ConfigureDeviceActivity.class);
			}
		});
		rl_configure_zones.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.gotoAct(ConfigureZonesActivity.class);
			}
		});
		rl_share_device.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.gotoAct(ShareDeviceActivity.class);
			}
		});
		rl_delete_device.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showAlertDialog();
			}
		});
		rl_program_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.gotoAct(ProgramsActivity.class);
			}
		});
	}

	/**
	 * 弹出选择提示框
	 */
	public void showAlertDialog() {
		Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(R.string.device_delete_title);
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
						// 删除设备
						doDelete();
					}
				});
		builder.create().show();
	}

	/**
	 * 删除设备
	 */
	private void doDelete() {
		DeviceInfo deviceInfo = DeviceManagers.getInstance().getDeviceInfo();
		if (deviceInfo != null) {
			String deviceId = deviceInfo.getDeviceid();
			UserInfo userInfo = UserManagers.getInstance().getUserInfo();
			if (userInfo != null) {
				String userId = userInfo.getUserId();
				activity.showDialog("",
						activity.getString(R.string.app_submit_doing), true);
				DataInterface.deleteDevice(userId, deviceId, callback);
			}
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
			activity.showToast("", R.string.app_network_error);
			activity.hideDialog();

			// 200: deleted
			// ● 400: unable to delete an active device that no other owner
			// ● 403: unauthorized, can’t access the deviceid
		}

		@Override
		public void onSuccess(String t) {
			// TODO Auto-generated method stub
			super.onSuccess(t);
			activity.hideDialog();

			activity.showToast("", R.string.device_delete_success);
		}

	};
}
