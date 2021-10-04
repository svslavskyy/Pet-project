package tech.pm.edu.lobby.web.service.impl;

import org.springframework.stereotype.Service;
import tech.pm.edu.lobby.domain.service.GameService;
import tech.pm.edu.lobby.web.mapper.LobbyGameDtoMapper;
import tech.pm.edu.lobby.web.model.GameDto;
import tech.pm.edu.lobby.web.service.GameDtoService;

@Service
public class GameDtoServiceDefault implements GameDtoService {

  private final GameService gameService;

  private final LobbyGameDtoMapper lobbyGameDtoMapper;

  public GameDtoServiceDefault(GameService gameService, LobbyGameDtoMapper lobbyGameDtoMapper) {
    this.gameService = gameService;
    this.lobbyGameDtoMapper = lobbyGameDtoMapper;
  }

  @Override
  public GameDto createGameDto(GameDto gameDto) {

    return lobbyGameDtoMapper.toGameDto(gameService
      .createGame(lobbyGameDtoMapper.gameDtoToLobbyGame(gameDto)));
  }

  @Override
  public GameDto updateGameDto(GameDto gameDto) {

    return lobbyGameDtoMapper.toGameDto(gameService
      .updateGame(lobbyGameDtoMapper.gameDtoToLobbyGame(gameDto)));
  }

  @Override
  public GameDto blockGameDto(String lobbyGameId) {

    return lobbyGameDtoMapper.toGameDto(gameService.blockGame(lobbyGameId));
  }

  @Override
  public GameDto getGame(String lobbyGameId) {

    return lobbyGameDtoMapper.toGameDto(gameService.getGame(lobbyGameId));
  }


}
