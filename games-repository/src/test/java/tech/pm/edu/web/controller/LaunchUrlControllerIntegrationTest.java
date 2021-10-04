package tech.pm.edu.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tech.pm.edu.web.model.LaunchUrlDto;
import tech.pm.edu.web.model.PlayerDetailsDto;

import java.net.URI;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LaunchUrlControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private URI playerServiceUri;

  private MockRestServiceServer mockRestTemplate;

  private PlayerDetailsDto playerDetailsDto;
  private LaunchUrlDto launchUrlDto;

  @BeforeEach
  public void setUp() {
    mockRestTemplate = MockRestServiceServer.createServer(restTemplate);
    playerDetailsDto = new PlayerDetailsDto("UAH", "UA","best player");
    launchUrlDto = new LaunchUrlDto("https://bestgames.com:/launch?game=gold-monkey&sessionToken=key&displayName=best+player&currency=UAH");
  }


  @Test
  public void generateLaunchUrl() throws Exception{

    mockRestTemplate.expect(ExpectedCount.once(),
        requestTo(createRestTemplateAvailableLobbyGamesUrl("key")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(objectMapper.writeValueAsString(playerDetailsDto)));

    mockMvc.perform(get(createControllerLaunchUrl("bestgames-gold-monkey", "key")))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.url").value(launchUrlDto.getUrl()));
  }

  private URI createRestTemplateAvailableLobbyGamesUrl(String sessionKey) {

    return UriComponentsBuilder.fromUri(playerServiceUri)
        .queryParam("sessionKey", sessionKey)
        .build().toUri();
  }

  private URI createControllerLaunchUrl(String lobbyGameId, String sessionKey) {
    String launchControllerUrl = "/games-repository/api/v1/launch-url";

    return UriComponentsBuilder.fromUriString(launchControllerUrl)
        .queryParam("lobbyGameId", lobbyGameId)
        .queryParam("sessionKey", sessionKey)
        .build().toUri();
  }


}
