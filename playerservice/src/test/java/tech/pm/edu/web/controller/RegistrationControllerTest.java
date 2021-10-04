package tech.pm.edu.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tech.pm.edu.domain.service.PlayerService;
import tech.pm.edu.repository.PlayerRepository;
import tech.pm.edu.web.mapper.PlayerDtoMapper;
import tech.pm.edu.web.model.PlayerRegistrationDto;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegistrationControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private PlayerRepository playerRepository;
  @Autowired
  private PlayerService playerService;
  @Autowired
  private PlayerDtoMapper playerMapper;

  @AfterEach
  public void deleteAll() {
    playerRepository.deleteAll();
  }

  @Test
  void registrationSuccessTest() throws Exception {

    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto(
            "test1@email.com",
            "name",
            "password",
            "UAH",
            "UA",
            false
    );

    mockMvc.perform(post("/player-service/api/v1/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerRegistrationDto)))
            .andExpect(status().is(201))
            .andExpect(jsonPath("$.email").value(playerRegistrationDto.getEmail()))
            .andExpect(jsonPath("$.displayName").value(playerRegistrationDto.getDisplayName()))
            .andExpect(jsonPath("$.currency").value(playerRegistrationDto.getCurrency()))
            .andExpect(jsonPath("$.country").value(playerRegistrationDto.getCountry()))
            .andReturn();
  }

  @Test
  void registrationErrorEmailAlreadyRegisteredTest() throws Exception {
    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto(
            "test1@email.com",
            "name",
            "password",
            "UAH",
            "UA",
            false);

    playerService.registerPlayer(playerMapper.playerRegistrationDtoToPlayer(playerRegistrationDto));

    PlayerRegistrationDto existingEmail = new PlayerRegistrationDto(
            "test1@email.com",
            "name",
            "password",
            "UAH",
            "UA",
            false);

    mockMvc.perform(post("/player-service/api/v1/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(existingEmail)))
            .andExpect(status().is(401))
            .andExpect(mvcResult -> Assertions.assertEquals("Player with specified email: test1@email.com, already registered", Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()))
            .andReturn();
  }


  @Test
  void registrationErrorCountryBadFormatTest() throws Exception {
    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto(
            "test1@email.com",
            "name",
            "password",
            "UAH",
            "Error",
            false);

    mockMvc.perform(post("/player-service/api/v1/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerRegistrationDto)))
            .andExpect(status().is(400))
            .andExpect(mvcResult -> Assertions.assertTrue(Objects.requireNonNull(mvcResult.getResolvedException()).getMessage().contains(
                    "Country should contain only 2 letters, for example UA"
            )))
            .andReturn();
  }

  @Test
  void registrationErrorCurrencyBadFormatTest() throws Exception {
    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto(
            "test1@email.com",
            "name",
            "password",
            "Error",
            "UA",
            false);

    mockMvc.perform(post("/player-service/api/v1/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerRegistrationDto)))
            .andExpect(status().is(400))
            .andExpect(mvcResult -> Assertions.assertTrue(Objects.requireNonNull(mvcResult.getResolvedException()).getMessage().contains(
                    "Currency should contain only 3 letters, for example USD "
            )))
            .andReturn();
  }

  @Test
  void registrationErrorCountryNotExistsTest() throws Exception {
    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto(
            "test1@email.com",
            "name",
            "password",
            "UAH",
            "YU",
            false);

    mockMvc.perform(post("/player-service/api/v1/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerRegistrationDto)))
            .andExpect(status().is(404))
            .andExpect(mvcResult -> Assertions.assertEquals(String.format("Country %s not exists", playerRegistrationDto.getCountry()), Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()))
            .andReturn();

  }

  @Test
  void registrationErrorCurrencyNotExistsTest() throws Exception {
    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto(
            "test1@email.com",
            "name",
            "password",
            "UA1",
            "UA",
            false);

    mockMvc.perform(post("/player-service/api/v1/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerRegistrationDto)))
            .andExpect(status().is(404))
            .andExpect(mvcResult -> Assertions.assertEquals(String.format("Currency %s not exists", playerRegistrationDto.getCurrency()), Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()))
            .andReturn();

  }

  @Test
  void whenWeCreatePlayerWithSpacesBeforeNameOrAfterTheyShouldBeDeleted() throws Exception {

    String expectedName = "My Name";

    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto(
            "test1@email.com",
            "           My Name            ",
            "password",
            "UAH",
            "UA",
            false);

    mockMvc.perform(post("/player-service/api/v1/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerRegistrationDto)))
            .andExpect(status().is(201))
            .andExpect(jsonPath("$.email").value(playerRegistrationDto.getEmail()))
            .andExpect(jsonPath("$.displayName").value(expectedName))
            .andExpect(jsonPath("$.currency").value(playerRegistrationDto.getCurrency()))
            .andExpect(jsonPath("$.country").value(playerRegistrationDto.getCountry()))
            .andReturn();

  }

  @Test
  void whenWeTryingEnterBlankEmailWeShouldGetError() throws Exception {
    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto(
            "",
            "My name",
            "password",
            "UAH",
            "UA",
            false);

    mockMvc.perform(post("/player-service/api/v1/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(playerRegistrationDto)))
            .andExpect(status().is(400))
            .andExpect(mvcResult -> Assertions.assertTrue(Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()
                    .contains("Email should be entered")))
            .andReturn();
  }


}