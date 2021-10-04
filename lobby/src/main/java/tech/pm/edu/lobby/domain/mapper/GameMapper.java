package tech.pm.edu.lobby.domain.mapper;

import tech.pm.edu.lobby.domain.model.Game;
import tech.pm.edu.lobby.repository.entity.GameEntity;

public interface GameMapper {

  Game toGame(GameEntity gameEntity);

  Game toGameWithOnlyLobbyGameId(GameEntity gameEntity);

  GameEntity toGameEntity(Game game);


}
