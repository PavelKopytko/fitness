package by.it_academy.kopytko.controller.filter;

import by.it_academy.kopytko.util.JwtTokenUtil;
import by.it_academy.kopytko.service.UserDetailServiceCustom;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailServiceCustom userDetailService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtFilter(UserDetailServiceCustom userDetailService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailService = userDetailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!this.jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        //Получите идентификатор пользователя и установите его в контексте безопасности spring.
        //получает его из БД
        UserDetails userDetails = userDetailService
                .loadUserByUsername(this.jwtTokenUtil.getUsername(token));

        //создаем объект UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        //помещаем объект в контекст СС
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}