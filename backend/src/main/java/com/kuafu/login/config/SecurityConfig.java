package com.kuafu.login.config;

import com.kuafu.login.handle.AuthenticationEntryPointImpl;
import com.kuafu.login.handle.JwtAuthenticationTokenFilter;
import com.kuafu.login.handle.LogoutSuccessHandlerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.Map;


@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ConditionalOnProperty(prefix = "login", name = "enable")
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .csrf().disable()
                // 禁用HTTP响应标头
                .headers().cacheControl().disable().and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/doc.html").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js","/profile/**").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();

        // 添加Logout filter
        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

        // 添加JWT filter
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        Map<String, AuthenticationProvider> providerMap = applicationContext.getBeansOfType(AuthenticationProvider.class);
        for (Map.Entry<String, AuthenticationProvider> entry : providerMap.entrySet()) {
            log.info("=============== loading provider {}", entry.getKey());
            auth.authenticationProvider(entry.getValue());
        }
    }


    @Bean
    public HttpFirewall allowDoubleSlashFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }

}
