package com.ciy.device_center.component;

import com.ciy.device_center.model.DeviceAppModel;

import java.util.List;

public interface IDeviceCenter {

    /**
     * 获取所有设备信息
     * @return
     */
    List<DeviceInfo> getDeviceGroup();

    /**
     * 添加一个app
     * @param deviceAppModel
     */
    void addAppInfo(DeviceAppModel deviceAppModel);

    /**
     * 移除一个app
     * @param deviceAppModel
     */
    void removeAppInfo(DeviceAppModel deviceAppModel);

    /**
     * 设置别名
     * @param deviceCode 设备码
     * @param alias 别名
     */
    boolean setAlias(String deviceCode, String alias);

    /**
     * 清除所有连接
     */
    void clear();
}
