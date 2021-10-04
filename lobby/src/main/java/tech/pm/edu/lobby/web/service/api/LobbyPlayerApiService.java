package tech.pm.edu.lobby.web.service.api;

import tech.pm.edu.lobby.web.model.PlayerDetailsDto;

public interface LobbyPlayerApiService {

  PlayerDetailsDto getPlayerDetailsDto(String sessionKey);


}
