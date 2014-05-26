package com.irainbot.entity;

/**
 * wifi信息
 */
public class WifiInfo {
	private String wifiName;
	private String mac;
	private String capability; // “flags like WPA-PSK-CCMP”
	private int frequency; // frequency in mhz
	private int level; // // signal level/strength

	public WifiInfo() {
	}

	public WifiInfo(String wifiName, String mac, String capability,
			int frequency, int level) {
		this.wifiName = wifiName;
		this.mac = mac;
		this.capability = capability;
		this.frequency = frequency;
		this.level = level;
	}

	public String getWifiName() {
		return wifiName;
	}

	public void setWifiName(String wifiName) {
		this.wifiName = wifiName;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCapability() {
		return capability;
	}

	public void setCapability(String capability) {
		this.capability = capability;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
