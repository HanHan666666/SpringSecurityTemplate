package com.system.config;

import com.system.secutity.CaptchaFilter;
import com.system.secutity.LoginFailureHandler;
import com.system.secutity.LoginSuccessHandler;
import com.system.secutity.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法级别的权限验证
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    //    校验验证码过滤器
    @Autowired
    CaptchaFilter captchaFilter;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder cryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    所有的路径都要经过spring security的过滤器
    //    定义一个白名单，放行一些请求
    public static final String[] WHITE_LIST = {
            "/login",
            "/register",
            "/index/captcha",
            "/favicon.ico"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .formLogin()
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .and()
                .authorizeRequests()
                .antMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //sessionCreationPolicy(SessionCreationPolicy.STATELESS);表示不创建session,不会使用session来保存用户的信息
                // ALWAYS 总是创建HttpSession
                // IF_REQUIRED Spring Security只会在需要时创建一个HttpSession
                // NEVER Spring Security不会创建HttpSession，但如果它已经存在，将可以使用HttpSession
                .and()
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
