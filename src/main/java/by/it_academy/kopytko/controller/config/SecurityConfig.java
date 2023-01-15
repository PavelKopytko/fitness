package by.it_academy.kopytko.controller.config;

import by.it_academy.kopytko.controller.filter.JwtFilter;
import by.it_academy.kopytko.util.serealization.LocalDateTimeDeserializer;
import by.it_academy.kopytko.util.serealization.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter filter;


    public SecurityConfig(JwtFilter filter) {
        this.filter = filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        return module;

    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Включает CORS и отключает CSRF
        http = http.cors().and().csrf().disable();

        //настройка для сессии
        //SessionCreationPolicy.STATELESS - будет управлять сессией юзера в системе спринг секюрити.
        //Так как я буду авторизировать пользователя по токену, мне не нужно создавать и хранить для него сессию
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Обработка запросов неавторизованных ?
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/product").permitAll()
                .antMatchers("/api/v1/users/registration").permitAll()
                .antMatchers("/api/v1/users/login").permitAll()
                .antMatchers("/api/v1/users/activate/**").permitAll()
                .antMatchers("/api/v1/users/me").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/users/**").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/audit/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }

}
