package com.irainbot.ui.main;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.irainbot.R;
import com.irainbot.action.device.DeviceManagers;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.parse.DataParse;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.DeviceInfo;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.adapter.ViewPagerAdapter;
import com.irainbot.utils.ToolUtils;

/**
 * 导航主界面
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout rl_nav_home, rl_nav_runnow, rl_nav_stats,
			rl_nav_settings; // 导航
	private ViewPager viewPager; // 导航翻页
	private ViewPagerAdapter viewPagerAdapter;
	private ArrayList<View> pagerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkLogin();
		setContentView(R.layout.nav_main);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		rl_nav_home = (RelativeLayout) findViewById(R.id.rl_nav_home);
		rl_nav_runnow = (RelativeLayout) findViewById(R.id.rl_nav_runnow);
		rl_nav_stats = (RelativeLayout) findViewById(R.id.rl_nav_stats);
		rl_nav_settings = (RelativeLayout) findViewById(R.id.rl_nav_settings);

		rl_nav_home.setOnClickListener(this);
		rl_nav_runnow.setOnClickListener(this);
		rl_nav_stats.setOnClickListener(this);
		rl_nav_settings.setOnClickListener(this);

		viewPager = (ViewPager) findViewById(R.id.vp_main);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 默认选择第一项
		changeTabState(0);

		// viewPager
		pagerList = new ArrayList<View>();

		// 添加分页
		pagerList.add((new HomeView(this)).getView());
		pagerList.add((new RunNowView(this)).getView());
		pagerList.add((new StatsView(this)).getView());
		pagerList.add((new SettingsView(this)).getView());

		viewPagerAdapter = new ViewPagerAdapter(pagerList);
		viewPager.setAdapter(viewPagerAdapter);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				changeTabState(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		// 初始化菜单
		initMenu();

		// 获取数据
		getData();
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		DeviceInfo deviceInfo = DeviceManagers.getInstance().getDeviceInfo();
		if (deviceInfo == null) { // 如果当前没有设备（内存&本地），获取设备列表，并指定第一个为当前设备
			UserInfo userinfo = UserManagers.getInstance().getUserInfo();
			if (userinfo != null) {
				String userId = userinfo.getUserId();
				if (!ToolUtils.isEmpty(userId)) {
					showDialog("", getString(R.string.app_loading), true);
					DataInterface.getDeviceList(userId, callback);
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
			if (listtemp != null && listtemp.size() > 0) {
				DeviceInfo deviceInfo = listtemp.get(0);
				if (deviceInfo != null) { // 指定列表中第一个为当前设备
					DeviceManagers.getInstance().setDeviceInfo(deviceInfo);
				}
			}
		}

	};

	/**
	 * tab的选择
	 * 
	 * @param resid
	 *            当前tab
	 */
	private void changeTabState(int index) {
		switch (index) {
		case 0:
			rl_nav_home.setBackgroundResource(R.drawable.main_tab_pressed);
			rl_nav_runnow.setBackgroundResource(R.drawable.main_tab_normal);
			rl_nav_stats.setBackgroundResource(R.drawable.main_tab_normal);
			rl_nav_settings.setBackgroundResource(R.drawable.main_tab_normal);
			viewPager.setCurrentItem(0);
			setTitle(getString(R.string.navigationvar_home));
			break;
		case 1:
			rl_nav_home.setBackgroundResource(R.drawable.main_tab_normal);
			rl_nav_runnow.setBackgroundResource(R.drawable.main_tab_pressed);
			rl_nav_stats.setBackgroundResource(R.drawable.main_tab_normal);
			rl_nav_settings.setBackgroundResource(R.drawable.main_tab_normal);
			viewPager.setCurrentItem(1);
			setTitle(getString(R.string.navigationvar_runnow));
			break;
		case 2:
			rl_nav_home.setBackgroundResource(R.drawable.main_tab_normal);
			rl_nav_runnow.setBackgroundResource(R.drawable.main_tab_normal);
			rl_nav_stats.setBackgroundResource(R.drawable.main_tab_pressed);
			rl_nav_settings.setBackgroundResource(R.drawable.main_tab_normal);
			viewPager.setCurrentItem(2);
			setTitle(getString(R.string.navigationvar_stats));
			break;
		case 3:
			rl_nav_home.setBackgroundResource(R.drawable.main_tab_normal);
			rl_nav_runnow.setBackgroundResource(R.drawable.main_tab_normal);
			rl_nav_stats.setBackgroundResource(R.drawable.main_tab_normal);
			rl_nav_settings.setBackgroundResource(R.drawable.main_tab_pressed);
			viewPager.setCurrentItem(3);
			setTitle(getString(R.string.navigationvar_settings));
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_nav_home: // home
			changeTabState(0);
			break;
		case R.id.rl_nav_runnow: // runnow
			changeTabState(1);
			break;
		case R.id.rl_nav_stats: // stats
			changeTabState(2);
			break;
		case R.id.rl_nav_settings: // settings
			changeTabState(3);
			break;
		}
	}
}
