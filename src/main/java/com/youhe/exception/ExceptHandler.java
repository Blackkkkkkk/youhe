package com.youhe.exception;

import com.youhe.utils.R;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class ExceptHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptHandler.class);

    @ExceptionHandler(YuheOAException.class)
    public R handleKtException(YuheOAException e) {
        LOGGER.error("yuheOA异常：", e);
        if (e.getErrorCode() == null) {
            return R.error(e.getMsg());
        }
        return R.error(e.getErrorCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        LOGGER.error("yuheOA异常：", e);
        return R.error(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public R handleUnauthorizedException(UnauthorizedException e) {
        LOGGER.error("yuheOA异常：", e);
        return R.error(403, "权限不足，请联系管理员");
    }
}
