package com.irainbot.ui.adapter;

import java.util.List;

import com.irainbot.R;
import com.irainbot.entity.ZoneInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * plan zone展示
 */
public class RunPlanZoneListAdapter extends BaseAdapter {
	private List<ZoneInfo> zonelist = null;
	private Context context;
	private int isSeekBarShow = View.VISIBLE; // 是否显示seekbar
	private int isMoreTimeShow = View.GONE; // 是否显示 详细时间

	public RunPlanZoneListAdapter(Context context, List<ZoneInfo> zonelist) {
		this.zonelist = zonelist;
		this.context = context;
	}

	public void setIsSeekBarShow(int isSeekBarShow) {
		this.isSeekBarShow = isSeekBarShow;
	}

	public void setIsMoreTimeShow(int isMoreTimeShow) {
		this.isMoreTimeShow = isMoreTimeShow;
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
					R.layout.plan_zone_item, null);
			holder = new Holder();
			holder.tv_zone_name = (TextView) convertView
					.findViewById(R.id.tv_zone_name);
			holder.tv_finished = (TextView) convertView
					.findViewById(R.id.tv_finished);
			holder.tv_duration = (TextView) convertView
					.findViewById(R.id.tv_duration);

			holder.sb_zone_duration = (SeekBar) convertView
					.findViewById(R.id.sb_zone_duration);
			holder.sb_zone_duration.setVisibility(isSeekBarShow);

			holder.tv_zone_alias = (TextView) convertView
					.findViewById(R.id.tv_zone_alias);

			holder.ll_zonetime_detail = (LinearLayout) convertView
					.findViewById(R.id.ll_zonetime_detail);
			holder.ll_zonetime_detail.setVisibility(isMoreTimeShow);
			holder.tv_time_smart = (TextView) convertView
					.findViewById(R.id.tv_time_smart);
			holder.tv_original = (TextView) convertView
					.findViewById(R.id.tv_original);
			holder.tv_manual = (TextView) convertView
					.findViewById(R.id.tv_manual);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (zonelist != null && zonelist.size() > 0) {
			ZoneInfo zoneInfo = zonelist.get(position);
			if (zoneInfo != null) {
				String zoneName = zoneInfo.getZoneName();
				StringBuilder sb = new StringBuilder();
				sb.append(zoneInfo.getVegation())
						.append("," + zoneInfo.getSunshine())
						.append("," + zoneInfo.getSlope())
						.append("," + zoneInfo.getSoil());
				int finished = zoneInfo.getFinished();
				int duration = zoneInfo.getDuration();

				int smartTime = zoneInfo.getSmart();
				int originalTime = zoneInfo.getProgram();
				int manualTime = zoneInfo.getManual();

				holder.tv_zone_name.setText((position + 1) + "." + zoneName);
				holder.tv_finished.setText(finished + "");
				holder.tv_duration.setText(duration + "");
				holder.tv_zone_alias.setText(sb.toString());

				holder.sb_zone_duration.setProgress(duration);
				holder.sb_zone_duration.setSecondaryProgress(finished);

				holder.tv_time_smart.setText("Smart "
						+ String.valueOf(smartTime) + " min");
				holder.tv_original.setText("Original "
						+ String.valueOf(originalTime) + " min");
				holder.tv_manual.setText("Manual " + String.valueOf(manualTime)
						+ " min");
			}
		}
		return convertView;
	}

	static class Holder {
		TextView tv_zone_name;
		TextView tv_finished;
		TextView tv_duration;
		SeekBar sb_zone_duration;
		TextView tv_zone_alias;

		LinearLayout ll_zonetime_detail;
		TextView tv_time_smart;
		TextView tv_original;
		TextView tv_manual;
	}

}
