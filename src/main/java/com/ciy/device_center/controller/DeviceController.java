package com.ciy.device_center.controller;

import com.ciy.device_center.DCProtoHeader;
import com.ciy.device_center.component.AppInfo;
import com.ciy.device_center.component.DeviceInfo;
import com.ciy.device_center.component.IDeviceCenter;
import com.ciy.device_center.handler.DCActionHandler;
import com.ciy.device_center.model.BaseResult;
import com.ciy.device_center.request.SetAliasRequest;
import com.ciy.device_center.request.ShockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class DeviceController extends BaseController {

    @Autowired
    IDeviceCenter deviceCenter;

    @GetMapping("/device")
    BaseResult<List<DeviceInfo>> getDeviceGroup() {
        return success(deviceCenter.getDeviceGroup());
    }

    @GetMapping("/setAlias")
    BaseResult<Boolean> setAlias(@Validated SetAliasRequest setAliasRequest) {
        if (deviceCenter.setAlias(setAliasRequest.getCode(), setAliasRequest.getAlias())) {
            return success(true);
        }
        return fail(201, "找不到设备");
    }

    @GetMapping("/shock")
    BaseResult<Boolean> shock(@Validated ShockRequest shockRequest) {
        AtomicBoolean flag = new AtomicBoolean(false);
        deviceCenter.getDeviceGroup().forEach(it -> {
            if (it.getDeviceCode().equals(shockRequest.getCode())) {
                AppInfo appInfo = null;
                if (it.getAppInfoList().size() == 1) {
                    appInfo = it.getAppInfoList().iterator().next();
                } else if (it.getAppInfoList().size() > 1) {
                    // 随机选择一个app进行振动
                    int r = new Random().nextInt(it.getAppInfoList().size());
                    int i = 0;
                    while (it.getAppInfoList().iterator().hasNext()) {
                        AppInfo next = it.getAppInfoList().iterator().next();
                        if (r == i) {
                            appInfo = next;
                            break;
                        }
                        i++;
                    }
                }
                if (appInfo != null) {
                    // 发送振动命令
                    appInfo.getCtx().writeAndFlush(new DCProtoHeader(DCActionHandler.SEND_SHOCK_CMDID, 0, new byte[]{1}));
                    flag.set(true);
                }
            }
        });
        if (flag.get()) {
            return success(true);
        }
        return fail(201, "找不到设备");
    }

    @GetMapping("clear")
    BaseResult<Boolean> clear() {
        deviceCenter.clear();
        return success(true);
    }
}
