package tech.pm.edu.web.service.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import tech.pm.edu.domain.service.GameService;
import tech.pm.edu.web.exception.GameBlockedException;
import tech.pm.edu.web.exception.ProviderBlockedException;
import tech.pm.edu.web.mapper.GameDtoMapper;
import tech.pm.edu.web.model.GameDto;
import tech.pm.edu.web.model.LaunchUrlDto;
import tech.pm.edu.web.model.PlayerDetailsDto;
import tech.pm.edu.web.service.LaunchUrlService;
import tech.pm.edu.web.service.GameRepositoryPlayerApiService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class LaunchUrlServiceDefault implements LaunchUrlService {

  private final GameRepositoryPlayerApiService gameRepositoryPlayerApiService;
  private final GameService gameService;

  private final GameDtoMapper gameDtoMapper;

  public LaunchUrlServiceDefault(GameRepositoryPlayerApiService gameRepositoryPlayerApiService, GameService gameService, GameDtoMapper gameDtoMapper) {
    this.gameRepositoryPlayerApiService = gameRepositoryPlayerApiService;
    this.gameService = gameService;
    this.gameDtoMapper = gameDtoMapper;
  }

  @Override
  public LaunchUrlDto generateLaunchUrl(String lobbyGameId, String sessionKey) {
    PlayerDetailsDto playerDetailsDto = gameRepositoryPlayerApiService.getPlayerDetailsDto(sessionKey);
    GameDto gameDto = gameDtoMapper.toGameDto(gameService.getGame(lobbyGameId));

    if (gameDto.getIsBlocked()) {
      throw new GameBlockedException("Game is blocked");
    } else if (gameDto.getProvider().getIsBlocked()) {
      throw new ProviderBlockedException("Provider is blocked");
    }

    String launchUrl = launchUri(playerDetailsDto, gameDto, sessionKey);

    return new LaunchUrlDto(launchUrl);
  }

  @SneakyThrows
  private String encodeValues(String value) {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
  }

  private String launchUri(PlayerDetailsDto playerDetailsDto, GameDto gameDto, String sessionToken) {
    UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
    builder.scheme(gameDto.getProvider().getProviderUrl());
    builder.path("launch");
    builder.queryParam("game", encodeValues(gameDto.getExternalId()));
    builder.queryParam("sessionToken", sessionToken);
    builder.queryParam("displayName", encodeValues(playerDetailsDto.getDisplayName()));
    builder.queryParam("currency", playerDetailsDto.getCurrency());

    return builder.build().toString();
  }


}
