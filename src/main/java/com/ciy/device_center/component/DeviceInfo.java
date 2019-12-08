package com.ciy.device_center.component;

import java.util.LinkedHashSet;
import java.util.Set;


public class DeviceInfo {
    private String deviceCode;
    private String deviceName;
    private int deviceType;
    // 别名
    private String alias;
    private Set<AppInfo> appInfoList;

    public DeviceInfo(String deviceCode, String deviceName, int deviceType, String alias) {
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.alias = alias;
        appInfoList = new LinkedHashSet<>();
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Set<AppInfo> getAppInfoList() {
        return appInfoList;
    }
}
