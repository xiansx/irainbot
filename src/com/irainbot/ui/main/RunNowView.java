package com.irainbot.ui.main;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.irainbot.ui.adapter.RunPlanZoneListAdapter;
import com.irainbot.utils.ToolUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * RunNow zone list
 */
public class RunNowView {
	private MainActivity activity;
	private View view;
	private ListView lv_zone_list; // runnow zone展示列表
	private ArrayList<ZoneInfo> zoneInfolist; // zone 数据
	private RunPlanZoneListAdapter runPlanZoneListAdapter; // 数据适配
	private TextView tv_empty; // 空数据

	private ProgressBar pb_total_time; // plan 总的进度
	private TextView tv_time_show; // plan 总的时间显示

	private String email; // 登录用户
	private String deviceId; // 当前设备id
	private RunPlanInfo planInfo; // 计划

	private ImageView iv_save; // 保存按钮

	public RunNowView(MainActivity activity) {
		this.activity = activity;
		LayoutInflater inflater = LayoutInflater.from(activity);
		view = inflater.inflate(R.layout.nav_runnow, null);
		initView();
		initData();
	}

	public View getView() {
		return view;
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		lv_zone_list = (ListView) view.findViewById(R.id.lv_zone_list);
		tv_empty = (TextView) view.findViewById(R.id.tv_empty);

		pb_total_time = (ProgressBar) view.findViewById(R.id.pb_total_time);
		tv_time_show = (TextView) view.findViewById(R.id.tv_time_show);

		iv_save = (ImageView) activity.findViewById(R.id.iv_setting);
//		iv_save.setVisibility(View.VISIBLE);
		iv_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doConfigure(""); // 修改计划
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// listView
		zoneInfolist = new ArrayList<ZoneInfo>();
		runPlanZoneListAdapter = new RunPlanZoneListAdapter(activity,
				zoneInfolist);
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
		if (userinfo != null && deviceInfo != null) {
			email = userinfo.getEmail();
			deviceId = deviceInfo.getDeviceid();
			if (!ToolUtils.isEmpty(email) && !ToolUtils.isEmpty(deviceId)) {
				activity.showDialog("",
						activity.getString(R.string.app_loading), true);
				DataInterface.getRunNowPlan(email, deviceId, callback_plan);
			}
		}

		// 模拟数据
		// ModelData modelData = new ModelData();
		// updateUI(modelData.getZoneInfoList());
	}

	/**
	 * 回调 plan
	 */
	private AjaxCallBack<String> callback_plan = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			activity.showToast("", R.string.app_network_error);
		}

		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			activity.hideDialog();
			DataParse dataParse = new DataParse();
			try {
				JSONObject obj = new JSONObject(t);
				planInfo = dataParse.parseRunplan(obj);
				if (planInfo != null) {
					DataInterface.getZoneList(email, deviceId, callback);
					int duration = planInfo.getDuration();
					int finished = planInfo.getFinished();
					pb_total_time.setProgress(finished);
					tv_time_show.setText("Finished " + finished
							+ " min / Total " + duration + " min");
				}
			} catch (JSONException e) {
			}
		}

	};

	/**
	 * 回调 zonelist
	 */
	private AjaxCallBack<String> callback = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			activity.showToast("", R.string.app_network_error);
		}

		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			activity.hideDialog();
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
	 *            runplan列表
	 */
	private void updateUI(ArrayList<ZoneInfo> list) {
		if (list != null && list.size() > 0) {
			zoneInfolist.clear();
			zoneInfolist.addAll(list);
			runPlanZoneListAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 计划修改
	 */
	private void doConfigure(String action) {
		StringBuilder manualArray = new StringBuilder();
		if (zoneInfolist != null && zoneInfolist.size() > 0) {
			for (int i = 0; i < zoneInfolist.size(); i++) {
				ZoneInfo zoneInfo = zoneInfolist.get(i);
				if (zoneInfo != null) {
					int manual = zoneInfo.getManual();
					if (manualArray.length() > 0)
						manualArray.append(",");
					manualArray.append(manual);
				}
			}
		}

		DeviceInfo deviceInfo = DeviceManagers.getInstance().getDeviceInfo();
		if (deviceInfo != null) {
			String deviceId = deviceInfo.getDeviceid();
			UserInfo userInfo = UserManagers.getInstance().getUserInfo();
			if (userInfo != null) {
				String email = userInfo.getEmail();
				activity.showDialog("",
						activity.getString(R.string.app_submit_doing), true);
				DataInterface.updateRunPlan(email, deviceId,
						manualArray.toString(), action, callback);
			}
		}

	}
}
