package tech.pm.edu.lobby.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {

  @JsonProperty(required = true)
  @Size(min = 3, max = 3, message = "Currency should contain only 3 letters, for example USD ")
  private String currencyCode;


}
