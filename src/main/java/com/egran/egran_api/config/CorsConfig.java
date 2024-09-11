package com.egran.egran_api.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Specify the domain of your frontend explicitly instead of "*"
        config.addAllowedOrigin("https://main.d14g9pqq90b83n.amplifyapp.com/");  // Amplify domain

        // Allow credentials if required (cookies, auth headers, etc.)
        config.setAllowCredentials(true);

        // Allow all headers and methods
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Register the configuration for all paths
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
