package jp.gr.java_conf.stardiopside.ark.web.config;

import jp.gr.java_conf.stardiopside.ark.data.repository.UserRepository;
import jp.gr.java_conf.stardiopside.ark.service.UserService;
import jp.gr.java_conf.stardiopside.ark.service.userdetails.BeforeLoginUserDetailsChecker;
import jp.gr.java_conf.stardiopside.ark.service.userdetails.LoginUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
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

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final MessageSourceAccessor messages;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserService userService,
                             UserRepository userRepository, MessageSourceAccessor messages) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.messages = messages;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(registry -> registry
                        .antMatchers("/favicon.ico").permitAll()
                        .antMatchers("/webjars/**").permitAll()
                        .antMatchers("/static/**").permitAll()
                        .antMatchers("/authentication/**").permitAll()
                        .antMatchers("/anonymous/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/captcha.jpg").permitAll()
                        .anyRequest().authenticated())
                .formLogin(config -> config
                        .permitAll()
                        .loginPage("/authentication/create")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .failureForwardUrl("/authentication/failure"))
                .logout(config -> config
                        .permitAll()
                        .logoutSuccessUrl("/authentication/logout"));
    }

    @Bean
    public UserDetailsService loginUserDetailsService() {
        return new LoginUserDetailsService(userRepository);
    }

    @Bean
    public UserDetailsChecker preAuthenticationChecks() {
        return new BeforeLoginUserDetailsChecker(userService, messages);
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
