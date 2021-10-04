package tech.pm.edu.domain.mapper;

import tech.pm.edu.domain.model.Player;
import tech.pm.edu.repository.entity.PlayerEntity;

public interface PlayerMapper {

  Player toPlayer(PlayerEntity playerEntity);

  PlayerEntity toPlayerEntity(Player pLayer);


}
