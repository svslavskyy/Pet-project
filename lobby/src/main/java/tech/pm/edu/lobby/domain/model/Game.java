package tech.pm.edu.lobby.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class Game {

  Long id;
  String lobbyGameId;
  Boolean isBlocked;
  Set<Country> countries;
  Set<Currency> currencies;


}
