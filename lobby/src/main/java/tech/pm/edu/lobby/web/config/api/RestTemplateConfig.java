package tech.pm.edu.lobby.web.config.api;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tech.pm.edu.lobby.web.exception.handler.RestTemplateResponseErrorHandler;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder, RestTemplateResponseErrorHandler errorHandler) {
    return builder.errorHandler(errorHandler).build();
  }


}
