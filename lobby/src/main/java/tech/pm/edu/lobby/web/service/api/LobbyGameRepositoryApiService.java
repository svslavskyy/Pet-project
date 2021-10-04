package tech.pm.edu.lobby.web.service.api;

import tech.pm.edu.lobby.web.model.LaunchUrlDto;

public interface LobbyGameRepositoryApiService {

  LaunchUrlDto getLaunchUrlDto(String lobbyGameId, String sessionKey);


}
