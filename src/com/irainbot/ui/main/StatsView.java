package com.irainbot.ui.main;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.parse.DataParse;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.RunPlanInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.zone.ZonesDetailActivity;
import com.irainbot.utils.ToolUtils;

/**
 * stats plan list
 */
public class StatsView {
	private MainActivity activity;
	private View view;
	private ArrayList<RunPlanInfo> runPlanInfolist; // plan 数据
	private RelativeLayout include_plan_item;

	private RunPlanInfo runPlanInfo;

	public StatsView(MainActivity activity) {
		this.activity = activity;
		LayoutInflater inflater = LayoutInflater.from(activity);
		view = inflater.inflate(R.layout.nav_stats, null);
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
		include_plan_item = (RelativeLayout) view
				.findViewById(R.id.include_plan_item);

		include_plan_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity,
						ZonesDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("planInfo", runPlanInfo);
				activity.startActivity(intent);
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		runPlanInfolist = new ArrayList<RunPlanInfo>();

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
//		ModelData modelData = new ModelData();
//		updateUI(modelData.getRunPlanInfoList());
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
		}
	}
}
