package com.irainbot.ui.program;

import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.http.AjaxCallBack;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.user.CheckInput;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.ProgramInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.widget.datepick.OnDatePickListener;
import com.irainbot.utils.ToolUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 程序修改
 */
public class ProgramEditActivity extends BaseActivity implements
		OnClickListener {
	private EditText et_program_name; // 程序名称
	private TextView tv_program_months; // 执行月份
	private RelativeLayout rl_program_months; // 执行月份 点击
	private TextView tv_program_frequency; // 频率
	private TextView tv_program_starttime; // 开始时间
	private RelativeLayout rl_program_starttime; // 开始时间 点击
	private TextView tv_watering_zones; // 区域

	private TextView tv_switch; // 开关
	private boolean switchstats = true;
	private TextView tv_status; // 开关状态显示

	private ImageView iv_save; // 保存按钮

	private String[] months = { "Jan.", "Feb.", "Mar.", "Apr.", "May.", "Jun.",
			"Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec." };
	private String[] frequency = { "Every Day", "Every Two Day",
			"Every Three Day" };

	private ProgramInfo programInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program_edit);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		et_program_name = (EditText) findViewById(R.id.et_program_name);
		tv_program_months = (TextView) findViewById(R.id.tv_program_months);
		rl_program_months = (RelativeLayout) findViewById(R.id.rl_program_months);
		tv_program_frequency = (TextView) findViewById(R.id.tv_program_frequency);
		tv_program_starttime = (TextView) findViewById(R.id.tv_program_starttime);
		rl_program_starttime = (RelativeLayout) findViewById(R.id.rl_program_starttime);
		tv_watering_zones = (TextView) findViewById(R.id.tv_watering_zones);

		tv_switch = (TextView) findViewById(R.id.tv_switch);
		tv_status = (TextView) findViewById(R.id.tv_status);

		rl_program_months.setOnClickListener(this);
		tv_program_frequency.setOnClickListener(this);
		tv_watering_zones.setOnClickListener(this);
		tv_switch.setOnClickListener(this);

		iv_save = (ImageView) findViewById(R.id.iv_setting);
		iv_save.setVisibility(View.VISIBLE);
		iv_save.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		setTitle(getString(R.string.program_title));
		try {
			Bundle bundle = getIntent().getExtras();
			programInfo = (ProgramInfo) bundle.getSerializable("programInfo");
			String programName = programInfo.getName();
			if (!ToolUtils.isEmpty(programName)) {
				setTitle(programName);
			}
		} catch (Exception e) {
		}

		rl_program_starttime.setOnClickListener(new OnDatePickListener(this,
				OnDatePickListener.DATEFORMAT_HHMM, tv_program_starttime));
	}

	/**
	 * 程序修改
	 */
	private void doConfigure() {
		String programName = et_program_name.getText().toString();
		String months = tv_program_months.getText().toString();
		String frequency = tv_program_frequency.getText().toString();
		String starttime = tv_program_starttime.getText().toString();

		int resid = -1;

		if (programInfo == null) {
			programInfo = new ProgramInfo();
			resid = CheckInput.checkEditProgram(programName, months, frequency,
					starttime);
			if (resid > -1) {
				showToast("", resid);
				return;
			}
		}

		if (!ToolUtils.isEmpty(programName))
			programInfo.setName(programName);
		if (!ToolUtils.isEmpty(months))
			programInfo.setActiveMonths(months);
		if (!ToolUtils.isEmpty(frequency))
			programInfo.setEveryNDays(frequency);
		if (!ToolUtils.isEmpty(starttime))
			programInfo.setStartTime(starttime);
		programInfo.setWeatherSmart(switchstats);

		if (resid <= -1) {
			DeviceInfo deviceInfo = DeviceManagers.getInstance()
					.getDeviceInfo();
			if (deviceInfo != null) {
				String deviceId = deviceInfo.getDeviceid();
				UserInfo userInfo = UserManagers.getInstance().getUserInfo();
				if (userInfo != null) {
					String userId = userInfo.getUserId();
					showDialog("", getString(R.string.app_submit_doing), true);

					String programId = programInfo.getProgramId();
					if (!ToolUtils.isEmpty(programId)) {
						DataInterface.updateProgram(userId, deviceId,
								programInfo, callback);
					} else {
						DataInterface.addProgram(userId, deviceId, programInfo,
								callback);
					}
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

			showToast("", R.string.program_configure_success);
			finish();
		}

	};

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
	 * 弹出选择提示框
	 */
	public void showAlertDialog(final TextView itemView, final String[] items,
			final boolean isMultiChoice) {
		final Map<Integer, Boolean> itemsSelect = new HashMap<Integer, Boolean>();
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.program_edit_months_select);
		if (isMultiChoice) {
			builder.setMultiChoiceItems(items, null,
					new OnMultiChoiceClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							itemsSelect.put(which, isChecked);
						}
					});
		} else {
			builder.setSingleChoiceItems(items, -1,
					new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							itemsSelect.clear();
							itemsSelect.put(which, true);
						}
					});
		}
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
						// 不遍历map原因是排列顺序
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < items.length; i++) {
							Boolean isChecked = itemsSelect.get(i);
							if (isChecked != null && isChecked) {
								if (sb.length() > 0)
									sb.append(",");
								sb.append(items[i]);
							}
						}
						itemView.setText(sb.toString());
					}
				});
		builder.create().show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_switch:
			switchstats = !switchstats;
			changeStats();
			break;
		case R.id.rl_program_months:
			showAlertDialog(tv_program_months, months, true);
			break;
		case R.id.tv_program_frequency:
			showAlertDialog(tv_program_frequency, frequency, false);
			break;
		case R.id.tv_watering_zones:
			Intent intent = new Intent(ProgramEditActivity.this,
					ProgramZonesActivity.class);
			if (programInfo != null) {
				String duration = programInfo.getDuration();
				if (!ToolUtils.isEmpty(duration)) {
					intent.putExtra("duration", duration);
				}
			}
			startActivityForResult(intent, 0);
			break;
		case R.id.iv_setting:
			doConfigure();
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				try {
					String duration = intent.getStringExtra("duration");
					if (programInfo != null) {
						programInfo.setDuration(duration);
					}
				} catch (Exception e) {
				}
			}
		}
	}

}
