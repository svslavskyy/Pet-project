package tech.pm.edu.domain.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.mapper.GameMapper;
import tech.pm.edu.domain.mapper.ProviderMapper;
import tech.pm.edu.domain.model.Game;
import tech.pm.edu.repository.model.GameEntity;

@Component
public class GameMapperDefault implements GameMapper {

  private final ProviderMapper providerMapper;

  public GameMapperDefault(ProviderMapper providerMapper) {
    this.providerMapper = providerMapper;
  }

  @Override
  public Game toGame(GameEntity gameEntity) {
    if (gameEntity == null) {
      return null;
    }

    return Game.builder()
        .id(gameEntity.getId())
        .internalId(gameEntity.getInternalId())
        .externalId(gameEntity.getExternalId())
        .isBlocked(gameEntity.getIsBlocked())
        .provider(providerMapper.toProvider(gameEntity.getProvider()))
        .build();
  }

  @Override
  public GameEntity toGameEntity(Game game) {
    if (game == null) {
      return null;
    }

    GameEntity gameEntity = new GameEntity();
    gameEntity.setId(game.getId());
    gameEntity.setInternalId(game.getInternalId());
    gameEntity.setExternalId(game.getExternalId());
    gameEntity.setIsBlocked(game.getIsBlocked());
    gameEntity.setProvider(providerMapper.toProviderEntity(game.getProvider()));

    return gameEntity;
  }


}
