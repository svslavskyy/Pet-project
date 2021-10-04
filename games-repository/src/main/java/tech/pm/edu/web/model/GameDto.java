package tech.pm.edu.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {

  @JsonProperty(required = true)
  @NotBlank
  private String internalId;

  @JsonProperty(required = true)
  @NotBlank
  private String externalId;

  @JsonProperty
  private Boolean isBlocked;

  @JsonProperty(required = true)
  @NotNull
  private ProviderDto provider;


}
