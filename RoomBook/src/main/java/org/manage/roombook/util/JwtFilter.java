package org.manage.roombook.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.manage.roombook.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        try {
            Claims claims = jwtUtil.parseToken(token);
            String userId = claims.getSubject();
            String name = claims.get("name", String.class);
            Boolean isAdmin = claims.get("isAdmin", Boolean.class);

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (isAdmin != null && isAdmin) {
                authorities.add(new SimpleGrantedAuthority("admin"));
            } else {
                authorities.add(new SimpleGrantedAuthority("user"));
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            name != null ? name : userId,
                            null,
                            authorities
                    );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            request.setAttribute("claims", claims);
        } catch (ExpiredJwtException e) {
            response.setStatus(401);
            response.setContentType("application/json; charset=utf-8");
            Result<String> result = Result.error(401, "Token expired");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        } catch (JwtException e) {
            response.setStatus(401);
            response.setContentType("application/json; charset=utf-8");
            Result<String> result = Result.error(401, "Token invalid");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return;
        }

        filterChain.doFilter(request, response);

    }
}
