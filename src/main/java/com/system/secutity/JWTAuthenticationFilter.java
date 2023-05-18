package com.system.secutity;

import cn.hutool.core.util.StrUtil;

import com.system.entity.User;
import com.system.service.UserService;
import com.system.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeSet;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JWT 校验过滤器运行--");
        String jwt = request.getHeader(jwtUtil.getHeader());
        if(StrUtil.isBlank(jwt)){
            chain.doFilter(request, response);
            return;
        }
        Claims claims = jwtUtil.getClaimsByToken(jwt);
        if(claims==null){
            throw new JwtException("token 解析异常");
        }
        if(jwtUtil.isTokenExpired(claims)){
            throw new JwtException("token 已过期");
        }
        String username = claims.getSubject();
        log.info("用户{}----正在访问",username);
        User user =  userService.getUserByUsername(username);
        // 参数1 用户名
        // 参数2 null
        // 参数3 详细的权限
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username,null,
                        // 查一下UsernamePasswordAuthenticationToken的文档
                        userDetailsService.getUserAuthority(user.getId()));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }
}
