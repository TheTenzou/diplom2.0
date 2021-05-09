package ru.thetenzou.tsoddservice.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider {
    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    @Value("\${jwt.token.secret}")
    private var secret: String? = null

    @Value("\${jwt.token.expired}")
    private val validityInSeconds: Long = 0

    @PostConstruct
    private fun init() {
        secret = Base64.getEncoder().encodeToString(secret!!.toByteArray())
    }

    fun getAuthentication(token: String): Authentication {
        val authorities = getUserAuthority(token)
        return UsernamePasswordAuthenticationToken(getUserID(token), "", authorities)
    }

    fun getUserID(token: String?): String? {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
    }

    fun getUserAuthority(token: String): List<GrantedAuthority> {
        val claimsBody = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        val roles = claimsBody["roles"]
        // TODO fix geting roles from request
        val rolesList = roles as List<String>
        return rolesList.stream().map { role: String? ->
            SimpleGrantedAuthority(
                role
            )
        }.collect(Collectors.toList())
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun validateToken(token: String?): Boolean {
        try {
            val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return !claims.body.expiration.before(Date())
        } catch (e: JwtException) {
            logger.error("Jwt invalid or expired token")
        } catch (e: IllegalArgumentException) {
            logger.error("Jwt invalid or expired token")
        }
        return false
    }
}