package tech.pm.edu.lobby.web.mapper;

import tech.pm.edu.lobby.domain.model.Game;
import tech.pm.edu.lobby.web.model.GameDto;
import tech.pm.edu.lobby.web.model.LobbyGameDto;

public interface LobbyGameDtoMapper {

  LobbyGameDto toLobbyGameDto(Game game);

  GameDto toGameDto(Game game);

  Game gameDtoToLobbyGame(GameDto gameDto);

  Game lobbyGameDtoToLobbyGame(LobbyGameDto lobbyGameDto);


}
