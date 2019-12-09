package com.ciy.device_center.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class AppInfo {

    private String applicationName;
    private int port;
    private String address;
    @JsonIgnore
    private ChannelHandlerContext ctx;

    public AppInfo(String applicationName, String address, int port, ChannelHandlerContext ctx) {
        this.applicationName = applicationName;
        this.address = address;
        this.port = port;
        this.ctx = ctx;
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

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AppInfo) {
            return getApplicationName().equals(((AppInfo) obj).getApplicationName());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return getApplicationName().hashCode();
    }
}
