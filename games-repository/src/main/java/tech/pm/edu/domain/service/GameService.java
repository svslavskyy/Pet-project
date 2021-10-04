package tech.pm.edu.domain.service;

import tech.pm.edu.domain.model.Game;

public interface GameService {

  Game getGame(String internalId);
  Game createGame(Game game);
  Game updateGame(Game game);
  Game deleteGame(String internalId);


}
