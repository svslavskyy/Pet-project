package tech.pm.edu.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tech.pm.edu.web.model.GameDto;
import tech.pm.edu.web.model.ProviderDto;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GameControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void getGame() throws Exception {
    String gameName = "playup-happy-bananas";

    mockMvc.perform(get("/games-repository/api/v1/games/"+gameName)
        .contentType(MediaType.TEXT_PLAIN)
        .content(gameName))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.internalId").value(gameName));
  }

  @Test
  public void getGameException() throws Exception {
    String gameName = "no this game";

    mockMvc.perform(get("/games-repository/api/v1/games/"+gameName)
        .contentType(MediaType.TEXT_PLAIN)
        .content(gameName))
        .andExpect(status().is(404))
        .andExpect(mvcResult -> Assertions.assertEquals("Game not found", Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()));
  }

  @Test
  public void createGame() throws Exception {
    ProviderDto providerDto = new ProviderDto();
    providerDto.setProviderName("playup");
    providerDto.setProviderUrl("https://playup.com.ua");
    providerDto.setIsBlocked(false);

    GameDto gameDto = new GameDto();
    gameDto.setInternalId("newgame");
    gameDto.setExternalId("newgame");
    gameDto.setIsBlocked(false);
    gameDto.setProvider(providerDto);

    mockMvc.perform(post("/games-repository/api/v1/games")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(gameDto)))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.internalId").value(gameDto.getInternalId()));
  }

  @Test
  public void updateGame() throws Exception {
    ProviderDto providerDto = new ProviderDto();
    providerDto.setProviderName("playup");
    providerDto.setProviderUrl("https://playup.com.ua");
    providerDto.setIsBlocked(false);

    GameDto gameDto = new GameDto();
    gameDto.setInternalId("playup-happy-bananas");
    gameDto.setExternalId("bestgame");
    gameDto.setIsBlocked(false);
    gameDto.setProvider(providerDto);

    mockMvc.perform(put("/games-repository/api/v1/games")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(gameDto)))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.internalId").value(gameDto.getInternalId()))
        .andExpect(jsonPath("$.externalId").value(gameDto.getExternalId()));
  }

  @Test
  public void blockedProvider() throws Exception {
    String gameName = "playup-happy-bananas";

    mockMvc.perform(delete("/games-repository/api/v1/games/"+gameName)
        .contentType(MediaType.TEXT_PLAIN)
        .content(gameName))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.internalId").value(gameName))
        .andExpect(jsonPath("$.isBlocked").value(true));
  }


}
