package tech.pm.edu.lobby.web.config.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.pm.edu.lobby.web.config.api.model.ApiUriProperties;

@Configuration
public class UriPropertiesConfig {

  @Bean(name = "gamesRepositoryUriProperties")
  public ApiUriProperties gamesRepositoryUriProperties() {
    return new ApiUriProperties();
  }

  @Bean(name = "playerServiceUriProperties")
  public ApiUriProperties playerServiceUriProperties() {
    return new ApiUriProperties();
  }


}
