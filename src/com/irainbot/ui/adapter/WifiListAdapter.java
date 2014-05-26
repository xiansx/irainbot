package com.irainbot.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.irainbot.R;
import com.irainbot.entity.WifiInfo;

/**
 * wifi展示
 */
public class WifiListAdapter extends BaseAdapter {
	private List<WifiInfo> wifiInfolist = null;
	private Context context;

	public WifiListAdapter(Context context, List<WifiInfo> wifiInfolist) {
		this.wifiInfolist = wifiInfolist;
		this.context = context;
	}

	public void setdevicelist(List<WifiInfo> wifiInfolist) {
		this.wifiInfolist = wifiInfolist;
	}

	@Override
	public int getCount() {
		if (wifiInfolist != null) {
			return wifiInfolist.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return wifiInfolist == null ? null : wifiInfolist.get(position);
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
			holder.iv_item_show = (ImageView) convertView.findViewById(R.id.iv_item_show);
			holder.iv_item_show.setVisibility(View.VISIBLE);
			holder.iv_item_show.setBackgroundResource(R.drawable.wifi_sign);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (wifiInfolist != null && wifiInfolist.size() > 0) {
			WifiInfo wifiInfo = wifiInfolist.get(position);
			if (wifiInfo != null) {
				String wifiName = wifiInfo.getWifiName();
				int level = wifiInfo.getLevel();

				holder.tv_name.setText(wifiName);
			}
		}
		return convertView;
	}

	static class Holder {
		TextView tv_name;
		ImageView iv_item_show;
	}

}
