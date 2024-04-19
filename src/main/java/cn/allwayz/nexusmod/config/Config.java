package cn.allwayz.nexusmod.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author allwayz
 * @create 2024-04-16 23:12
 */
@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ProcessBuilder processBuilder(){
        return new ProcessBuilder();
    }
}
