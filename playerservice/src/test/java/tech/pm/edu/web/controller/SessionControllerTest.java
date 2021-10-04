package tech.pm.edu.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tech.pm.edu.repository.PlayerRepository;
import tech.pm.edu.repository.SessionRepository;
import tech.pm.edu.repository.entity.SessionEntity;
import tech.pm.edu.web.model.PlayerDetailsApiDto;
import tech.pm.edu.web.model.PlayerLoginDto;
import tech.pm.edu.web.model.PlayerRegistrationDto;
import tech.pm.edu.web.model.SessionDto;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SessionControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private PlayerRepository playerRepository;
  @Autowired
  private SessionRepository sessionRepository;

  @AfterEach
  public void cleanRepo() {
    sessionRepository.deleteAll();
    playerRepository.deleteAll();
  }

  @BeforeEach
  public void createNewUser() throws Exception {
    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto(
            "test1@email.com",
            "name",
            "password",
            "UAH",
            "UA",
            false);

    mockMvc.perform(post("/player-service/api/v1/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerRegistrationDto)))
            .andReturn();
  }

  @Test
  public void sessionNotFoundException() throws Exception {
    mockMvc.perform(get(String.format("/player-service/api/v1/sessions?sessionKey=%s", "key")))
            .andExpect(status().is(404))
            .andReturn();
  }

  @Test
  public void sessionPlayerBlockedException() throws Exception {

    PlayerLoginDto playerLoginDto = new PlayerLoginDto(
            "test1@email.com",
            "password"
    );

    MvcResult sessionResult = mockMvc.perform(post("/player-service/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerLoginDto)))
            .andExpect(status().is(200))
            .andReturn();

    SessionDto sessionDto = objectMapper.readValue(sessionResult.getResponse().getContentAsString(), SessionDto.class);

    mockMvc.perform(delete(String.format("/player-service/api/v1/players/%s", playerLoginDto.getEmail())))
            .andExpect(status().is(200))
            .andReturn();

    mockMvc.perform(get(String.format("/player-service/api/v1/sessions?sessionKey=%s", sessionDto.getSessionKey())))
            .andExpect(status().is(403))
            .andReturn();
  }

  @Test
  public void successReturnPlayerDetails() throws Exception {
    PlayerLoginDto playerLoginDto = new PlayerLoginDto(
            "test1@email.com",
            "password"
    );

    MvcResult sessionResult = mockMvc.perform(post("/player-service/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerLoginDto)))
            .andExpect(status().is(200))
            .andReturn();

    SessionDto sessionDto = objectMapper.readValue(sessionResult.getResponse().getContentAsString(), SessionDto.class);

    MvcResult result = mockMvc.perform(get(String.format("/player-service/api/v1/sessions?sessionKey=%s", sessionDto.getSessionKey())))
            .andExpect(status().is(200))
            .andReturn();

    assertEquals(
            new PlayerDetailsApiDto("UAH", "UA", "name"),
            objectMapper.readValue(result.getResponse().getContentAsString(), PlayerDetailsApiDto.class)
    );
  }


  @Test
  public void sessionExpiredException() throws Exception {
    PlayerLoginDto playerLoginDto = new PlayerLoginDto(
            "test1@email.com",
            "password"
    );

    MvcResult sessionResult = mockMvc.perform(post("/player-service/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerLoginDto)))
            .andExpect(status().is(200))
            .andReturn();

    String session = sessionResult.getResponse().getContentAsString();

    SessionDto sessionDto = objectMapper.readValue(session, SessionDto.class);

    SessionEntity sessionEntity = sessionRepository.findBySessionKey(sessionDto.getSessionKey()).get();
    sessionEntity.setExpirationTimestamp(Instant.now());

    sessionRepository.save(sessionEntity);

    mockMvc.perform(get(String.format("/player-service/api/v1/sessions?sessionKey=%s", sessionDto.getSessionKey())))
            .andExpect(status().is(401))
            .andReturn();
  }

}