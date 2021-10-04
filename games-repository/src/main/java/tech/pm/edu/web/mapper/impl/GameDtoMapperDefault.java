package tech.pm.edu.web.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.model.Game;
import tech.pm.edu.web.mapper.GameDtoMapper;
import tech.pm.edu.web.mapper.ProviderDtoMapper;
import tech.pm.edu.web.model.GameDto;

@Component
public class GameDtoMapperDefault implements GameDtoMapper {

  private final ProviderDtoMapper providerDtoMapper;

  public GameDtoMapperDefault(ProviderDtoMapper providerDtoMapper) {
    this.providerDtoMapper = providerDtoMapper;
  }

  @Override
  public Game gameDtoToGame(GameDto gameDto) {
    if (gameDto == null) {
      return null;
    }

    return Game.builder()
        .id(null)
        .internalId(gameDto.getInternalId())
        .externalId(gameDto.getExternalId())
        .isBlocked(gameDto.getIsBlocked())
        .provider(providerDtoMapper.providerDtoToProvider(gameDto.getProvider()))
        .build();
  }

  @Override
  public GameDto toGameDto(Game game) {
    if (game == null) {
      return null;
    }

    return new GameDto(
      game.getInternalId(),
      game.getExternalId(),
      game.getIsBlocked(),
      providerDtoMapper.toProviderDto(game.getProvider())
    );
  }


}
