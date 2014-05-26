package com.irainbot.ui.adapter;

import java.util.List;

import com.irainbot.R;
import com.irainbot.entity.ZoneInfo;
import com.irainbot.utils.ToolUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * zone展示
 */
public class ZoneListAdapter extends BaseAdapter {
	private List<ZoneInfo> zonelist = null;
	private Context context;
	private int isSeekBarShow = View.VISIBLE; // 是否显示seekbar
	private int isItemShow = View.VISIBLE; // 是否显示右箭头

	public ZoneListAdapter(Context context, List<ZoneInfo> zonelist) {
		this.zonelist = zonelist;
		this.context = context;
	}

	public void setItemShow(int isItemShow) {
		this.isItemShow = isItemShow;
	}

	public void setIsSeekBarShow(int isSeekBarShow) {
		this.isSeekBarShow = isSeekBarShow;
	}

	public void setzonelist(List<ZoneInfo> zonelist) {
		this.zonelist = zonelist;
	}

	@Override
	public int getCount() {
		if (zonelist != null) {
			return zonelist.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return zonelist == null ? null : zonelist.get(position);
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
			holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
			holder.tv_des.setVisibility(View.VISIBLE);

			holder.iv_item_show = (ImageView) convertView
					.findViewById(R.id.iv_item_show);
			holder.iv_item_show.setVisibility(isItemShow);
			holder.iv_item_show
					.setBackgroundResource(R.drawable.setting_item_show);

			holder.tv_duration_show = (TextView) convertView
					.findViewById(R.id.tv_duration_show);
			holder.tv_duration_show.setVisibility(isSeekBarShow);
			holder.sb_duration = (SeekBar) convertView
					.findViewById(R.id.sb_duration);
			holder.sb_duration.setVisibility(isSeekBarShow);
			holder.sb_duration.setOnSeekBarChangeListener(new OnSeekBarChange(
					holder.tv_duration_show, position));

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.tv_duration_show.setText("0 min");
		holder.sb_duration.setProgress(0);
		convertView.setBackgroundResource(R.drawable.bg_pop_item);

		if (zonelist != null && zonelist.size() > 0) {
			ZoneInfo zoneInfo = zonelist.get(position);
			if (zoneInfo != null) {
				String zoneName = zoneInfo.getZoneName();
				String vegation = zoneInfo.getVegation();
				String sunshine = zoneInfo.getSunshine();
				String slope = zoneInfo.getSlope();
				String soil = zoneInfo.getSoil();
				StringBuilder sb = new StringBuilder();
				boolean zoneEnable = zoneInfo.isZoneEnable();
				if (zoneEnable) {
					sb.append("Enabled");
				} else {
					sb.append("Disabled");
				}

				if (!ToolUtils.isEmpty(vegation)) {
					sb.append("," + vegation);
				}

				if (!ToolUtils.isEmpty(sunshine)) {
					sb.append("," + sunshine);
				}

				if (!ToolUtils.isEmpty(slope)) {
					sb.append("," + slope);
				}

				if (!ToolUtils.isEmpty(soil)) {
					sb.append("," + soil);
				}

				String zoneId = zoneInfo.getZoneId();
				String nameShow = "Value " + zoneId;
				if (!ToolUtils.isEmpty(zoneName)) {
					nameShow += " - " + zoneName;
				}

				String masterId = zoneInfo.getMasterZoneId();
				if (!ToolUtils.isEmpty(zoneId) && !ToolUtils.isEmpty(masterId)
						&& masterId.equals(zoneId)) {
					// 不可点击
					nameShow += " - master";
					convertView.setBackgroundResource(R.color.red);
				}

				holder.tv_name.setText(nameShow);
				holder.tv_des.setText(sb.toString());

				// 显示时间控制进度
				if (isSeekBarShow == View.VISIBLE) {
					int program = zoneInfo.getProgram();
					holder.sb_duration.setProgress(program);
				}

			}
		}
		return convertView;
	}

	class OnSeekBarChange implements OnSeekBarChangeListener {
		private TextView tv;
		private int position;

		public OnSeekBarChange(TextView tv, int position) {
			this.tv = tv;
			this.position = position;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			tv.setText(String.valueOf(progress) + " min");
			if (zonelist != null && zonelist.size() > 0) {
				ZoneInfo zoneInfo = zonelist.get(position);
				if (zoneInfo != null) {
					zoneInfo.setProgram(progress);
				}
			}

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	}

	static class Holder {
		TextView tv_name;
		TextView tv_des;
		SeekBar sb_duration;
		TextView tv_duration_show;
		ImageView iv_item_show;
	}

}
