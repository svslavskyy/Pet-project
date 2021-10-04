package tech.pm.edu.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class Session {

  Long id;
  String sessionKey;
  Instant creationTimestamp;
  Instant expirationTimestamp;
  Player player;


}
