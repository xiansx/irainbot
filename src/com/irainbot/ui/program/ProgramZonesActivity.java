package com.irainbot.ui.program;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
 * 添加/修改 program zone 时间
 */
public class ProgramZonesActivity extends BaseActivity {
	private ListView lv_zone_list; // zone列表
	private ArrayList<ZoneInfo> zoneInfolist; // zone 数据
	private ZoneListAdapter zoneListAdapter; // 数据适配
	private TextView tv_empty; // 空数据

	private String duration; // 各个区域的时间数组 1,2,3,4...

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.program_edit_watering_zones_lable));

		lv_zone_list = (ListView) findViewById(R.id.lv_list);
		tv_empty = (TextView) findViewById(R.id.tv_empty);

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		try {
			duration = getIntent().getStringExtra("duration");
		} catch (Exception e) {
		}

		// listView
		zoneInfolist = new ArrayList<ZoneInfo>();
		zoneListAdapter = new ZoneListAdapter(this, zoneInfolist);
		zoneListAdapter.setIsSeekBarShow(View.VISIBLE);
		zoneListAdapter.setItemShow(View.GONE);
		lv_zone_list.setAdapter(zoneListAdapter);
		lv_zone_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
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
			showToast("", R.string.app_network_error);
		}

		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			hideDialog();
			DataParse dataParse = new DataParse();
			ArrayList<ZoneInfo> listtemp = dataParse.parseZoneInfoList(t, null,
					null, duration, null, null);
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
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if (zoneInfolist != null && zoneInfolist.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < zoneInfolist.size(); i++) {
					ZoneInfo zoneInfo = zoneInfolist.get(i);
					if (zoneInfo != null) {
						int program = zoneInfo.getProgram();
						if (sb.length() > 0)
							sb.append(",");
						sb.append(program);
					}

				}
			}
			Intent intent = new Intent();
			intent.putExtra("duration", duration);
			setResult(RESULT_OK, intent);
		} catch (Exception e) {
		}
	}

}
