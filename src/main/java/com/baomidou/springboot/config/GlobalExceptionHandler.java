package com.baomidou.springboot.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;

/**
* @Description:    通用 Api Controller 全局异常处理
* @Author:         LiHaitao
* @CreateDate:     2018/8/15 17:36
* @UpdateUser:
* @UpdateDate:     2018/8/25 14:19
* @UpdateRemark:   添加登录权限的异常信息
* @Version:        1.0.0
*/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义 REST 业务异常
     * @param e 异常类型
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResult<Object> handleBadRequest(Exception e) {
        /*
         * 业务逻辑异常
         */
        if (e instanceof ApiException) {
            IErrorCode errorCode = ((ApiException) e).getErrorCode();
            if (null != errorCode) {
                logger.debug("Rest request error, {}", errorCode.toString());
                return ApiResult.failed(errorCode);
            }
            logger.debug("Rest request error, {}", e.getMessage());
            return ApiResult.failed(e.getMessage());
        }

        /*
         * 参数校验异常
         */

        if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            if (null != bindingResult && bindingResult.hasErrors()) {
                List<Object> jsonList = new ArrayList<>();
                bindingResult.getFieldErrors().stream().forEach(fieldError -> {
                    Map<String, Object> jsonObject = new HashMap<>(2);
                    jsonObject.put("name", fieldError.getField());
                    jsonObject.put("msg", fieldError.getDefaultMessage());
                    jsonList.add(jsonObject);
                });
                return ApiResult.restResult(jsonList, ApiErrorCode.FAILED);
            }
        }
        Map<String, Object> jsonObject1 = new HashMap<>(2);
        List<Object> jsonList = new ArrayList<>();
        if (e instanceof UnauthenticatedException) {
            jsonObject1.put("code", "1000001");
            jsonObject1.put("msg", "token错误");
            return ApiResult.restResult(jsonList, ApiErrorCode.FAILED);
        } else if (e instanceof UnauthorizedException) {
            jsonObject1.put("code", "1000002");
            jsonObject1.put("msg", "用户无权限");
            return ApiResult.restResult(jsonList, ApiErrorCode.FAILED);
        } else {
            jsonObject1.put("code", "1000003");
            jsonObject1.put("msg", e.getMessage());
            return ApiResult.restResult(jsonList, ApiErrorCode.FAILED);
        }
    }
}
