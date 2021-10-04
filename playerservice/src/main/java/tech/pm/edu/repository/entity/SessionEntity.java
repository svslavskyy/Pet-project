package tech.pm.edu.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity(name = "session")
@Table(name = "session")
public class SessionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "session_key", nullable = false, unique = true)
  private String sessionKey;

  @Column(name = "creation_timestamp", nullable = false)
  private Instant creationTimestamp;

  @Column(name = "expiration_timestamp", nullable = false)
  private Instant expirationTimestamp;

  @ManyToOne
  @JoinColumn(name = "player_id", nullable = false)
  private PlayerEntity player;


}
