package tech.pm.edu.lobby.web.service.api.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tech.pm.edu.lobby.web.model.LaunchUrlDto;
import tech.pm.edu.lobby.web.service.api.LobbyGameRepositoryApiService;

import java.net.URI;

@Service
public class LobbyGameRepositoryApiServiceDefault implements LobbyGameRepositoryApiService {

  private final RestTemplate restTemplate;
  private final URI gamesRepositoryUri;

  public LobbyGameRepositoryApiServiceDefault(RestTemplate restTemplate, URI gamesRepositoryUri) {
    this.restTemplate = restTemplate;
    this.gamesRepositoryUri = gamesRepositoryUri;
  }

  @Override
  public LaunchUrlDto getLaunchUrlDto(String lobbyGameId, String sessionKey) {
    URI uriWithParams = UriComponentsBuilder.fromUri(gamesRepositoryUri)
      .queryParam("lobbyGameId", lobbyGameId)
      .queryParam("sessionKey", sessionKey)
      .build().toUri();

    return restTemplate.getForEntity(uriWithParams, LaunchUrlDto.class).getBody();
  }


}
