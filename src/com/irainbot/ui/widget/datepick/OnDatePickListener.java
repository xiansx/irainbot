package com.irainbot.ui.widget.datepick;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.irainbot.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 日期选择监听
 */
public class OnDatePickListener implements OnClickListener {
	private Activity activity;
	private TextView datetime;
	private WheelMain wheelMain;
	private DateFormat dateFormat;
	private String format;

	public static final String DATEFORMAT_HHMM = "HH:mm";

	public OnDatePickListener(Activity activity, String format,
			TextView datetime) {
		this.activity = activity;
		this.datetime = datetime;
		this.format = format;
		dateFormat = new SimpleDateFormat(format);
	}

	@Override
	public void onClick(View arg0) {
		LayoutInflater inflater = LayoutInflater.from(activity);
		final View timepickerview = inflater.inflate(R.layout.timepicker, null);
		ScreenInfo screenInfo = new ScreenInfo(activity);
		wheelMain = new WheelMain(timepickerview, true);
		wheelMain.screenheight = screenInfo.getHeight();
		String time = datetime.getText().toString();
		Calendar calendar = Calendar.getInstance();
		if (JudgeDate.isDate(time, format)) {
			try {
				calendar.setTime(dateFormat.parse(time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelMain.initDateTimePicker(year, month, day, hour, minute);
		new AlertDialog.Builder(activity)
				.setTitle("Select Time")
				.setView(timepickerview)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						datetime.setText(wheelMain.getTime());
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).show();
	}

}
