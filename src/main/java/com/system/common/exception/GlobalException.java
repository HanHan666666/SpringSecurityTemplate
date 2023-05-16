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
        log.info("spring security permission denied---{}",e.getMessage());
        return Result.fail("permission denied");
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.info("spring security Argument error---{}",e.getMessage());
        return Result.fail("Argument error");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
        log.info("RuntimeException---{}",e.getMessage());
        return Result.fail("RuntimeException"+e.getMessage());
    }
}
