package lifestyle.dt.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import lifestyle.dt.global.config.FilterConfig
import lifestyle.dt.global.security.handler.CustomAuthenticationEntryPoint
import lifestyle.dt.global.security.jwt.JwtGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsUtils

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val jwtGenerator: JwtGenerator
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .cors().and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .requestMatchers(RequestMatcher { request ->
                CorsUtils.isPreFlightRequest(request)
            }).permitAll()

            .mvcMatchers("/auth/**").permitAll()
            .anyRequest().denyAll()

            .and()
            .exceptionHandling()
            .authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper))

            .and()
            .apply(FilterConfig(jwtGenerator, objectMapper))

            .and()
            .build()
    }
}