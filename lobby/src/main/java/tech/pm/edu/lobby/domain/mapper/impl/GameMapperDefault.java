package tech.pm.edu.lobby.domain.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.lobby.domain.mapper.CountryMapper;
import tech.pm.edu.lobby.domain.mapper.CurrencyMapper;
import tech.pm.edu.lobby.domain.mapper.GameMapper;
import tech.pm.edu.lobby.domain.model.Game;
import tech.pm.edu.lobby.repository.entity.GameEntity;

import java.util.stream.Collectors;

@Component
public class GameMapperDefault implements GameMapper {

  private final CountryMapper countryMapper;
  private final CurrencyMapper currencyMapper;

  public GameMapperDefault(CountryMapper countryMapper, CurrencyMapper currencyMapper) {
    this.countryMapper = countryMapper;
    this.currencyMapper = currencyMapper;
  }

  @Override
  public Game toGame(GameEntity gameEntity) {
    if (gameEntity == null) {
      return null;
    }

    return Game.builder()
      .id(gameEntity.getId())
      .lobbyGameId(gameEntity.getLobbyGameId())
      .isBlocked(gameEntity.getIsBlocked())
      .countries(gameEntity.getCountries().stream().map(countryMapper::toCountry).collect(Collectors.toSet()))
      .currencies(gameEntity.getCurrencies().stream().map(currencyMapper::toCurrency).collect(Collectors.toSet()))
      .build();
  }

  @Override
  public Game toGameWithOnlyLobbyGameId(GameEntity gameEntity) {
    if (gameEntity == null) {
      return null;
    }

    return Game.builder().lobbyGameId(gameEntity.getLobbyGameId()).build();
  }

  @Override
  public GameEntity toGameEntity(Game game) {
    if (game == null) {
      return null;
    }

    GameEntity gameEntity = new GameEntity();
    gameEntity.setId(game.getId());
    gameEntity.setLobbyGameId(game.getLobbyGameId());
    gameEntity.setIsBlocked(game.getIsBlocked());
    gameEntity.getCountries().addAll(game.getCountries().stream().map(countryMapper::toCountryEntity).collect(Collectors.toSet()));
    gameEntity.getCurrencies().addAll(game.getCurrencies().stream().map(currencyMapper::toCurrencyEntity).collect(Collectors.toSet()));

    return gameEntity;
  }


}
