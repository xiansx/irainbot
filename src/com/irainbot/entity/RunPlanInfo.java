package com.irainbot.entity;

import java.io.Serializable;

/**
 * 计划信息
 */
public class RunPlanInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String date; // 创建日期
	private String startTime; // 计划开始时间
	private String stopTime; // 计划结束时间
	private String status; // 状态 “finished”, “running”, “paused”
	private int duration; // 　总时间
	private int finished; // 已经完成时间

	// composition, duration program + weather + manual
	private int program; // 计划初始设置值
	private int smart; // 根据天气智能设置值
	private int manual; // 手动设置值

	private String weatherCondition; // 天气情况 sunny
	private String weatherTempMax; // 最高温度
	private String weatherTempMin; // 最低温度

	private String durationArray = ""; // 各区域总时间数组
	private String finishedArray = ""; // 各区域使用时间数组
	private String programArray = ""; // 各区域初始时间数组
	private String smartArray = ""; // 各区域智能调整时间数组
	private String manualArray = ""; // 各区域手动调整时间数组

	public RunPlanInfo() {
	}

	public RunPlanInfo(String date, String startTime, String stopTime,
			String status, int duration, int finished, int program, int smart,
			int manual, String weatherCondition, String weatherTempMax,
			String weatherTempMin, String durationArray, String finishedArray,
			String programArray, String smartArray, String manualArray) {
		this.date = date;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.status = status;
		this.duration = duration;
		this.finished = finished;
		this.program = program;
		this.smart = smart;
		this.manual = manual;
		this.weatherCondition = weatherCondition;
		this.weatherTempMax = weatherTempMax;
		this.weatherTempMin = weatherTempMin;
		this.durationArray = durationArray;
		this.finishedArray = finishedArray;
		this.programArray = programArray;
		this.smartArray = smartArray;
		this.manualArray = manualArray;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

	public int getProgram() {
		return program;
	}

	public void setProgram(int program) {
		this.program = program;
	}

	public int getSmart() {
		return smart;
	}

	public void setSmart(int smart) {
		this.smart = smart;
	}

	public int getManual() {
		return manual;
	}

	public void setManual(int manual) {
		this.manual = manual;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	public String getWeatherTempMax() {
		return weatherTempMax;
	}

	public void setWeatherTempMax(String weatherTempMax) {
		this.weatherTempMax = weatherTempMax;
	}

	public String getWeatherTempMin() {
		return weatherTempMin;
	}

	public void setWeatherTempMin(String weatherTempMin) {
		this.weatherTempMin = weatherTempMin;
	}

	public String getDurationArray() {
		return durationArray;
	}

	public void setDurationArray(String durationArray) {
		this.durationArray = durationArray;
	}

	public String getFinishedArray() {
		return finishedArray;
	}

	public void setFinishedArray(String finishedArray) {
		this.finishedArray = finishedArray;
	}

	public String getProgramArray() {
		return programArray;
	}

	public void setProgramArray(String programArray) {
		this.programArray = programArray;
	}

	public String getSmartArray() {
		return smartArray;
	}

	public void setSmartArray(String smartArray) {
		this.smartArray = smartArray;
	}

	public String getManualArray() {
		return manualArray;
	}

	public void setManualArray(String manualArray) {
		this.manualArray = manualArray;
	}

}
