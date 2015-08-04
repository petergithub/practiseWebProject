package org.peter.bean;

public class HeaderBean {

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HeaderBean [appKey=").append(appKey).append(", udid=").append(udid)
				.append(", os=").append(os).append(", osVersion=").append(osVersion)
				.append(", appVersion=").append(appVersion).append(", sourceId=").append(sourceId)
				.append(", ver=").append(ver).append(", userId=").append(userId).append(", token=")
				.append(token).append(", unique=").append(unique).append(", modelId=")
				.append(modelId).append(", modelName=").append(modelName).append(", region=")
				.append(region).append("]");
		return builder.toString();
	}

	private String appKey;// String Y 123456 服务器端分配的软件身份key
	private String udid;// String Y udid or imei 手机客户端的唯一标识
	private String os;// String Y Iphone os 操作系统名称
	private String osVersion;// String Y 5.0 操作系统版本
	private String appVersion;// String Y 1.0.0 APP版本
	private String sourceId;// String Y Yek_test 推广ID
	private String ver;// String Y 0.9 通讯协议版本
	private String userId;// String N 12345 用户ID
	private String token;// String N Cbaq4fxvb 登陆后得到的用户唯一性标识
	private String unique;// String N xvbvsfsgdsg 激活后得到的设备唯一性标识
	private String modelId;// String N xvbvsfsgdsg 激活后得到的设备唯一性标识
	private String modelName;// String N 机型名称
	private String region;
	
	public HeaderBean(String appKey, String udid, String os, String osVersion, String appVersion,
			String sourceId, String ver, String userId, String token, String unique,
			String modelId, String modelName, String region) {
		super();
		this.appKey = appKey;
		this.udid = udid;
		this.os = os;
		this.osVersion = osVersion;
		this.appVersion = appVersion;
		this.sourceId = sourceId;
		this.ver = ver;
		this.userId = userId;
		this.token = token;
		this.unique = unique;
		this.modelId = modelId;
		this.modelName = modelName;
		this.region = region;
	}

	public HeaderBean() {
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getModelId() {
		return modelId;
	}

	public Long getLongModelId() {
		try {
			return Long.parseLong(this.modelId);
		} catch (Exception e) {
		}
		return null;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
}