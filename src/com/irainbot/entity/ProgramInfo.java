package com.irainbot.entity;

import java.io.Serializable;

/**
 * 程序信息
 */
public class ProgramInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String programId; // server generated id
	private String name; // 程序名称
	private boolean weatherSmart;// whether the controller adjust watering among
									// based on weather
	private String activeMonths; // month from 1 to 12 1,2,3,4

	private String everyNDays; // every 3 days to run
	private String startTime; // 21:30 00
	private String duration; // duration for each zone 1,2,3,4,5

	public ProgramInfo() {
	}

	public ProgramInfo(String programId, String name, boolean weatherSmart,
			String activeMonths, String everyNDays, String startTime,
			String duration) {
		this.programId = programId;
		this.name = name;
		this.weatherSmart = weatherSmart;
		this.activeMonths = activeMonths;
		this.everyNDays = everyNDays;
		this.startTime = startTime;
		this.duration = duration;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isWeatherSmart() {
		return weatherSmart;
	}

	public void setWeatherSmart(boolean weatherSmart) {
		this.weatherSmart = weatherSmart;
	}

	public String getActiveMonths() {
		return activeMonths;
	}

	public void setActiveMonths(String activeMonths) {
		this.activeMonths = activeMonths;
	}

	public String getEveryNDays() {
		return everyNDays;
	}

	public void setEveryNDays(String everyNDays) {
		this.everyNDays = everyNDays;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
