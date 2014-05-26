package com.irainbot.ui.device;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.parse.DataParse;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.adapter.DeviceListAdapter;
import com.irainbot.utils.ToolUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 更换设备
 */
public class SwitchDeviceActivity extends BaseActivity {
	private ListView lv_device_list; // device列表
	private ArrayList<DeviceInfo> deviceInfolist; // device 数据
	private DeviceListAdapter deviceListAdapter; // 数据适配
	private TextView tv_empty; // 空数据

	private TextView tv_device_lable; // 提示

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_add);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.device_switch));

		lv_device_list = (ListView) findViewById(R.id.lv_device_list);
		tv_empty = (TextView) findViewById(R.id.tv_empty);

		tv_device_lable = (TextView) findViewById(R.id.tv_device_lable);
		tv_device_lable.setText(R.string.device_switch_choose);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// listView
		deviceInfolist = new ArrayList<DeviceInfo>();
		deviceListAdapter = new DeviceListAdapter(this, deviceInfolist);
		deviceListAdapter.setItemShow(View.GONE); // item不显示右箭头
		lv_device_list.setAdapter(deviceListAdapter);
		lv_device_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (deviceInfolist != null && deviceInfolist.size() > 0) {
					DeviceInfo deviceInfo = deviceInfolist.get(position);
					if (deviceInfo != null) {
						showToast("", R.string.app_operation_success);
						// 设置当前设备
						DeviceManagers.getInstance().setDeviceInfo(deviceInfo);
						finish();
					}
				}
			}
		});

		lv_device_list.setEmptyView(tv_empty);

		// 获取数据
		getData();
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		UserInfo userinfo = UserManagers.getInstance().getUserInfo();
		if (userinfo != null) {
			String userId = userinfo.getUserId();
			if (!ToolUtils.isEmpty(userId)) {
				showDialog("", getString(R.string.app_loading), true);
				DataInterface.getDeviceList(userId, callback);
			}
		}

		// 模拟数据
		// ModelData modelData = new ModelData();
		// updateUI(modelData.getDeviceInfoList());
	}

	/**
	 * 回调
	 */
	private AjaxCallBack<String> callback = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			showToast("", R.string.app_network_error);
			hideDialog();
		}

		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			hideDialog();
			DataParse dataParse = new DataParse();
			ArrayList<DeviceInfo> listtemp = dataParse.parseDeviceInfoList(t);
			updateUI(listtemp);
		}

	};

	/**
	 * 更新ui
	 * 
	 * @param list
	 *            DeviceInfo列表
	 */
	private void updateUI(ArrayList<DeviceInfo> list) {
		if (list != null && list.size() > 0) {
			deviceInfolist.clear();
			deviceInfolist.addAll(list);
			deviceListAdapter.notifyDataSetChanged();
		}
	}

}
