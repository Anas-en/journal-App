package com.anas.journalApp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//this does not use nowdays , as WebSecurityConfigurationAdpater Obselate (New techniquess)
//as discussed in SecurityConfig


@Configuration
@EnableMethodSecurity
public class SpringSecurity {

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
                            .requestMatchers("/admin/**").hasRole("ADMIN")
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
