package com.system.config;

import com.system.secutity.*;

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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法级别的权限验证
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    //校验验证码过滤器
    @Autowired
    CaptchaFilter captchaFilter;
    // 校验用户信息
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    //jwt验证失败的处理器
    @Autowired
    AuthenticationEntryPoint authenticcationEntryPoint;
    @Autowired
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    // 登出成功
    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // 创建JWT过滤器对象
    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(authenticationManager());
    }

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
            "/favicon.ico",
            "/film/**",
            "/system/**",
            "/arrangement/**",
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .formLogin() // 表单登录,
                .failureHandler(loginFailureHandler)// 登陆失败
                .successHandler(loginSuccessHandler)//登陆成功
                .and()
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler) // 登出成功

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
                .exceptionHandling()// 异常处理器，一旦产生异常，就会执行下面的方法
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(authenticcationEntryPoint)
                .and()
                .addFilter(jwtAuthenticationFilter()) //配置jwt验证过滤器
                // 在springSecurity验证用户名密码过滤器执行之前，执行我们自定义的captchaFilter验证码过滤器
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
