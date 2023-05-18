package com.system.secutity;

import cn.hutool.json.JSONUtil;
import com.system.common.Result;
import com.system.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();
        Result result = Result.success("登出成功");

        response.setHeader(jwtUtil.getHeader(), null);
        out.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
