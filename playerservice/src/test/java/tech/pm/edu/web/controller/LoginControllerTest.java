package tech.pm.edu.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tech.pm.edu.repository.PlayerRepository;
import tech.pm.edu.repository.SessionRepository;
import tech.pm.edu.web.model.PlayerLoginDto;
import tech.pm.edu.web.model.PlayerRegistrationDto;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginControllerTest {

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
  public void successLogin() throws Exception {
    PlayerLoginDto playerLoginDto = new PlayerLoginDto(
            "test1@email.com",
            "password"
    );

    mockMvc.perform(post("/player-service/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerLoginDto)))
            .andExpect(status().is(200))
            .andReturn();
  }

  @Test
  public void loginErrorEmailNotFound() throws Exception {
    PlayerLoginDto playerLoginDto = new PlayerLoginDto(
            "test@email.com",
            "password"
    );

    mockMvc.perform(post("/player-service/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerLoginDto)))
            .andExpect(status().is(404))
            .andExpect(mvcResult -> Assertions.assertEquals("Player with specified email: test@email.com not exists", Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()))
            .andReturn();
  }


  @Test
  public void loginErrorPasswordNotMatch() throws Exception {
    PlayerLoginDto playerLoginDto = new PlayerLoginDto(
            "test1@email.com",
            "password1"
    );

    mockMvc.perform(post("/player-service/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerLoginDto)))
            .andExpect(status().is(401))
            .andExpect(mvcResult -> Assertions.assertEquals("Wrong password, try again.", Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()))
            .andReturn();
  }

  @Test
  public void playerBlockedException() throws Exception {

    PlayerLoginDto playerLoginDto = new PlayerLoginDto(
            "test1@email.com",
            "password1"
    );

    mockMvc.perform(delete(String.format("/player-service/api/v1/players/%s", playerLoginDto.getEmail()))).andExpect(status().is(200));

    mockMvc.perform(post("/player-service/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerLoginDto)))
            .andExpect(status().is(403))
            .andExpect(mvcResult -> Assertions.assertEquals("Player with specified email: test1@email.com is blocked", Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()))
            .andReturn();
  }

}