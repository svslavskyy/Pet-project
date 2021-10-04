package tech.pm.edu.lobby.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.pm.edu.lobby.web.model.LaunchUrlDto;
import tech.pm.edu.lobby.web.model.LobbyGameDto;

public interface LobbyGameDtoService {

  Page<LobbyGameDto> getAvailableLobbyGames(String sessionKey, Pageable pageable);

  LaunchUrlDto getLaunchUrl(String lobbyGameId, String sessionKey);


}
