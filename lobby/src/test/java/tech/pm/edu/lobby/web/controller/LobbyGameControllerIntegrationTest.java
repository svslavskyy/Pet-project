package tech.pm.edu.lobby.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tech.pm.edu.lobby.web.model.LaunchUrlDto;
import tech.pm.edu.lobby.web.model.LobbyGameDto;
import tech.pm.edu.lobby.web.model.PlayerDetailsDto;

import java.net.URI;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LobbyGameControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private URI gamesRepositoryUri;
  @Autowired
  private URI playerServiceUri;

  private MockRestServiceServer mockRestTemplate;

  private PlayerDetailsDto playerDetailsDto;
  private LaunchUrlDto launchUrlDto;

  @BeforeEach
  public void setUp() {
    mockRestTemplate = MockRestServiceServer.createServer(restTemplate);
    playerDetailsDto = new PlayerDetailsDto("UAH", "UA");
    launchUrlDto = new LaunchUrlDto("launch-url");
  }

  @Test
  void getLaunchUrl() throws Exception {
    mockRestTemplate.expect(ExpectedCount.once(),
      requestTo(createRestTemplateAvailableLobbyGamesUrl()))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(playerDetailsDto)));

    mockRestTemplate.expect(ExpectedCount.once(),
      requestTo(createRestTemplateLaunchUrl()))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(launchUrlDto)));

    mockMvc.perform(get(createControllerLaunchUrl("playup-happy-bananas")))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.url").value(launchUrlDto.getUrl()));
  }

  @Test
  void getAvailableGames() throws Exception {
    LobbyGameDto lobbyGameDto1 = new LobbyGameDto("playup-slot");
    LobbyGameDto lobbyGameDto2 = new LobbyGameDto("playup-happy-bananas");
    LobbyGameDto lobbyGameDto3 = new LobbyGameDto("bestgames-slot");

    mockRestTemplate.expect(ExpectedCount.once(),
      requestTo(createRestTemplateAvailableLobbyGamesUrl()))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(playerDetailsDto)));

    mockMvc.perform(get(createControllerAvailableLobbyGamesUrl()))
      .andExpect(status().is(200))
      .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString()
        .contains(lobbyGameDto1.getLobbyGameId())))
      .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString()
        .contains(lobbyGameDto2.getLobbyGameId())))
      .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString()
        .contains(lobbyGameDto3.getLobbyGameId())));
  }

  @Test
  void nonExistentSessionException() throws Exception {
    mockRestTemplate.expect(ExpectedCount.once(),
      requestTo(createRestTemplateAvailableLobbyGamesUrl()))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.NOT_FOUND));

    mockMvc.perform(get(createControllerAvailableLobbyGamesUrl()))
      .andExpect(status().is(404));
  }

  @Test
  void sessionExpiredException() throws Exception {
    mockRestTemplate.expect(ExpectedCount.once(),
      requestTo(createRestTemplateAvailableLobbyGamesUrl()))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.UNAUTHORIZED));

    mockMvc.perform(get(createControllerAvailableLobbyGamesUrl()))
      .andExpect(status().is(401));
  }

  @Test
  void entityBlockedException() throws Exception {
    mockRestTemplate.expect(ExpectedCount.once(),
      requestTo(createRestTemplateAvailableLobbyGamesUrl()))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.FORBIDDEN));

    mockMvc.perform(get(createControllerAvailableLobbyGamesUrl()))
      .andExpect(status().is(403));
  }

  @Test
  void trowsUnavailableGameException() throws Exception {
    mockRestTemplate.expect(ExpectedCount.once(),
      requestTo(createRestTemplateAvailableLobbyGamesUrl()))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(playerDetailsDto)));

    mockMvc.perform(get(createControllerLaunchUrl("casino-black-pirate")))
      .andExpect(status().isForbidden());
  }

  private URI createRestTemplateLaunchUrl() {

    return UriComponentsBuilder.fromUri(gamesRepositoryUri)
      .queryParam("lobbyGameId", "playup-happy-bananas")
      .queryParam("sessionKey", "key")
      .build().toUri();
  }

  private URI createRestTemplateAvailableLobbyGamesUrl() {

    return UriComponentsBuilder.fromUri(playerServiceUri)
      .queryParam("sessionKey", "key")
      .build().toUri();
  }

  private URI createControllerLaunchUrl(String lobbyGameId) {
    String launchControllerUrl = "/lobby/api/v1/lobby-games/launch";

    return UriComponentsBuilder.fromUriString(launchControllerUrl)
      .queryParam("lobbyGameId", lobbyGameId)
      .queryParam("sessionKey", "key")
      .build().toUri();
  }

  private URI createControllerAvailableLobbyGamesUrl() {
    String availableLobbyGamesControllerUrl = "/lobby/api/v1/lobby-games/available";

    return UriComponentsBuilder.fromUriString(availableLobbyGamesControllerUrl)
      .queryParam("sessionKey", "key")
      .build().toUri();
  }


}