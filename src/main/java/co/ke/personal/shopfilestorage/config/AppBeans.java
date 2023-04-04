package co.ke.personal.shopfilestorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Configuration
public class AppBeans {
    @Bean
    public WebClient webclient(){
        return WebClient.builder().build();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new UserHeaderInterceptor()));
        return restTemplate;
    }
}
