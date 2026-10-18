package com.anas.journalApp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //configure
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
            http
                    .csrf(csrf-> csrf.disable())

                    .authorizeHttpRequests(auth->auth
                            .requestMatchers("/journals/**","/user/**")
                            .authenticated()

                            .anyRequest()
                            .permitAll()

                    )

                    .httpBasic(Customizer.withDefaults())

                    .sessionManagement(session -> session
                            .sessionCreationPolicy(
                                    SessionCreationPolicy.STATELESS
                            )
                    );

            return http.build();
    }


}
