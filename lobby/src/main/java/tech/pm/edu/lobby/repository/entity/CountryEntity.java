package tech.pm.edu.lobby.repository.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity(name = "country")
@Table(name = "country")
public class CountryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "country_code", nullable = false, length = 2)
  private String countryCode;

  @ManyToMany(mappedBy = "countries")
  @Setter(AccessLevel.PRIVATE)
  private Set<GameEntity> games;


}
