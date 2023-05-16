package com.system.secutity;

import cn.hutool.json.JSONUtil;
import com.system.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ServletOutputStream out = response.getOutputStream();
        Result result = Result.fail("登录验证失败");
        out.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();

    }


}
