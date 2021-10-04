package tech.pm.edu.lobby.web.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.lobby.domain.model.Game;
import tech.pm.edu.lobby.web.mapper.CountryDtoMapper;
import tech.pm.edu.lobby.web.mapper.CurrencyDtoMapper;
import tech.pm.edu.lobby.web.mapper.LobbyGameDtoMapper;
import tech.pm.edu.lobby.web.model.GameDto;
import tech.pm.edu.lobby.web.model.LobbyGameDto;

import java.util.stream.Collectors;

@Component
public class LobbyGameDtoMapperDefault implements LobbyGameDtoMapper {

  private final CurrencyDtoMapper currencyDtoMapper;
  private final CountryDtoMapper countryDtoMapper;

  public LobbyGameDtoMapperDefault(CurrencyDtoMapper currencyDtoMapper, CountryDtoMapper countryDtoMapper) {
    this.currencyDtoMapper = currencyDtoMapper;
    this.countryDtoMapper = countryDtoMapper;
  }

  @Override
  public LobbyGameDto toLobbyGameDto(Game game) {
    if (game == null) {
      return null;
    }

    return new LobbyGameDto(game.getLobbyGameId());
  }

  @Override
  public GameDto toGameDto(Game game) {
    if (game == null) {
      return null;
    }

    return new GameDto(
      game.getLobbyGameId(),
      game.getIsBlocked(),
      game.getCountries().stream().map(countryDtoMapper::toCountryDto).collect(Collectors.toSet()),
      game.getCurrencies().stream().map(currencyDtoMapper::toCurrencyDto).collect(Collectors.toSet())
    );
  }

  @Override
  public Game gameDtoToLobbyGame(GameDto gameDto) {
    if (gameDto == null) {
      return null;
    }

    return Game.builder()
      .lobbyGameId(gameDto.getLobbyGameId())
      .isBlocked(gameDto.getIsBlocked())
      .countries(gameDto.getCountries().stream().map(countryDtoMapper::countryDtoToCountry).collect(Collectors.toSet()))
      .currencies(gameDto.getCurrencies().stream().map(currencyDtoMapper::currencyDtoToCurrency).collect(Collectors.toSet()))
      .build();
  }

  @Override
  public Game lobbyGameDtoToLobbyGame(LobbyGameDto lobbyGameDto) {
    if (lobbyGameDto == null) {
      return null;
    }

    return Game.builder()
      .lobbyGameId(lobbyGameDto.getLobbyGameId())
      .build();
  }


}
