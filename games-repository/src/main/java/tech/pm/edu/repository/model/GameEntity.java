package tech.pm.edu.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
@Entity(name = "game")
@Table(name = "game")
public class GameEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "internal_id", nullable = false)
  private String internalId;

  @Column(name = "external_id", nullable = false)
  private String externalId;

  @Column(name = "is_blocked", nullable = false)
  private Boolean isBlocked;

  @ManyToOne
  @JoinColumn(name = "provider_id", nullable = false)
  private ProviderEntity provider;


}
