package tech.pm.edu.lobby.web.service;

import tech.pm.edu.lobby.web.model.GameDto;

public interface GameDtoService {

  GameDto createGameDto(GameDto gameDto);

  GameDto updateGameDto(GameDto gameDto);

  GameDto blockGameDto(String lobbyGameId);

  GameDto getGame(String lobbyGameId);


}
