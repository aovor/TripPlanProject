package com.TripPlan.TripPlanProject.config;
import com.TripPlan.TripPlanProject.UserPackage.dto.UserResponseDTO;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("Header: {} = {}", headerName, request.getHeader(headerName));
        }


        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/users") ||
                requestURI.startsWith("/api/auth") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/error")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = resolveToken(request);
        log.info("Authorization Header Token: {}", token);

        if (token == null) {
            UserResponseDTO responseDTO = new UserResponseDTO("Fail", "JWT Token is missing");

            log.info("JWT Token is missing");

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 상태 코드 반환
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(responseDTO));
            return;
        }

        if (!jwtTokenProvider.validateToken(token)) {
            UserResponseDTO responseDTO = new UserResponseDTO("Fail", "JWT Token is invalid or expired");

            log.warn("JWT Token is invalid or expired");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 상태 코드 반환
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(responseDTO));
            return;
        }

        String userId = jwtTokenProvider.getUsername(token);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null, null);
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        log.info("Authorization header is missing or does not contain Bearer token");
        return null;
    }
}
