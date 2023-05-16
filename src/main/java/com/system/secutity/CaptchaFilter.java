package com.system.secutity;

import com.system.common.exception.CaptchaException;
import com.system.common.lang.Const;
import com.system.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {
    private final String loginURL = "/login";
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    LoginFailureHandler loginFailureHandler;
    private void validate(HttpServletRequest request) throws CaptchaException{
        String code = request.getParameter("code");
        String key = request.getParameter("key");
        if(StringUtils.isBlank(code) || StringUtils.isBlank(key)){
            log.info("验证码为空-{}",code);
            log.info("key为空-{}",key);
            throw new CaptchaException("验证码为空");
        }
        log.info("用户输入的验证码{}---redis读取出的验证码 = {}", code, redisUtil.hget(Const.CAPTCHA_KEY, key));
        if(!code.equals(redisUtil.hget(Const.CAPTCHA_KEY, key))){
            log.info("验证码错误{}",redisUtil.hget(Const.CAPTCHA_KEY, key));
            throw new CaptchaException("验证码错误");
        }
//        redisUtil.hdel(Const.CAPTCHA_KEY, key);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获得请求的url
       String uri =  request.getRequestURI();
       if(loginURL.equals(uri) && request.getMethod().equals("POST")){
            log.info("进入验证码过滤器,正在校验验证码");
            try{
                validate(request);
            }catch (CaptchaException e){
                log.info("验证码校验失败"+e.getMessage());
                loginFailureHandler.onAuthenticationFailure(request,response,e);
            }
       }
       // 验证成功，放行
       filterChain.doFilter(request,response);
    }
}
