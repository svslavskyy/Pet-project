package tech.pm.edu.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerRegistrationDto {

  @JsonProperty(required = true)
  @NotBlank(message = "Email should be entered")
  @Email(message = "Wrong email format")
  private String email;

  @JsonProperty(required = true)
  @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Wrong name format, you can use only letters and numbers")
  private String displayName;

  @JsonProperty(required = true)
  @Size(min = 6, max = 20, message = "Password size should be between 6 and 20 symbols")
  private String password;

  @JsonProperty(required = true)
  @Size(min = 3, max = 3, message = "Currency should contain only 3 letters, for example USD ")
  private String currency;

  @JsonProperty(required = true)
  @Size(min = 2, max = 2, message = "Country should contain only 2 letters, for example UA ")
  private String country;

  @JsonProperty(defaultValue = "false")
  private Boolean isBlocked;


}
