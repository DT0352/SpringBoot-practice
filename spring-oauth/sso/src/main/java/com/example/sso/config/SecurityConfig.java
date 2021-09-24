package com.example.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // 角色继承 需要 ROLE_ 前缀
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

     @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //启动服务的时候建表，默认是false
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 表单登录
                .loginPage("/login") // 登录页  若没有设置登录接口 登录接口也同登录页一致
                .loginProcessingUrl("/login")// 登录接口 默认为login
                .usernameParameter("username")// 登录参数 默认为username 同前端的表单登录参数一致
                .passwordParameter("password")// 登录参数，默认为password
                .successForwardUrl("/index")// 登录成功跳转的页面
                .defaultSuccessUrl("/index", true) // TRUE 时，登录成功 跳转到index页面， 默认为FALSE 登录成功后跳转到上一个页面
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                }) // 登录成功 返回JSON
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                }) // 登录失败回调 登录失败返回JSON
                .and()
                .logout() // 注销登录的配置
                .logoutUrl("/logout") // 注销登录的接口  默认为logout
                .logoutSuccessUrl("/index") // 注销成功后调转的地址 前后端不分离
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                }) // 前后端分离 注销成功返回前端JSON
                .deleteCookies() // 清除cookie
                .and()
                .rememberMe() // 记住我功能
                .tokenRepository(persistentTokenRepository())// 用到的数据库
                .tokenValiditySeconds(3600) // 记住我的有效时间
                .userDetailsService(userDetailsService()) // 用户登录的数据库
                .and()
                .csrf().disable().exceptionHandling().authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                }) // 认证失败 （客户未登录，跳转到需要登录的页面）返回JSON给前端  前端做登录跳转
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                })// 权限不够 返回JSON给前端
                .and()
                .authorizeRequests() // 授权配置 按照顺序依次匹配 符合及退出
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .antMatchers("/").anonymous();
      // 其他页面需登录访问
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js");
    }
}
