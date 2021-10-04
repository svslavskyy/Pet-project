package tech.pm.edu.lobby.repository.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "game")
@Table(name = "game")
public class GameEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "lobby_game_id", nullable = false)
  private String lobbyGameId;

  @Column(name = "is_blocked", nullable = false)
  private Boolean isBlocked;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable
    (
      name = "game_country",
      joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id")
    )
  @Setter(AccessLevel.PRIVATE)
  private Set<CountryEntity> countries = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable
    (
      name = "game_currency",
      joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "currency_id", referencedColumnName = "id")
    )
  @Setter(AccessLevel.PRIVATE)
  private Set<CurrencyEntity> currencies = new HashSet<>();


}
