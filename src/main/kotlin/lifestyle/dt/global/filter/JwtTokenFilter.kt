package lifestyle.dt.global.filter

import lifestyle.dt.global.security.jwt.JwtGenerator
import lifestyle.dt.global.security.jwt.JwtParser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(
    private val jwtParser: JwtParser
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token: String? = jwtParser.resolveToken(request)
        if (!token.isNullOrBlank()) {
            val authentication = jwtParser.authentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

}