package tech.pm.edu.web.config.api.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api")
public class ApiUriProperties {

  private PlayerServiceUriProperties playerService;


}
