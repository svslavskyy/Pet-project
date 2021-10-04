package tech.pm.edu.repository.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
@Entity(name = "provider")
@Table(name = "provider")
public class ProviderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "provider_name", nullable = false)
  private String providerName;

  @Column(name = "provider_url", nullable = false)
  private String providerUrl;

  @Column(name = "is_blocked", nullable = false)
  private Boolean isBlocked;


}
