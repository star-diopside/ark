package jp.gr.java_conf.stardiopside.ark.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import jp.gr.java_conf.stardiopside.ark.service.userdetails.BeforeLoginUserDetailsChecker;
import jp.gr.java_conf.stardiopside.ark.service.userdetails.LoginUserDetailsService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/authentication/**").permitAll()
                .antMatchers("/anonymous/**").permitAll()
                .antMatchers(HttpMethod.GET, "/captcha.jpg").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin().permitAll()
                .loginPage("/authentication/create")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureForwardUrl("/authentication/failure")
            .and()
                .logout().permitAll()
                .logoutSuccessUrl("/authentication/logout");
    }

    @Bean
    public UserDetailsService loginUserDetailsService() {
        LoginUserDetailsService service = new LoginUserDetailsService();
        service.setDataSource(dataSource);
        return service;
    }

    @Bean
    public UserDetailsChecker preAuthenticationChecks() {
        return new BeforeLoginUserDetailsChecker();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(loginUserDetailsService());
        provider.setPasswordEncoder(passwordEncoder);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPreAuthenticationChecks(preAuthenticationChecks());
        return provider;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
