package com.irainbot.ui.adapter;

import java.util.List;

import com.irainbot.R;
import com.irainbot.entity.ProgramInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * program展示
 */
public class ProgramListAdapter extends BaseAdapter {
	private List<ProgramInfo> programlist = null;
	private Context context;

	public ProgramListAdapter(Context context, List<ProgramInfo> programlist) {
		this.programlist = programlist;
		this.context = context;
	}

	public void setdevicelist(List<ProgramInfo> programlist) {
		this.programlist = programlist;
	}

	@Override
	public int getCount() {
		if (programlist != null) {
			return programlist.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return programlist == null ? null : programlist.get(position);
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
					R.layout.program_list_item, null);
			holder = new Holder();
			holder.tv_program_name = (TextView) convertView
					.findViewById(R.id.tv_program_name);
			holder.tv_months = (TextView) convertView
					.findViewById(R.id.tv_months);
			holder.tv_frequency = (TextView) convertView
					.findViewById(R.id.tv_frequency);
			holder.tv_duration = (TextView) convertView
					.findViewById(R.id.tv_duration);
			holder.tv_weather_smart = (TextView) convertView
					.findViewById(R.id.tv_weather_smart);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (programlist != null && programlist.size() > 0) {
			ProgramInfo programInfo = programlist.get(position);
			if (programInfo != null) {
				String programName = programInfo.getName();
				String months = programInfo.getActiveMonths();
				String frequency = programInfo.getEveryNDays();
				String duration = programInfo.getDuration();
				String startTime = programInfo.getStartTime();
				boolean weatherSmart = programInfo.isWeatherSmart();

				holder.tv_program_name.setText(programName);
				holder.tv_months.setText("Months " + months);
				holder.tv_frequency.setText(frequency + " at " + startTime);
				holder.tv_duration.setText("Duration " + duration + "mins");

				if (weatherSmart) {
					holder.tv_weather_smart.setText("Weather Smart ON");
				} else {
					holder.tv_weather_smart.setText("Weather Smart OFF");
				}
			}
		}
		return convertView;
	}

	static class Holder {
		TextView tv_program_name;
		TextView tv_months;
		TextView tv_frequency;
		TextView tv_duration;
		TextView tv_weather_smart;
	}

}
