package com.irainbot.entity;

import java.io.Serializable;

/**
 * 区域信息
 */
public class ZoneInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String zoneId; // 区域id
	private String zoneName; // 区域名称
	private boolean zoneEnable; // 区域开关
	private String vegation; // 区域种类
	private String sunshine;
	private String slope;
	private String soil;

	private String masterZoneId;
	private boolean masterZoneEnable;

	private int duration; // 　总时间
	private int finished; // 已经完成时间

	private int program; // 计划初始设置值
	private int smart; // 根据天气智能设置值
	private int manual; // 手动设置值

	public ZoneInfo() {
	}

	public ZoneInfo(String zoneId, String zoneName, boolean zoneEnable,
			String vegation, String sunshine, String slope, String soil,
			String masterZoneId, boolean masterZoneEnable) {
		this.zoneId = zoneId;
		this.zoneName = zoneName;
		this.zoneEnable = zoneEnable;
		this.vegation = vegation;
		this.sunshine = sunshine;
		this.slope = slope;
		this.soil = soil;
		this.masterZoneId = masterZoneId;
		this.masterZoneEnable = masterZoneEnable;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public boolean isZoneEnable() {
		return zoneEnable;
	}

	public void setZoneEnable(boolean zoneEnable) {
		this.zoneEnable = zoneEnable;
	}

	public String getVegation() {
		return vegation;
	}

	public void setVegation(String vegation) {
		this.vegation = vegation;
	}

	public String getSunshine() {
		return sunshine;
	}

	public void setSunshine(String sunshine) {
		this.sunshine = sunshine;
	}

	public String getSlope() {
		return slope;
	}

	public void setSlope(String slope) {
		this.slope = slope;
	}

	public String getSoil() {
		return soil;
	}

	public void setSoil(String soil) {
		this.soil = soil;
	}

	public String getMasterZoneId() {
		return masterZoneId;
	}

	public void setMasterZoneId(String masterZoneId) {
		this.masterZoneId = masterZoneId;
	}

	public boolean isMasterZoneEnable() {
		return masterZoneEnable;
	}

	public void setMasterZoneEnable(boolean masterZoneEnable) {
		this.masterZoneEnable = masterZoneEnable;
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

}
