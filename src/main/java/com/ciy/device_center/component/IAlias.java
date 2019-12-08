package com.ciy.device_center.component;

import java.util.Map;

public interface IAlias {

    /**
     * 保存别名
     * @param alias key: 设备id，value: 别名
     */
    void saveAlias(Map<String, String> alias);

    /**
     * 加载别名
     * @return
     */
    Map<String, String> loadAlias();
}
