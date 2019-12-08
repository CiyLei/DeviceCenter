package com.ciy.device_center.controller;

import com.ciy.device_center.model.BaseResult;

public class BaseController {

    protected <T> BaseResult<T> success(T data) {
        return new BaseResult<>(200, true, data, "");
    }

    protected <T> BaseResult<T> fail(int code, String message) {
        return new BaseResult<>(code, false, null, message);
    }
}
