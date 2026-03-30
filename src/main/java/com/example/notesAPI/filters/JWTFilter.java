package com.example.notesAPI.filters;

import com.example.notesAPI.model.MyUserDetails;
import com.example.notesAPI.service.JWTService;
import com.example.notesAPI.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email= null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);//jwt string starts at 7th index of header string
            email = jwtService.extractEmail(token);
        }

                            // returns the auth state of the current request, needed bc some other filter might auth the user (in the future), dont wanna auth twice
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //bc authConfig needs an instance of JWTFilter in order to be inititalized, but jwt needs a bean in authConfig to be initialized, it creates a circular redundancy if i try to call the UserDetails bean here
            MyUserDetails userDetails = (MyUserDetails) context.getBean(MyUserDetailsService.class).loadUserByUsername(email);//load by usernae=me is a misnomer, it loads the user by their email

            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Attaches additional request details (e.g., IP address, session ID) to the authentication token for debugging/logging/auditing
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //set user as authenticated if nothing fails
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
