package tech.pm.edu.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Game {

  Long id;
  String internalId;
  String externalId;
  Boolean isBlocked;
  Provider provider;


}
