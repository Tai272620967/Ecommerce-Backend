package vn.hoidanit.jobhunter.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        // Don't ignore null values - include all fields
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return objectMapper;
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

