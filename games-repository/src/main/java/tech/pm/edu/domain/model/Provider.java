package tech.pm.edu.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Provider {

  Integer id;
  Boolean isBlocked;
  String providerName;
  String providerUrl;


}
