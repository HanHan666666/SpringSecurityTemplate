package com.system.secutity;

import cn.hutool.json.JSONUtil;
import com.system.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
/**
 * 这个类的方法不会执行，因为在全局异常里面已经处理了，
 * */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();
        log.info("--用户权限不足--");
        response.setStatus(403);
        Result result = Result.fail("权限不足");
        out.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();

    }
}
