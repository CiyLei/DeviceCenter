package com.ciy.device_center.component;

import com.ciy.device_center.model.DeviceAppModel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class DeviceCenter implements IDeviceCenter, InitializingBean {

    private List<DeviceInfo> deviceAppGroup = new ArrayList<>();
    private Map<String, String> aliasGroup = new HashMap<>();

    @Autowired
    private IAlias alias;

    @Override
    public void afterPropertiesSet() throws Exception {
        aliasGroup.putAll(alias.loadAlias());
    }

    @Override
    public List<DeviceInfo> getDeviceGroup() {
        return deviceAppGroup;
    }

    /**
     * 添加一个app
     *
     * @param deviceAppModel
     */
    public void addAppInfo(DeviceAppModel deviceAppModel) {
        // 如果设备未存在的话
        DeviceInfo device = findDevice(deviceAppModel.getDeviceCode());
        if (device == null) {
            DeviceInfo deviceInfo = new DeviceInfo(deviceAppModel.getDeviceCode(), deviceAppModel.getDeviceName(), deviceAppModel.getDeviceType(), getAlias(deviceAppModel.getDeviceCode()));
            AppInfo appInfo = new AppInfo(deviceAppModel.getApplicationName(), deviceAppModel.getAddress(), deviceAppModel.getPort(), deviceAppModel.getCtx());
            deviceInfo.getAppInfoList().add(appInfo);
            deviceAppGroup.add(deviceInfo);
        } else {
            // 如果设备已存在的话
            Set<AppInfo> appInfoList = device.getAppInfoList();
            AppInfo appInfo = new AppInfo(deviceAppModel.getApplicationName(), deviceAppModel.getAddress(), deviceAppModel.getPort(), deviceAppModel.getCtx());
            appInfoList.add(appInfo);
        }
    }

    /**
     * 根据设备码寻找设备
     *
     * @param deviceCode
     * @return
     */
    public DeviceInfo findDevice(String deviceCode) {
        for (DeviceInfo device : deviceAppGroup) {
            if (device.getDeviceCode().equals(deviceCode)) {
                return device;
            }
        }
        return null;
    }

    /**
     * 根据设备码获取设备别名
     *
     * @param deviceCode
     * @return
     */
    public String getAlias(String deviceCode) {
        if (aliasGroup.containsKey(deviceCode)) {
            return aliasGroup.get(deviceCode);
        }
        return "";
    }

    /**
     * 移除一个app
     *
     * @param deviceAppModel
     */
    @Override
    public void removeAppInfo(DeviceAppModel deviceAppModel) {
        DeviceInfo device = findDevice(deviceAppModel.getDeviceCode());
        if (device != null) {
            // 寻找app是否存在
            Set<AppInfo> appInfoList = device.getAppInfoList();
            AppInfo entry = null;
            for (AppInfo appInfo : appInfoList) {
                if (appInfo.getApplicationName().equals(deviceAppModel.getApplicationName())) {
                    entry = appInfo;
                    break;
                }
            }
            // 如果app存在，则移除
            if (entry != null) {
                appInfoList.remove(entry);
            }
            // 如果设备下面没有app了，就移除设备
            if (appInfoList.size() == 0) {
                deviceAppGroup.remove(device);
            }
        }
    }

    /**
     * 设置别名
     *
     * @param deviceCode 设备码
     * @param alias      别名
     */
    @Override
    public boolean setAlias(String deviceCode, String alias) {
        AtomicBoolean flag = new AtomicBoolean(false);
        if ("".equals(alias.trim())) {
            aliasGroup.remove(deviceCode);
        } else {
            deviceAppGroup.forEach(it -> {
                if (it.getDeviceCode().equals(deviceCode)) {
                    it.setAlias(alias);
                    flag.set(true);
                }
            });
            aliasGroup.put(deviceCode, alias);
        }
        this.alias.saveAlias(aliasGroup);
        return flag.get();
    }
}
