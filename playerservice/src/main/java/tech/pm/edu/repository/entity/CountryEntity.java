package tech.pm.edu.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "country")
@Table(name = "country")
public class CountryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "country_code", nullable = false, length = 2)
  private String code;


}
