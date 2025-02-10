package com.example.AirBnb.Security;

import com.example.AirBnb.Entities.User;
import com.example.AirBnb.Services.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

   private final JwtService jwtService;
   private final UserService userService;

   @Autowired
   @Qualifier("handlerExceptionResolver")
   private HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
        final String requestTokenHeader=request.getHeader("Authorization");
        if(requestTokenHeader ==null || !requestTokenHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        // Extract and trim the token, Bearer-7
        String token = requestTokenHeader.substring(7).trim(); // Remove "Bearer " and trim any whitespace
        Long userId = jwtService.getUserIdFromToken(token);

        //checking user exists in the database or not
        //SecurityContextHolder.getContext().getAuthentication()==null we are adding this because, once we set the
        // context still there are chances that we come back and hit this, so no need to check again
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.getUserById(userId);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            //adding few more details to authentication token, not necessary
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //passing the request to the next filter
        filterChain.doFilter(request, response);
     }catch(JwtException ex){
             handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }
}
