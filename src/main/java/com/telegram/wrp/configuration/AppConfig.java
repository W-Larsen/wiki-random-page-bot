package com.telegram.wrp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Application configuration.
 *
 * @author Valentyn Korniienko
 */
@Configuration
public class AppConfig {

    /**
     * Gets rest template bean.
     *
     * @return rest template
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
