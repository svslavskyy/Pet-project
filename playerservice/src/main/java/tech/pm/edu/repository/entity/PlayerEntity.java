package tech.pm.edu.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "player")
@Table(name = "player")
public class PlayerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "display_name", nullable = false)
  private String displayName;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "is_blocked")
  private Boolean isBlocked;

  @ManyToOne
  private CountryEntity country;

  @ManyToOne
  private CurrencyEntity currency;


}
