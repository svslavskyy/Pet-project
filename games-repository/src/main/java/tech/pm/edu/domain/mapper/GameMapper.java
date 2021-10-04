package tech.pm.edu.domain.mapper;

import tech.pm.edu.domain.model.Game;
import tech.pm.edu.repository.model.GameEntity;

public interface GameMapper {

  Game toGame(GameEntity gameEntity);

  GameEntity toGameEntity(Game game);


}
