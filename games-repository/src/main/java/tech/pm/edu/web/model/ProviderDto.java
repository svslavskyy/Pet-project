package tech.pm.edu.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDto {

  @JsonProperty(required = true)
  @NotBlank
  private String providerName;

  @JsonProperty(required = true)
  @NotBlank
  private String providerUrl;

  @JsonProperty
  private Boolean isBlocked;


}
