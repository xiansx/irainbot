package com.irainbot.ui.device;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.http.AjaxCallBack;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.irainbot.ModelData;
import com.irainbot.R;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.adapter.DeviceListAdapter;
import com.irainbot.utils.ToolUtils;

/**
 * 添加设备
 */
public class AddDeviceActivity extends BaseActivity {
	private ListView lv_device_list; // device列表
	private ArrayList<DeviceInfo> deviceInfolist; // device 数据
	private DeviceListAdapter deviceListAdapter; // 数据适配
	private TextView tv_empty; // 空数据

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
		setTitle(getString(R.string.device_add));

		lv_device_list = (ListView) findViewById(R.id.lv_device_list);
		tv_empty = (TextView) findViewById(R.id.tv_empty);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// listView
		deviceInfolist = new ArrayList<DeviceInfo>();
		deviceListAdapter = new DeviceListAdapter(this, deviceInfolist);
		lv_device_list.setAdapter(deviceListAdapter);
		lv_device_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (deviceInfolist != null && deviceInfolist.size() > 0) {
					DeviceInfo deviceInfo = deviceInfolist.get(position);
					if (deviceInfo != null) {
						showAlertDialog(deviceInfo);
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
		// DataInterface.getRunPlanList(callback);

		// 模拟数据
		ModelData modelData = new ModelData();
		updateUI(modelData.getDeviceInfoList());
		
		ModelData.addDebugDevice(new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				try {
					JSONObject obj = new JSONObject(t);
					String deviceid = obj.optString("id");
					UserInfo userInfo = UserManagers.getInstance().getUserInfo();
					String userid = userInfo.getUserId();
					ModelData.linkDeviceAndUser(userid, deviceid, new AjaxCallBack<String>() {

						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							// TODO Auto-generated method stub
							super.onFailure(t, errorNo, strMsg);
						}

						@Override
						public void onSuccess(String t) {
							// TODO Auto-generated method stub
							super.onSuccess(t);
						}
						
					});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
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
			// int result = -1;
			// 解析数据并更新ui
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

	/**
	 * 弹出选择提示框
	 */
	public void showAlertDialog(DeviceInfo deviceInfo) {
		final View pincodeView; // 弹出框中的输入框
		final EditText et_pincode; // 输入pincode

		LayoutInflater inflater = LayoutInflater.from(this);
		pincodeView = inflater.inflate(R.layout.pincode_input, null);
		et_pincode = (EditText) pincodeView.findViewById(R.id.et_pincode);

		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.device_pincode_title);
		builder.setView(pincodeView);
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
						// 校对pincode处理
						String pincode = et_pincode.getText().toString();
						checkPinCode(pincode);
					}
				});
		builder.create().show();
	}

	/**
	 * 控制器匹配链接检测
	 */
	private void checkPinCode(String pincode) {
		
		// pincode 匹配检测
		
		
		if (!ToolUtils.isEmpty(pincode)) {
			Intent intent = new Intent(AddDeviceActivity.this,
					DeviceWFSetActivity.class);
			intent.putExtra("ssid", "");
			intent.putExtra("ip", "");
			startActivity(intent);
		}
	}
}
