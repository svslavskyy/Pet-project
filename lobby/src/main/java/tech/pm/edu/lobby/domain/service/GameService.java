package tech.pm.edu.lobby.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.pm.edu.lobby.domain.model.Game;
import tech.pm.edu.lobby.domain.model.PlayerDetails;

public interface GameService {

  Page<Game> getAvailableLobbyGameIds(PlayerDetails playerDetails, Pageable pageable);

  boolean isLobbyGameExistsForPlayer(Game game, PlayerDetails playerDetails);

  Game createGame(Game game);

  Game updateGame(Game game);

  Game blockGame(String lobbyGameId);

  Game getGame(String lobbyGameId);


}
