package tech.pm.edu.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.pm.edu.domain.config.model.SessionDurationProperties;

@Configuration
public class SessionDurationConfig {

  @Bean
  @ConfigurationProperties(prefix = "session")
  public SessionDurationProperties sessionDurationProperties() {
    return new SessionDurationProperties();
  }


}
