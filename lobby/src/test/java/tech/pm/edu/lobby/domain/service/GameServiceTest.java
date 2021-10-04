package tech.pm.edu.lobby.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import tech.pm.edu.lobby.domain.model.Country;
import tech.pm.edu.lobby.domain.model.Currency;
import tech.pm.edu.lobby.domain.model.Game;
import tech.pm.edu.lobby.domain.model.PlayerDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class GameServiceTest {

  @Autowired
  private GameService gameService;

  private Game playUpHappyBananas;
  private Game bestGamesSlot;

  private List<Game> games;

  private PlayerDetails playerDetails;

  private Set<Country> countries;
  private Set<String> countryCodes;

  private Set<Currency> currencies;
  private Set<String> currencyCodes;

  private Game myGame;


  @BeforeEach
  void setUp() {
    playUpHappyBananas = Game.builder().lobbyGameId("playup-happy-bananas").build();
    Game playUpSlot = Game.builder().lobbyGameId("playup-slot").build();
    bestGamesSlot = Game.builder().lobbyGameId("bestgames-slot").build();

    games = new ArrayList<>();

    games.add(playUpHappyBananas);
    games.add(playUpSlot);
    games.add(bestGamesSlot);

    playerDetails = new PlayerDetails("UAH", "UA");

    countries = new HashSet<>();

    countries.add(new Country(null, "EU"));
    countries.add(new Country(null, "CN"));

    countryCodes = countries
      .stream()
      .map(Country::getCountryCode)
      .collect(Collectors.toSet());

    currencies = new HashSet<>();

    currencies.add(new Currency(null, "EUR"));
    currencies.add(new Currency(null, "CNY"));

    currencyCodes = currencies
      .stream()
      .map(Currency::getCurrencyCode)
      .collect(Collectors.toSet());

    myGame = Game.builder()
      .lobbyGameId("My game")
      .countries(countries)
      .currencies(currencies)
      .build();
  }

  @Test
  @Order(1)
  void getAvailableLobbyGames() {
    List<Game> availableGames = gameService.getAvailableLobbyGameIds(playerDetails, PageRequest.of(0, 5)).getContent();
    assertThat(games).hasSameElementsAs(availableGames);
  }

  @Test
  @Order(2)
  void getAvailableLobbyGamesWithPage() {
    List<Game> games = new ArrayList<>();
    games.add(playUpHappyBananas);

    List<Game> availableGames = gameService.getAvailableLobbyGameIds(playerDetails, PageRequest.of(0, 1)).getContent();
    assertThat(games).hasSameElementsAs(availableGames);
  }

  @Test
  @Order(3)
  void getAvailableLobbyGamesWithUnPaged() {
    List<Game> availableGames = gameService.getAvailableLobbyGameIds(playerDetails, Pageable.unpaged()).getContent();
    assertThat(games).hasSameElementsAs(availableGames);
  }

  @Test
  @Order(4)
  void getEmptyListOfLobbyGames() {
    List<Game> games = new ArrayList<>();

    List<Game> availableGames = gameService.getAvailableLobbyGameIds(
      new PlayerDetails("RUB", "CN"), Pageable.unpaged()).getContent();
    assertThat(games).hasSameElementsAs(availableGames);
  }

  @Test
  @Order(5)
  void isGameAvailableForPlayer() {
    assertTrue(gameService.isLobbyGameExistsForPlayer(playUpHappyBananas, playerDetails));
    assertTrue(gameService.isLobbyGameExistsForPlayer(playUpHappyBananas, new PlayerDetails("UAH", "RU")));
  }

  @Test
  @Order(6)
  void isGameUnavailableForPlayer() {
    assertFalse(gameService.isLobbyGameExistsForPlayer(playUpHappyBananas, new PlayerDetails("CNY", "CNY")));
    assertFalse(gameService.isLobbyGameExistsForPlayer(bestGamesSlot, new PlayerDetails("RU", "RU")));
  }

  @Test
  @Order(7)
  void createGame() {
    Game createdGame = gameService.createGame(myGame);

    assertEquals(myGame.getLobbyGameId(), createdGame.getLobbyGameId());

    Set<String> createdGameCountryCodes = getCreatedCountryCodes(createdGame);

    assertEquals(countryCodes, createdGameCountryCodes);

    Set<String> createdGameCurrencyCodes = getCreatedCurrencyCodes(createdGame);

    assertEquals(currencyCodes, createdGameCurrencyCodes);
  }

  @Test
  @Order(8)
  void blockGame() {
    Game bannedGame = gameService.blockGame(myGame.getLobbyGameId());

    assertTrue(bannedGame.getIsBlocked());
  }

  @Test
  @Order(9)
  void updateGame() {
    currencies.add(new Currency(null, "USD"));
    countries.add(new Country(null, "US"));

    countryCodes.add("US");
    currencyCodes.add("USD");

    Game myGameUpdated = Game.builder()
      .lobbyGameId("My game")
      .isBlocked(false)
      .currencies(currencies)
      .countries(countries)
      .build();

    Game updatedGame = gameService.updateGame(myGameUpdated);

    assertEquals(myGameUpdated.getLobbyGameId(), updatedGame.getLobbyGameId());

    assertFalse(updatedGame.getIsBlocked());

    Set<String> updatedGameCountryCodes = getCreatedCountryCodes(updatedGame);

    assertEquals(countryCodes, updatedGameCountryCodes);

    Set<String> createdGameCurrencyCodes = getCreatedCurrencyCodes(updatedGame);

    assertEquals(currencyCodes, createdGameCurrencyCodes);
  }

  @Test
  @Order(10)
  void getGame() {
    Game game = Game.builder()
      .lobbyGameId("My game")
      .isBlocked(false)
      .currencies(currencies)
      .countries(countries)
      .build();

    countryCodes.add("US");
    currencyCodes.add("USD");

    Game gameFromRepository = gameService.getGame("My game");

    assertEquals(game.getLobbyGameId(), gameFromRepository.getLobbyGameId());

    assertFalse(gameFromRepository.getIsBlocked());

    Set<String> updatedGameCountryCodes = getCreatedCountryCodes(gameFromRepository);

    assertEquals(countryCodes, updatedGameCountryCodes);

    Set<String> createdGameCurrencyCodes = getCreatedCurrencyCodes(gameFromRepository);

    assertEquals(currencyCodes, createdGameCurrencyCodes);
  }

  private Set<String> getCreatedCountryCodes(Game createdGame) {
    return createdGame.getCountries()
      .stream()
      .map(Country::getCountryCode)
      .collect(Collectors.toSet());
  }

  private Set<String> getCreatedCurrencyCodes(Game createdGame) {
    return createdGame.getCurrencies()
      .stream()
      .map(Currency::getCurrencyCode)
      .collect(Collectors.toSet());
  }


}