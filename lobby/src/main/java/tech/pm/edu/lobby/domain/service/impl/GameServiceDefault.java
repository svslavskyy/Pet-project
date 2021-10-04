package tech.pm.edu.lobby.domain.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.pm.edu.lobby.domain.mapper.CountryMapper;
import tech.pm.edu.lobby.domain.mapper.CurrencyMapper;
import tech.pm.edu.lobby.domain.mapper.GameMapper;
import tech.pm.edu.lobby.domain.model.Country;
import tech.pm.edu.lobby.domain.model.Currency;
import tech.pm.edu.lobby.domain.model.Game;
import tech.pm.edu.lobby.domain.model.PlayerDetails;
import tech.pm.edu.lobby.domain.service.GameService;
import tech.pm.edu.lobby.repository.CountryRepository;
import tech.pm.edu.lobby.repository.CurrencyRepository;
import tech.pm.edu.lobby.repository.GameRepository;
import tech.pm.edu.lobby.repository.entity.GameEntity;
import tech.pm.edu.lobby.web.exception.EntityNotFoundException;
import tech.pm.edu.lobby.web.exception.GameAlreadyExistsException;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceDefault implements GameService {

  private final GameRepository gameRepository;
  private final CountryRepository countryRepository;
  private final CurrencyRepository currencyRepository;

  private final GameMapper gameMapper;
  private final CountryMapper countryMapper;
  private final CurrencyMapper currencyMapper;

  public GameServiceDefault(GameRepository gameRepository, CountryRepository countryRepository,
                            CurrencyRepository currencyRepository, GameMapper gameMapper,
                            CountryMapper countryMapper, CurrencyMapper currencyMapper) {
    this.gameRepository = gameRepository;
    this.countryRepository = countryRepository;
    this.currencyRepository = currencyRepository;
    this.gameMapper = gameMapper;
    this.countryMapper = countryMapper;
    this.currencyMapper = currencyMapper;
  }

  // use toGameWithOnlyLobbyGameId for disable @Transactional and EAGER initialization
  @Override
  public Page<Game> getAvailableLobbyGameIds(PlayerDetails playerDetails, Pageable pageable) {
    Page<GameEntity> games = gameRepository
      .getAvailableLobbyGameIds(playerDetails.getCurrencyCode(), playerDetails.getCountryCode(), pageable);
    return games.map(gameMapper::toGameWithOnlyLobbyGameId);
  }

  @Override
  public boolean isLobbyGameExistsForPlayer(Game game, PlayerDetails playerDetails) {
    return gameRepository
      .isExistsForPlayer(game.getLobbyGameId(), playerDetails.getCurrencyCode(), playerDetails.getCountryCode())
      .isPresent();
  }

  @Override
  @Transactional
  public Game createGame(Game game) {
    if (gameRepository.findByLobbyGameId(game.getLobbyGameId()).isPresent()) {
      throw new GameAlreadyExistsException(String
        .format("Game with specified lobby id: %s, already created", game.getLobbyGameId()));
    }

    Set<Country> availableCountries = getAvailableCountries(game);

    Set<Currency> availableCurrencies = getAvailableCurrencies(game);

    Game newGame = Game.builder()
      .lobbyGameId(game.getLobbyGameId())
      .isBlocked(false)
      .countries(availableCountries)
      .currencies(availableCurrencies)
      .build();

    return gameMapper.toGame(gameRepository
      .save(gameMapper.toGameEntity(newGame)));
  }

  @Override
  @Transactional
  public Game updateGame(Game game) {
    Game gameFromRepository = gameMapper.toGame(gameRepository
      .findByLobbyGameId(game.getLobbyGameId())
      .orElseThrow(() -> new EntityNotFoundException(String
        .format("Game with specified lobby id: %s, not found", game.getLobbyGameId()))));

    Set<Country> availableCountries = getAvailableCountries(game);

    Set<Currency> availableCurrencies = getAvailableCurrencies(game);

    Game newGame = Game.builder()
      .id(gameFromRepository.getId())
      .lobbyGameId(gameFromRepository.getLobbyGameId())
      .isBlocked(game.getIsBlocked())
      .countries(availableCountries)
      .currencies(availableCurrencies)
      .build();

    return gameMapper.toGame(gameRepository
      .save(gameMapper.toGameEntity(newGame)));
  }

  @Override
  @Transactional
  public Game blockGame(String lobbyGameId) {
    Game gameFromRepository = gameMapper.toGame(gameRepository
      .findByLobbyGameId(lobbyGameId)
      .orElseThrow(() -> new EntityNotFoundException(String
        .format("Game with specified lobby id: %s, not found", lobbyGameId))));

    Game newGame = Game.builder()
      .id(gameFromRepository.getId())
      .lobbyGameId(gameFromRepository.getLobbyGameId())
      .isBlocked(true)
      .countries(gameFromRepository.getCountries())
      .currencies(gameFromRepository.getCurrencies())
      .build();

    return gameMapper.toGame(gameRepository
      .save(gameMapper.toGameEntity(newGame)));
  }

  @Override
  @Transactional
  public Game getGame(String lobbyGameId) {
    return gameMapper.toGame(gameRepository
      .findByLobbyGameId(lobbyGameId)
      .orElseThrow(() -> new EntityNotFoundException(String
        .format("Game with specified lobby id: %s, not found", lobbyGameId))));
  }

  private Set<Currency> getAvailableCurrencies(Game game) {
    Set<String> gameCurrencyCodes = game.getCurrencies()
      .stream()
      .map(Currency::getCurrencyCode)
      .collect(Collectors.toSet());

    Set<Currency> currencyFromRepository = currencyRepository
      .findAllByCurrencyCodeIn(gameCurrencyCodes)
      .stream()
      .map(currencyMapper::toCurrency)
      .collect(Collectors.toSet());

    if (!gameCurrencyCodes.containsAll(currencyFromRepository
      .stream()
      .map(Currency::getCurrencyCode)
      .collect(Collectors.toSet()))) {
      throw new EntityNotFoundException("Someone currency from list does not exists!");
    }

    return currencyFromRepository;
  }

  private Set<Country> getAvailableCountries(Game game) {
    Set<String> gameCountryCodes = game.getCountries()
      .stream()
      .map(Country::getCountryCode)
      .collect(Collectors.toSet());

    Set<Country> countriesFromRepository = countryRepository
      .findAllByCountryCodeIn(gameCountryCodes)
      .stream()
      .map(countryMapper::toCountry)
      .collect(Collectors.toSet());

    if (!gameCountryCodes.containsAll(countriesFromRepository
      .stream()
      .map(Country::getCountryCode)
      .collect(Collectors.toSet()))) {
      throw new EntityNotFoundException("Someone country from list does not exists!");
    }

    return countriesFromRepository;
  }


}
