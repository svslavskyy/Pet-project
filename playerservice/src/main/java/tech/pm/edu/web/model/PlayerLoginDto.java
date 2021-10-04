package tech.pm.edu.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerLoginDto {

  @JsonProperty(required = true)
  @NotBlank(message = "Email should be entered")
  @Email(message = "Wrong email format")
  private String email;

  @JsonProperty(required = true)
  @Size(min = 1, message = "Password should be entered")
  private String password;


}
