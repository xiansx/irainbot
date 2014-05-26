package com.irainbot.ui.main;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.irainbot.entity.RunPlanInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.adapter.RunPlanListAdapter;
import com.irainbot.ui.zone.ZonesDetailActivity;
import com.irainbot.utils.ToolUtils;

/**
 * home plan list
 */
public class HomeView {
	private MainActivity activity;
	private View view;
	private ListView lv_plan_list; // plan展示列表
	private ArrayList<RunPlanInfo> runPlanInfolist; // plan 数据
	private RunPlanListAdapter runPlanListAdapter; // 数据适配
	private TextView tv_empty; // 空数据

	public HomeView(MainActivity activity) {
		this.activity = activity;
		LayoutInflater inflater = LayoutInflater.from(activity);
		view = inflater.inflate(R.layout.nav_home, null);
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
		lv_plan_list = (ListView) view.findViewById(R.id.lv_plan_list);
		tv_empty = (TextView) view.findViewById(R.id.tv_empty);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// listView
		runPlanInfolist = new ArrayList<RunPlanInfo>();
		runPlanListAdapter = new RunPlanListAdapter(activity, runPlanInfolist);
		lv_plan_list.setAdapter(runPlanListAdapter);
		lv_plan_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (runPlanInfolist != null && runPlanInfolist.size() > 0) {
					RunPlanInfo runPlanInfo = runPlanInfolist.get(position);
					if (runPlanInfo != null) {
						Intent intent = new Intent(activity,
								ZonesDetailActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("planInfo", runPlanInfo);
						activity.startActivity(intent);
					}
				}
			}
		});

		lv_plan_list.setEmptyView(tv_empty);

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
			String email = userinfo.getEmail();
			String deviceId = deviceInfo.getDeviceid();
			if (!ToolUtils.isEmpty(email) && !ToolUtils.isEmpty(deviceId)) {
				String from = "20140315";
				String to = "20140515";
				DataInterface.getRunPlanList(email, deviceId, from, to, callback);
			}
		}

		// 模拟数据
		// ModelData modelData = new ModelData();
		// updateUI(modelData.getRunPlanInfoList());
	}

	/**
	 * 回调
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
			DataParse dataParse = new DataParse();
			ArrayList<RunPlanInfo> listtemp = dataParse.parseRunPlanInfoList(t);
			updateUI(listtemp);
		}

	};

	/**
	 * 更新ui
	 * 
	 * @param list
	 *            runplan列表
	 */
	private void updateUI(ArrayList<RunPlanInfo> list) {
		if (list != null && list.size() > 0) {
			runPlanInfolist.clear();
			runPlanInfolist.addAll(list);
			runPlanListAdapter.notifyDataSetChanged();
		}
	}
}
