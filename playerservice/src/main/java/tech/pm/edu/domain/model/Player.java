package tech.pm.edu.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Player {

  Long id;
  String email;
  String displayName;
  String password;
  Currency currency;
  Country country;
  Boolean isBlocked;


}
