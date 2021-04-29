package ru.thetenzou.tsoddservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import ru.thetenzou.tsoddservice.security.JwtTokenProvider;
import ru.thetenzou.tsoddservice.security.JwtAuthenticationEntryPoint;
import ru.thetenzou.tsoddservice.security.JwtConfigurer;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private static final String HELLO_ENDPOINT = "/api/tsodd/v1/hello";
    private static final String HELLO_USER_ENDPOINT = "/api/tsodd/v1/helloUser";
    private static final String HELLO_ADMIN_ENDPOINT = "/api/tsodd/v1/helloAdmin";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HELLO_ENDPOINT).permitAll()
            .antMatchers(HELLO_USER_ENDPOINT).hasRole("USER")
            .antMatchers(HELLO_ADMIN_ENDPOINT).hasRole("ADMIN")
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
