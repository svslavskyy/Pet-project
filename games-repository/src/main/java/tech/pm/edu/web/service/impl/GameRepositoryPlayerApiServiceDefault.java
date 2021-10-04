package tech.pm.edu.web.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tech.pm.edu.web.model.PlayerDetailsDto;
import tech.pm.edu.web.service.GameRepositoryPlayerApiService;

import java.net.URI;

@Service
public class GameRepositoryPlayerApiServiceDefault implements GameRepositoryPlayerApiService {

  private final RestTemplate restTemplate;
  
  private final URI playerServiceUri;

  public GameRepositoryPlayerApiServiceDefault(RestTemplate restTemplate, URI playerServiceUri) {
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
