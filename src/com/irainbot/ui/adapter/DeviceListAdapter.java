package com.irainbot.ui.adapter;

import java.util.List;

import com.irainbot.R;
import com.irainbot.entity.DeviceInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * device展示
 */
public class DeviceListAdapter extends BaseAdapter {
	private List<DeviceInfo> devicelist = null;
	private Context context;
	private int isItemShow = View.VISIBLE; // 是否显示右箭头

	public DeviceListAdapter(Context context, List<DeviceInfo> devicelist) {
		this.devicelist = devicelist;
		this.context = context;
	}

	public void setdevicelist(List<DeviceInfo> devicelist) {
		this.devicelist = devicelist;
	}

	public void setItemShow(int isItemShow) {
		this.isItemShow = isItemShow;
	}

	@Override
	public int getCount() {
		if (devicelist != null) {
			return devicelist.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return devicelist == null ? null : devicelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item, null);
			holder = new Holder();
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.iv_item_show = (ImageView) convertView
					.findViewById(R.id.iv_item_show);
			holder.iv_item_show.setVisibility(isItemShow);
			holder.iv_item_show
					.setBackgroundResource(R.drawable.setting_item_show);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (devicelist != null && devicelist.size() > 0) {
			DeviceInfo deviceInfo = devicelist.get(position);
			if (deviceInfo != null) {
				String deviceName = deviceInfo.getDeviceName();

				holder.tv_name.setText(deviceName);
			}
		}
		return convertView;
	}

	static class Holder {
		TextView tv_name;
		ImageView iv_item_show;
	}

}
