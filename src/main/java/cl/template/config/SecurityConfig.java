package cl.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* COMMENT FOR TESTING **/
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/oauth2/**", "/health").permitAll() // Permite acceso a endpoints OAuth2
                                .anyRequest().authenticated() // Requiere autenticación para otras URL's
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/oauth2/authorization/github") // Redirige a GitHub para la autenticación
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // URL para cerrar sesión
                                .logoutSuccessUrl("/") // Redirige después de cerrar sesión
                                .invalidateHttpSession(true) // Invalida la sesión HTTP
                                .clearAuthentication(true) // Limpia la autenticación
                                .deleteCookies("JSESSIONID") // Borra la cookie JSESSIONID
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}

