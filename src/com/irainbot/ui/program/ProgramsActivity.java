package com.irainbot.ui.program;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.parse.DataParse;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.ProgramInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.adapter.ProgramListAdapter;
import com.irainbot.utils.ToolUtils;

import android.os.Bundle;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 程序编辑
 */
public class ProgramsActivity extends BaseActivity implements OnClickListener {
	private ListView lv_program_list; // program列表
	private ArrayList<ProgramInfo> programInfolist; // program 数据
	private ProgramListAdapter programListAdapter; // 数据适配
	private TextView tv_empty; // 空数据

	private TextView tv_create_program;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programs);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.program_title));

		lv_program_list = (ListView) findViewById(R.id.lv_program_list);
		tv_empty = (TextView) findViewById(R.id.tv_empty);

		tv_create_program = (TextView) findViewById(R.id.tv_create_program);
		tv_create_program.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// listView
		programInfolist = new ArrayList<ProgramInfo>();
		programListAdapter = new ProgramListAdapter(this, programInfolist);
		lv_program_list.setAdapter(programListAdapter);
		lv_program_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (programInfolist != null && programInfolist.size() > 0) {
					ProgramInfo programInfo = programInfolist.get(position);
					if (programInfo != null) {
						Intent intent = new Intent(ProgramsActivity.this,
								ProgramEditActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("programInfo", programInfo);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				}
			}
		});

		lv_program_list.setEmptyView(tv_empty);

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
				DataInterface.getProgramList(userId, deviceId, callback);
			}
		}

		// 模拟数据
//		ModelData modelData = new ModelData();
//		updateUI(modelData.getProgramInfoList());
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
			ArrayList<ProgramInfo> listtemp = dataParse.parseProgramInfoList(t);
			updateUI(listtemp);
		}

	};

	/**
	 * 更新ui
	 * 
	 * @param list
	 *            programInfo列表
	 */
	private void updateUI(ArrayList<ProgramInfo> list) {
		if (list != null && list.size() > 0) {
			programInfolist.clear();
			programInfolist.addAll(list);
			programListAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_create_program:
			gotoAct(ProgramEditActivity.class);
			break;
		}

	}

}
