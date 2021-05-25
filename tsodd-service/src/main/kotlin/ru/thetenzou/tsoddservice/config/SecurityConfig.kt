package ru.thetenzou.tsoddservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import ru.thetenzou.tsoddservice.security.JwtAuthenticationEntryPoint
import ru.thetenzou.tsoddservice.security.JwtConfigurer
import ru.thetenzou.tsoddservice.security.JwtTokenProvider

@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    companion object {
        private val HELLO_ENDPOINT = "/api/tsodd/v1/hello"
        private val HELLO_USER_ENDPOINT = "/api/tsodd/v1/helloUser"
        private val HELLO_ADMIN_ENDPOINT = "/api/tsodd/v1/helloAdmin"
        private val SECURE_ENDPOINT = "/api/tsodd/v1/**"
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HELLO_ENDPOINT).permitAll()
            .antMatchers(SECURE_ENDPOINT).hasRole("USER")
            .antMatchers(HELLO_USER_ENDPOINT).hasRole("USER")
            .antMatchers(HELLO_ADMIN_ENDPOINT).hasRole("ADMIN")
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .apply(JwtConfigurer(jwtTokenProvider))
    }
}