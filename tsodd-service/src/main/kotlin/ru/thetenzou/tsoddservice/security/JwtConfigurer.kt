package ru.thetenzou.tsoddservice.security

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtConfigurer(
    private val jwtTokenProvider: JwtTokenProvider,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val jwtTokenFilter = JwtTokenFilter(jwtTokenProvider)
        builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}