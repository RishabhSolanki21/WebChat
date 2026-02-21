package com.example.WebChat.Security;

import com.example.WebChat.CustomException.CustomLoginException;
import com.example.WebChat.Service.JwtClaims;
import com.example.WebChat.Service.MyUserDtails;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    @Autowired
    private JwtClaims jwtClaims;

    @Autowired
    private ApplicationContext context;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        String method = request.getMethod();
        System.out.println("════════════════════════════════════");
        System.out.println("🔍 JWT Filter Check");
        System.out.println("📍 Method: " + method);
        System.out.println("📍 Path: " + path);

        boolean shouldSkip = path.startsWith("/ws") ||
                path.equals("/login") ||
                path.equals("/register") ;

        System.out.println("❓ Skip filter: " + shouldSkip);
        System.out.println("════════════════════════════════════");
        return shouldSkip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            String header = request.getHeader("Authorization");
            System.out.println("header="+header);
            String username = null;
            String jwt = null;
            if (header != null && header.startsWith("Bearer ")) {
                jwt = header.substring(7);
                username=jwtClaims.getUsername(jwt);
            }
            System.out.println("jwt token ="+jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails details = context.getBean(MyUserDtails.class).loadUserByUsername(username);
                if (jwtClaims.isValid(jwt)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(details, jwt, details.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (JwtException e){
            log.warn("Jwt validation failed {}",e.getMessage());
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request,response,new BadCredentialsException("Invalid JWT"));
            return;
        }
        filterChain.doFilter(request, response);

    }
}
