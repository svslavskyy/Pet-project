package tech.pm.edu.lobby.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

  @JsonProperty(required = true)
  @Size(min = 2, max = 2, message = "Country should contain only 2 letters, for example UA ")
  private String countryCode;


}
