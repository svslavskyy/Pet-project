package tech.pm.edu.lobby.web.config.api.model;

import lombok.Data;

@Data
public abstract class UriProperties {

  private String scheme;
  private String host;
  private String port;
  private String path;


}
