package tech.pm.edu.lobby.web.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.pm.edu.lobby.domain.model.Game;
import tech.pm.edu.lobby.domain.service.GameService;
import tech.pm.edu.lobby.web.exception.UnavailableGameException;
import tech.pm.edu.lobby.web.mapper.LobbyGameDtoMapper;
import tech.pm.edu.lobby.web.mapper.PlayerDetailsDtoMapper;
import tech.pm.edu.lobby.web.model.LaunchUrlDto;
import tech.pm.edu.lobby.web.model.LobbyGameDto;
import tech.pm.edu.lobby.web.model.PlayerDetailsDto;
import tech.pm.edu.lobby.web.service.LobbyGameDtoService;
import tech.pm.edu.lobby.web.service.api.LobbyGameRepositoryApiService;
import tech.pm.edu.lobby.web.service.api.LobbyPlayerApiService;

@Service
public class LobbyGameDtoServiceDefault implements LobbyGameDtoService {

  private final GameService gameService;
  private final LobbyPlayerApiService lobbyPlayerApiService;
  private final LobbyGameRepositoryApiService lobbyGameRepositoryApiService;

  private final PlayerDetailsDtoMapper playerDetailsDtoMapper;
  private final LobbyGameDtoMapper lobbyGameDtoMapper;

  public LobbyGameDtoServiceDefault(GameService gameService, LobbyPlayerApiService lobbyPlayerApiService,
                                    LobbyGameRepositoryApiService lobbyGameRepositoryApiService,
                                    PlayerDetailsDtoMapper playerDetailsDtoMapper, LobbyGameDtoMapper lobbyGameDtoMapper) {
    this.gameService = gameService;
    this.lobbyPlayerApiService = lobbyPlayerApiService;
    this.lobbyGameRepositoryApiService = lobbyGameRepositoryApiService;
    this.playerDetailsDtoMapper = playerDetailsDtoMapper;
    this.lobbyGameDtoMapper = lobbyGameDtoMapper;
  }

  @Override
  public Page<LobbyGameDto> getAvailableLobbyGames(String sessionKey, Pageable pageable) {
    PlayerDetailsDto playerDetailsDto = lobbyPlayerApiService.getPlayerDetailsDto(sessionKey);

    Page<Game> lobbyGames = gameService.getAvailableLobbyGameIds(playerDetailsDtoMapper.toPlayerDetails(playerDetailsDto), pageable);

    return lobbyGames.map(lobbyGameDtoMapper::toLobbyGameDto);
  }

  @Override
  public LaunchUrlDto getLaunchUrl(String lobbyGameId, String sessionKey) {
    PlayerDetailsDto playerDetailsDto = lobbyPlayerApiService.getPlayerDetailsDto(sessionKey);
    if (
      gameService.isLobbyGameExistsForPlayer(lobbyGameDtoMapper.lobbyGameDtoToLobbyGame(new LobbyGameDto(lobbyGameId)),
        playerDetailsDtoMapper.toPlayerDetails(playerDetailsDto))
    ) {
      return lobbyGameRepositoryApiService.getLaunchUrlDto(lobbyGameId, sessionKey);
    } else {
      throw new UnavailableGameException("This game is unavailable for you!");
    }
  }


}
