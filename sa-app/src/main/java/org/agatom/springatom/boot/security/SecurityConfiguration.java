package org.agatom.springatom.boot.security;

import org.agatom.springatom.boot.security.core.AjaxAuthenticationFailureHandler;
import org.agatom.springatom.boot.security.core.AjaxAuthenticationSuccessHandler;
import org.agatom.springatom.boot.security.core.AjaxLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-28</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Order
@Configuration
@ComponentScan(
        basePackages = "org.agatom.springatom.boot.security.impl"
)
@EnableWebSecurity
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration
        extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder                  passwordEncoder;
    @Autowired
    private LoggingUserDetailsService        loggingUserDetailsService;
    @Autowired
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
    @Autowired
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
    @Autowired
    private AjaxLogoutHandler                ajaxLogoutHandler;
    @Autowired
    private AuthenticationEntryPoint         authenticationEntryPoint;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true)
                .userDetailsService(this.loggingUserDetailsService)
                .passwordEncoder(this.passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return this.loggingUserDetailsService;
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.exceptionHandling()
                .accessDeniedPage("/app/403")
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and();

        http = http.authorizeRequests()
                .antMatchers("/app/**").permitAll()
                .antMatchers("/login**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and();

//        http = http.requiresChannel()
//                .anyRequest().requiresSecure()
//                .and();

        http.formLogin()
                .loginProcessingUrl("/app/authentication")
                .successHandler(this.ajaxAuthenticationSuccessHandler)
                .failureHandler(this.ajaxAuthenticationFailureHandler)
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll()
                .and();

        http = http.logout()
                .logoutUrl("/app/logout")
                .logoutSuccessHandler(this.ajaxLogoutHandler)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and();

        http.csrf().disable();

//        http.sessionManagement()
//                .invalidSessionUrl("/app/session/invalid")
//                .maximumSessions(1);

    }

}
