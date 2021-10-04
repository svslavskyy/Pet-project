package tech.pm.edu.lobby.web.config.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;
import tech.pm.edu.lobby.web.config.api.model.ApiUriProperties;
import tech.pm.edu.lobby.web.config.api.model.GamesRepositoryUriProperties;
import tech.pm.edu.lobby.web.config.api.model.PlayerServiceUriProperties;

import java.net.URI;

@Configuration
public class UriConfig {

  @Bean(name = "gamesRepositoryUri")
  public URI gamesRepositoryUri(ApiUriProperties gamesRepositoryUriProperties) {
    GamesRepositoryUriProperties uriProperties = gamesRepositoryUriProperties.getGamesRepository();

    String scheme = uriProperties.getScheme();
    String host = uriProperties.getHost();
    String port = uriProperties.getPort();
    String path = uriProperties.getPath();

    return getUriComponentsBuilder(scheme, host, port, path);
  }

  @Bean(name = "playerServiceUri")
  public URI playerServiceUri(ApiUriProperties playerServiceUriProperties) {
    PlayerServiceUriProperties uriProperties = playerServiceUriProperties.getPlayerService();

    String scheme = uriProperties.getScheme();
    String host = uriProperties.getHost();
    String port = uriProperties.getPort();
    String path = uriProperties.getPath();

    return getUriComponentsBuilder(scheme, host, port, path);
  }

  private URI getUriComponentsBuilder(String scheme, String host, String port, String path) {
    UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
    builder.scheme(scheme);
    builder.host(host);
    if (!port.isEmpty()) {
      builder.port(port);
    }
    builder.path(path);

    return builder.build().toUri();
  }


}
