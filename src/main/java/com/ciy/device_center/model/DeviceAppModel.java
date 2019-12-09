package com.ciy.device_center.model;

import com.ciy.device_center.proto.AppProto;
import io.netty.channel.ChannelHandlerContext;

public class DeviceAppModel {
    private String deviceCode;
    private String deviceName;
    private int deviceType;
    private String applicationName;
    private String address;
    private int port;
    // 别名
    private String alias;
    private ChannelHandlerContext ctx;

    public DeviceAppModel(AppProto.Info appInfo, ChannelHandlerContext ctx) {
        this.deviceCode = appInfo.getDeviceCode();
        this.deviceName = appInfo.getDeviceName();
        this.deviceType = appInfo.getDeviceType();
        this.applicationName = appInfo.getApplicationName();
        this.address = appInfo.getAddress();
        this.port = appInfo.getPort();
        this.ctx = ctx;
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
