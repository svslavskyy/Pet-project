package tech.pm.edu.lobby.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tech.pm.edu.lobby.web.model.CountryDto;
import tech.pm.edu.lobby.web.model.CurrencyDto;
import tech.pm.edu.lobby.web.model.GameDto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GameControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  private GameDto gameDto;

  @BeforeEach
  public void setUp() {
    Set<CountryDto> countries = new HashSet<>();

    countries.add(new CountryDto("EU"));
    countries.add(new CountryDto("CN"));

    Set<CurrencyDto> currencies = new HashSet<>();

    currencies.add(new CurrencyDto("EUR"));
    currencies.add(new CurrencyDto("CNY"));

    gameDto = new GameDto("new-game", false, countries, currencies);
  }

  @Test
  @Order(1)
  void getGame() throws Exception {

    mockMvc.perform(get("/lobby/api/v1/games/playup-happy-bananas"))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.lobbyGameId").value("playup-happy-bananas"))
      .andExpect(jsonPath("$.isBlocked").value(false));
  }

  @Test
  @Order(2)
  void createGame() throws Exception {

    mockMvc.perform(post("/lobby/api/v1/games")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(gameDto)))
      .andExpect(status().is(201))
      .andExpect(jsonPath("$.lobbyGameId").value("new-game"))
      .andExpect(jsonPath("$.isBlocked").value(false));
  }

  @Test
  @Order(3)
  void blockGame() throws Exception {

    mockMvc.perform(delete("/lobby/api/v1/games/new-game")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(gameDto)))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.lobbyGameId").value("new-game"))
      .andExpect(jsonPath("$.isBlocked").value(true));
  }

  @Test
  @Order(4)
  void updateGame() throws Exception {

    gameDto.setIsBlocked(false);

    mockMvc.perform(put("/lobby/api/v1/games")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(gameDto)))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.lobbyGameId").value("new-game"))
      .andExpect(jsonPath("$.isBlocked").value(false));
  }

  @Test
  @Order(5)
  void GameAlreadyExistsException() throws Exception {

    mockMvc.perform(post("/lobby/api/v1/games")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(gameDto)))
      .andExpect(status().is(401))
      .andExpect(mvcResult -> Assertions.assertTrue(Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()
        .contains("Game with specified lobby id: new-game, already created")));
  }

  @Test
  @Order(6)
  void EntityNotFoundException() throws Exception {

    mockMvc.perform(get("/lobby/api/v1/games/gagame"))
      .andExpect(status().is(404))
      .andExpect(mvcResult -> Assertions.assertTrue(Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()
        .contains("Game with specified lobby id: gagame, not found")));
  }


}