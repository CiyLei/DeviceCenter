package com.ciy.device_center.exception;

import com.ciy.device_center.model.BaseResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *  校验错误拦截处理
     *
     * @param ex 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleBindException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return new BaseResult<>(201, false, null, fieldError.getDefaultMessage());
    }

    /**
     * 参数类型转换错误
     *
     * @param ex 错误
     * @return 错误信息
     */
    @ExceptionHandler(BindException.class)
    public BaseResult handleBindException(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return new BaseResult<>(201, false, null, fieldError.getDefaultMessage());
    }
}
