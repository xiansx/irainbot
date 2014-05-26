package com.irainbot.entity;

/**
 * 设备信息
 */
public class DeviceInfo {
	private String deviceid;
	private String model;
	private String softwareVersion;
	private int numZones;
	private boolean status; // <online|disconnected>
	private String lastSeenTime; // last time this device was seen by server
									// alive
	private String timezone; // “America/Los_Angeles”, derived from zip code

	private double latitude;
	private double lontitude;
	private String deviceName;
	private String zipcode;

	private boolean isOwner;
	private String customName;

	private String rainbot;
	private String backup;
	private String rootfs;
	private String bootloader;
	private String os;

	public DeviceInfo() {
	}

	public DeviceInfo(String deviceid, String model, String softwareVersion,
			int numZones, boolean status, String lastSeenTime, String timezone,
			String deviceName, String zipcode, boolean isOwner,
			String customName) {
		super();
		this.deviceid = deviceid;
		this.model = model;
		this.softwareVersion = softwareVersion;
		this.numZones = numZones;
		this.status = status;
		this.lastSeenTime = lastSeenTime;
		this.timezone = timezone;
		this.deviceName = deviceName;
		this.zipcode = zipcode;
		this.isOwner = isOwner;
		this.customName = customName;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getRainbot() {
		return rainbot;
	}

	public void setRainbot(String rainbot) {
		this.rainbot = rainbot;
	}

	public String getBackup() {
		return backup;
	}

	public void setBackup(String backup) {
		this.backup = backup;
	}

	public String getRootfs() {
		return rootfs;
	}

	public void setRootfs(String rootfs) {
		this.rootfs = rootfs;
	}

	public String getBootloader() {
		return bootloader;
	}

	public void setBootloader(String bootloader) {
		this.bootloader = bootloader;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLontitude() {
		return lontitude;
	}

	public void setLontitude(double lontitude) {
		this.lontitude = lontitude;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public int getNumZones() {
		return numZones;
	}

	public void setNumZones(int numZones) {
		this.numZones = numZones;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getLastSeenTime() {
		return lastSeenTime;
	}

	public void setLastSeenTime(String lastSeenTime) {
		this.lastSeenTime = lastSeenTime;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
