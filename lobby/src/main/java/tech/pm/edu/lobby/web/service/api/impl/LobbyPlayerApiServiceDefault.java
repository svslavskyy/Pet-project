package tech.pm.edu.lobby.web.service.api.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tech.pm.edu.lobby.web.model.PlayerDetailsDto;
import tech.pm.edu.lobby.web.service.api.LobbyPlayerApiService;

import java.net.URI;

@Service
public class LobbyPlayerApiServiceDefault implements LobbyPlayerApiService {

  private final RestTemplate restTemplate;
  private final URI playerServiceUri;

  public LobbyPlayerApiServiceDefault(RestTemplate restTemplate, URI playerServiceUri) {
    this.restTemplate = restTemplate;
    this.playerServiceUri = playerServiceUri;
  }

  @Override
  public PlayerDetailsDto getPlayerDetailsDto(String sessionKey) {
    URI uriWithParams = UriComponentsBuilder.fromUri(playerServiceUri)
      .queryParam("sessionKey", sessionKey)
      .build().toUri();

    return restTemplate.getForEntity(uriWithParams, PlayerDetailsDto.class).getBody();
  }


}
