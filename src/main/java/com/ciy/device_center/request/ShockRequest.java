package com.ciy.device_center.request;

import javax.validation.constraints.NotBlank;

public class ShockRequest {
    @NotBlank(message = "设备码不能为空")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
