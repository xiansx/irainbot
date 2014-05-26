package com.irainbot.ui.zone;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.parse.DataParse;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.RunPlanInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.entity.ZoneInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.adapter.RunPlanZoneListAdapter;
import com.irainbot.utils.ToolUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * plan zone detail
 */
public class ZonesDetailActivity extends BaseActivity {
	private ListView lv_zone_list; // runnow zone展示列表
	private ArrayList<ZoneInfo> zoneInfolist; // zone 数据
	private RunPlanZoneListAdapter runPlanZoneListAdapter; // 数据适配
	private TextView tv_empty; // 空数据

	private RunPlanInfo planInfo; // 计划

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
		lv_zone_list = (ListView) findViewById(R.id.lv_list);
		tv_empty = (TextView) findViewById(R.id.tv_empty);

		setTitle(getString(R.string.zone_plan_detail_title));
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		try {
			Bundle bundle = getIntent().getExtras();
			planInfo = (RunPlanInfo) bundle.getSerializable("planInfo");
		} catch (Exception e) {
		}

		// listView
		zoneInfolist = new ArrayList<ZoneInfo>();
		runPlanZoneListAdapter = new RunPlanZoneListAdapter(this, zoneInfolist);
		runPlanZoneListAdapter.setIsMoreTimeShow(View.VISIBLE);
		runPlanZoneListAdapter.setIsSeekBarShow(View.GONE);
		lv_zone_list.setAdapter(runPlanZoneListAdapter);
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
		if (userinfo != null) {
			String email = userinfo.getEmail();
			String deviceId = deviceInfo.getDeviceid();
			if (!ToolUtils.isEmpty(email) && !ToolUtils.isEmpty(deviceId)) {
				showDialog("", getString(R.string.app_loading), true);
				DataInterface.getZoneList(email, deviceId, callback);
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
			String durationArray = "";
			String finishedArray = "";
			String programArray = "";
			String smartArray = "";
			String manualArray = "";
			if (planInfo != null) {
				durationArray = planInfo.getDurationArray();
				finishedArray = planInfo.getFinishedArray();
				programArray = planInfo.getProgramArray();
				smartArray = planInfo.getSmartArray();
				manualArray = planInfo.getManualArray();
			}
			ArrayList<ZoneInfo> listtemp = dataParse.parseZoneInfoList(t,
					durationArray, finishedArray, programArray, smartArray,
					manualArray);
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
			runPlanZoneListAdapter.notifyDataSetChanged();
		}
	}

}
