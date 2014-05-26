package com.irainbot.ui.zone;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.parse.DataParse;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.entity.ZoneInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.adapter.ZoneListAdapter;
import com.irainbot.utils.ToolUtils;

/**
 * 修改zone
 */
public class ConfigureZonesActivity extends BaseActivity implements
		OnClickListener {
	private ListView lv_zone_list; // zone列表
	private ArrayList<ZoneInfo> zoneInfolist; // zone 数据
	private ZoneListAdapter zoneListAdapter; // 数据适配
	private TextView tv_empty; // 空数据
	private TextView tv_switch; // 主控开关
	private TextView tv_status; // 开关状态显示
	private boolean switchstats = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zone_configure);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.zone_title));

		lv_zone_list = (ListView) findViewById(R.id.lv_zone_list);
		tv_empty = (TextView) findViewById(R.id.tv_empty);
		tv_status = (TextView) findViewById(R.id.tv_status);
		tv_switch = (TextView) findViewById(R.id.tv_switch);

		tv_switch.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// listView
		zoneInfolist = new ArrayList<ZoneInfo>();
		zoneListAdapter = new ZoneListAdapter(this, zoneInfolist);
		zoneListAdapter.setIsSeekBarShow(View.GONE);
		zoneListAdapter.setItemShow(View.VISIBLE);
		lv_zone_list.setAdapter(zoneListAdapter);
		lv_zone_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (zoneInfolist != null && zoneInfolist.size() > 0) {
					ZoneInfo zoneInfo = zoneInfolist.get(position);
					if (zoneInfo != null) {
						String zoneId = zoneInfo.getZoneId();
						String masterId = zoneInfo.getMasterZoneId();
						if (!ToolUtils.isEmpty(zoneId)
								&& !ToolUtils.isEmpty(masterId)
								&& masterId.equals(zoneId)) {
							return;
						}

						Intent intent = new Intent(ConfigureZonesActivity.this,
								ConfigureZonesItemActivity.class);
						intent.putExtra("zoneInfo", zoneInfo);
						intent.putExtra("position", position);
						startActivityForResult(intent, 0);
					}
				}
			}
		});

		lv_zone_list.setEmptyView(tv_empty);

		// 获取数据
		getData();
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		UserInfo userinfo = UserManagers.getInstance().getUserInfo();
		DeviceInfo deviceInfo = DeviceManagers.getInstance().getDeviceInfo();
		if (userinfo != null && deviceInfo != null) {
			String userId = userinfo.getUserId();
			String deviceId = deviceInfo.getDeviceid();
			if (!ToolUtils.isEmpty(userId) && !ToolUtils.isEmpty(deviceId)) {
				showDialog("", getString(R.string.app_loading), true);
				DataInterface.getZoneList(userId, deviceId, callback);
			}
		}

		// 模拟数据
		// ModelData modelData = new ModelData();
		// updateUI(modelData.getZoneInfoList());
	}

	/**
	 * 回调
	 */
	private AjaxCallBack<String> callback = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			hideDialog();
			showToast("", R.string.app_network_error);
		}

		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			hideDialog();
			DataParse dataParse = new DataParse();
			ArrayList<ZoneInfo> listtemp = dataParse.parseZoneInfoList(t, null,
					null, null, null, null);
			updateUI(listtemp);
		}

	};

	/**
	 * 更新ui
	 * 
	 * @param list
	 *            zoneInfo列表
	 */
	private void updateUI(ArrayList<ZoneInfo> list) {
		if (list != null && list.size() > 0) {
			zoneInfolist.clear();
			zoneInfolist.addAll(list);
			zoneListAdapter.notifyDataSetChanged();

			ZoneInfo zoneInfo = zoneInfolist.get(0);
			boolean masterZoneEnable = zoneInfo.isMasterZoneEnable();
			switchstats = masterZoneEnable;
			changeStats();
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
	 * 提交
	 */
	private void doMasterChange() {
		DeviceInfo deviceInfo = DeviceManagers.getInstance().getDeviceInfo();
		if (deviceInfo != null) {
			String deviceId = deviceInfo.getDeviceid();
			UserInfo userInfo = UserManagers.getInstance().getUserInfo();
			if (userInfo != null) {
				String userId = userInfo.getUserId();
				showDialog("", getString(R.string.app_submit_doing), true);
				DataInterface.updateMasterZone(userId, deviceId, switchstats,
						master_callback);
			}
		}
	}

	/**
	 * 修改主区域回调
	 */
	private AjaxCallBack<String> master_callback = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			hideDialog();
			showToast("", R.string.app_network_error);
		}

		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			hideDialog();
			showToast("", R.string.app_operation_success);
		}

	};

	/**
	 * 修改区域属性以后返回
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				try {
					ZoneInfo zoneInfo = (ZoneInfo) intent.getSerializableExtra("zoneInfo");
					int position = intent.getIntExtra("position", -1);
					if (zoneInfo != null) {
						zoneInfolist.remove(position);
						zoneInfolist.add(position, zoneInfo);
						zoneListAdapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_switch:
			switchstats = !switchstats;
			changeStats();
			doMasterChange();
			break;
		}

	}

}
