package tech.pm.edu.lobby.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {

  @JsonProperty(required = true)
  @NotBlank(message = "Lobby game id should be entered")
  private String lobbyGameId;

  @JsonProperty(defaultValue = "false")
  private Boolean isBlocked;

  @JsonProperty(required = true)
  @NotNull
  private Set<CountryDto> countries;

  @JsonProperty(required = true)
  @NotNull
  private Set<CurrencyDto> currencies;


}
