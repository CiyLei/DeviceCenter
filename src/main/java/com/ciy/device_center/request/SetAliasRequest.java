package com.ciy.device_center.request;

import javax.validation.constraints.NotBlank;

public class SetAliasRequest {
    @NotBlank(message = "设备码不能为空")
    private String code;
    @NotBlank(message = "别名不能为空")
    private String alias;

    public String getCode() {
        return code;
    }

    public String getAlias() {
        return alias;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
