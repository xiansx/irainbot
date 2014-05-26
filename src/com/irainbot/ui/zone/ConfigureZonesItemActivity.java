package com.irainbot.ui.zone;

import net.tsz.afinal.http.AjaxCallBack;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.user.CheckInput;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.entity.ZoneInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.utils.ToolUtils;

/**
 * 区域编辑
 */
public class ConfigureZonesItemActivity extends BaseActivity implements
		OnClickListener {
	private EditText et_name;
	private TextView tv_vegetation;
	private TextView tv_sunshine;
	private TextView tv_slope;
	private TextView tv_soil;

	private TextView tv_switch; // 开关
	private TextView tv_status; // 开关状态显示
	private boolean switchstats = true;

	private ImageView iv_save; // 保存按钮

	private ZoneInfo zoneInfo;
	private int position; // 对象在list中的位置

	// 模拟数据
	private String[] vegation = { "vegation1", "vegation2", "vegation3" };
	private String[] sunshine = { "sunshine1", "sunshine2", "sunshine3" };
	private String[] slope = { "slope1", "slope2", "slope3" };
	private String[] soil = { "soil1", "soil2", "soil3" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zone_configure_value);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		et_name = (EditText) findViewById(R.id.et_name);
		tv_vegetation = (TextView) findViewById(R.id.tv_vegetation);
		tv_sunshine = (TextView) findViewById(R.id.tv_sunshine);
		tv_slope = (TextView) findViewById(R.id.tv_slope);
		tv_soil = (TextView) findViewById(R.id.tv_soil);

		tv_status = (TextView) findViewById(R.id.tv_status);
		tv_switch = (TextView) findViewById(R.id.tv_switch);

		tv_vegetation.setOnClickListener(this);
		tv_sunshine.setOnClickListener(this);
		tv_slope.setOnClickListener(this);
		tv_soil.setOnClickListener(this);
		tv_switch.setOnClickListener(this);

		iv_save = (ImageView) findViewById(R.id.iv_setting);
		iv_save.setVisibility(View.VISIBLE);
		iv_save.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		try {
			Intent intent = getIntent();
			zoneInfo = (ZoneInfo) intent.getSerializableExtra("zoneInfo");
			position = intent.getIntExtra("position", -1);
			String zoneName = zoneInfo.getZoneName();
			String vegetation = zoneInfo.getVegation();
			String sunshine = zoneInfo.getSunshine();
			String slope = zoneInfo.getSlope();
			String soil = zoneInfo.getSoil();
			boolean zoneEnable = zoneInfo.isZoneEnable();
			if (!ToolUtils.isEmpty(zoneName)) {
				setTitle(zoneName);
				et_name.setText(zoneName);
			}

			tv_vegetation.setText(vegetation);
			tv_sunshine.setText(sunshine);
			tv_slope.setText(slope);
			tv_soil.setText(soil);
			switchstats = zoneEnable;
			changeStats();
		} catch (Exception e) {
		}
	}

	/**
	 * 开关
	 */
	private void changeStats() {
		if (switchstats) {
			tv_switch.setBackgroundResource(R.drawable.switch_on);
			tv_status.setText("Enabled");
		} else {
			tv_switch.setBackgroundResource(R.drawable.switch_off);
			tv_status.setText("Disabled");
		}
	}

	/**
	 * 设备修改
	 */
	private void doConfigure() {
		if (zoneInfo == null)
			return;
		String zoneName = et_name.getText().toString();
		String vegetation = tv_vegetation.getText().toString();
		String sunshine = tv_sunshine.getText().toString();
		String slope = tv_slope.getText().toString();
		String soil = tv_soil.getText().toString();

		int resid = CheckInput.checkEditZone(zoneName, vegetation, sunshine,
				slope, soil);
		if (resid > -1) {
			showToast("", resid);
			return;
		}

		if (!ToolUtils.isEmpty(zoneName))
			zoneInfo.setZoneName(zoneName);
		if (!ToolUtils.isEmpty(vegetation))
			zoneInfo.setVegation(vegetation);
		if (!ToolUtils.isEmpty(sunshine))
			zoneInfo.setSunshine(sunshine);
		if (!ToolUtils.isEmpty(slope))
			zoneInfo.setSlope(slope);
		if (!ToolUtils.isEmpty(soil))
			zoneInfo.setSoil(soil);
		zoneInfo.setZoneEnable(switchstats);

		if (resid <= -1) {
			DeviceInfo deviceInfo = DeviceManagers.getInstance()
					.getDeviceInfo();
			if (deviceInfo != null) {
				String deviceId = deviceInfo.getDeviceid();
				UserInfo userInfo = UserManagers.getInstance().getUserInfo();
				if (userInfo != null) {
					String userId = userInfo.getUserId();
					showDialog("", getString(R.string.app_submit_doing), true);
					DataInterface.updateZone(userId, deviceId, zoneInfo,
							callback);
				}
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
			showToast("", R.string.app_network_error);
			hideDialog();
		}

		@Override
		public void onSuccess(String t) {
			// TODO Auto-generated method stub
			super.onSuccess(t);
			hideDialog();
			showToast("", R.string.zone_configure_success);
			if (zoneInfo != null) {
				Intent intent = new Intent();
				intent.putExtra("zoneInfo", zoneInfo);
				intent.putExtra("position", position);
				setResult(RESULT_OK, intent);
			}
			finish();
		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_switch:
			switchstats = !switchstats;
			changeStats();
			break;
		case R.id.tv_vegetation:
			showAlertDialog(tv_vegetation, vegation);
			break;
		case R.id.tv_sunshine:
			showAlertDialog(tv_sunshine, sunshine);
			break;
		case R.id.tv_slope:
			showAlertDialog(tv_slope, slope);
			break;
		case R.id.tv_soil:
			showAlertDialog(tv_soil, soil);
			break;
		case R.id.iv_setting:
			doConfigure();
			break;
		}

	}

	/**
	 * 弹出选择提示框
	 */
	public void showAlertDialog(final TextView itemView, final String[] items) {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.zone_value_alias_select);
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
