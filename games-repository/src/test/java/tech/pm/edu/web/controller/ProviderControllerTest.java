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
import tech.pm.edu.web.model.ProviderDto;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProviderControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void getProvider() throws Exception {
    String providerName = "playup";

    mockMvc.perform(get("/games-repository/api/v1/providers/"+providerName)
        .contentType(MediaType.TEXT_PLAIN)
        .content(providerName))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.providerName").value(providerName));
  }

  @Test
  public void getProviderException() throws Exception {
    String providerName = "no this provider";

    mockMvc.perform(get("/games-repository/api/v1/providers/"+providerName)
        .contentType(MediaType.TEXT_PLAIN)
        .content(providerName))
        .andExpect(status().is(404))
        .andExpect(mvcResult -> Assertions.assertEquals("Provider not found", Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()));
  }

  @Test
  public void createProvider() throws Exception {
    ProviderDto providerDto = new ProviderDto();
    providerDto.setProviderName("playup-new");
    providerDto.setProviderUrl("https://playup.new.com.ua");
    providerDto.setIsBlocked(false);

    mockMvc.perform(post("/games-repository/api/v1/providers/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(providerDto)))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.providerName").value(providerDto.getProviderName()));
  }

  @Test
  public void updateProvider() throws Exception {
    ProviderDto providerDto = new ProviderDto();
    providerDto.setProviderName("playup");
    providerDto.setProviderUrl("https://playup.update.com.ua");
    providerDto.setIsBlocked(false);

    mockMvc.perform(put("/games-repository/api/v1/providers/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(providerDto)))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.providerName").value(providerDto.getProviderName()));
  }

  @Test
  public void blockedProvider() throws Exception {
    String providerName = "playup";

    mockMvc.perform(delete("/games-repository/api/v1/providers/"+providerName)
        .contentType(MediaType.TEXT_PLAIN)
        .content(providerName))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.providerName").value(providerName))
        .andExpect(jsonPath("$.isBlocked").value(true));
  }


}
