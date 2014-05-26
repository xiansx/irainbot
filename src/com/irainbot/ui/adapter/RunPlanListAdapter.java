package com.irainbot.ui.adapter;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import com.irainbot.R;
import com.irainbot.entity.RunPlanInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * plan展示
 */
public class RunPlanListAdapter extends BaseAdapter {
	private List<RunPlanInfo> runPlanInfolist = null;
	private Context context;
	private FinalBitmap fb;
	private Bitmap defBitMap;

	public RunPlanListAdapter(Context context, List<RunPlanInfo> runPlanInfolist) {
		this.runPlanInfolist = runPlanInfolist;
		this.context = context;
		fb = FinalBitmap.create(context);
		defBitMap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.icon_weather);
	}

	public void setRunPlanInfolist(List<RunPlanInfo> runPlanInfolist) {
		this.runPlanInfolist = runPlanInfolist;
	}

	@Override
	public int getCount() {
		if (runPlanInfolist != null) {
			return runPlanInfolist.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return runPlanInfolist == null ? null : runPlanInfolist.get(position);
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
					R.layout.plan_item, null);
			holder = new Holder();
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_starttime = (TextView) convertView
					.findViewById(R.id.tv_starttime);
			holder.tv_finished = (TextView) convertView
					.findViewById(R.id.tv_finished);
			holder.tv_duration = (TextView) convertView
					.findViewById(R.id.tv_duration);

			holder.iv_weather_icon = (ImageView) convertView
					.findViewById(R.id.iv_weather_icon);
			holder.tv_weather = (TextView) convertView
					.findViewById(R.id.tv_weather);

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

		if (runPlanInfolist != null && runPlanInfolist.size() > 0) {
			RunPlanInfo runPlanInfo = runPlanInfolist.get(position);
			if (runPlanInfo != null) {
				String date = runPlanInfo.getDate();
				String starttime = runPlanInfo.getStartTime();
				int finished = runPlanInfo.getFinished();
				int duration = runPlanInfo.getDuration();
				String weather = runPlanInfo.getWeatherTempMin() + "℃/"
						+ runPlanInfo.getWeatherTempMax() + "℃";
				int smartTime = runPlanInfo.getSmart();
				int originalTime = runPlanInfo.getProgram();
				int manualTime = runPlanInfo.getManual();

				fb.display(holder.iv_weather_icon, "", defBitMap, defBitMap);

				holder.tv_date.setText(date);
				holder.tv_starttime.setText(starttime);
				holder.tv_finished.setText(String.valueOf(finished));
				holder.tv_duration.setText(String.valueOf(duration));
				holder.tv_weather.setText(weather);
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
		TextView tv_date;
		TextView tv_starttime;
		TextView tv_finished;
		TextView tv_duration;

		ImageView iv_weather_icon;
		TextView tv_weather;

		TextView tv_time_smart;
		TextView tv_original;
		TextView tv_manual;
	}

}
