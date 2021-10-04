package tech.pm.edu.domain.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.mapper.ProviderMapper;
import tech.pm.edu.domain.model.Provider;
import tech.pm.edu.repository.model.ProviderEntity;

@Component
public class ProviderMapperDefault implements ProviderMapper {

  @Override
  public Provider toProvider(ProviderEntity providerEntity) {
    if (providerEntity == null) {
      return null;
    }

    return Provider.builder()
        .id(providerEntity.getId())
        .isBlocked( providerEntity.getIsBlocked())
        .providerName(providerEntity.getProviderName())
        .providerUrl(providerEntity.getProviderUrl())
        .build();
  }

  @Override
  public ProviderEntity toProviderEntity(Provider provider) {
    if (provider == null) {
      return null;
    }

    ProviderEntity providerEntity = new ProviderEntity();
    providerEntity.setId(provider.getId());
    providerEntity.setIsBlocked(provider.getIsBlocked());
    providerEntity.setProviderName(provider.getProviderName());
    providerEntity.setProviderUrl(provider.getProviderUrl());

    return providerEntity;
  }


}
