package com.system.common.exception;

import com.system.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {
//    AccessDeniedException accessDeniedException;
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result handler(AccessDeniedException e){
        log.info("spring security 没有权限---{}",e.getMessage());
        return Result.fail("权限不足");
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.info("spring security 参数错误---{}",e.getMessage());
        return Result.fail("接口参数异常");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
        log.info("运行时异常---{}",e.getMessage());
        return Result.fail("运行时错误"+e.getMessage());
    }
}
