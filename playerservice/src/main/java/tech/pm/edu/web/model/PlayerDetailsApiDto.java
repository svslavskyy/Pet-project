package tech.pm.edu.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDetailsApiDto {

  private String currency;
  private String country;
  private String displayName;


}
