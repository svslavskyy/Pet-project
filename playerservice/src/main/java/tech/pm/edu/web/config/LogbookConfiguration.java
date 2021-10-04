package tech.pm.edu.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;

import static org.zalando.logbook.json.JsonPathBodyFilters.jsonPath;

@Configuration
public class LogbookConfiguration {

  @Bean
  public Logbook logbook() {
    return Logbook.builder()
            .bodyFilter(jsonPath("$.password").replace("<secret>"))
            .bodyFilter(jsonPath("$.sessionKey").replace("<secret>"))
            .build();
  }


}
