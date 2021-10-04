package tech.pm.edu.web.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.model.Provider;
import tech.pm.edu.web.mapper.ProviderDtoMapper;
import tech.pm.edu.web.model.ProviderDto;

@Component
public class ProviderDtoMapperDefault implements ProviderDtoMapper {

  @Override
  public Provider providerDtoToProvider(ProviderDto providerDto) {
    if (providerDto == null) {
      return null;
    }

    return Provider.builder()
        .id(null)
        .isBlocked(providerDto.getIsBlocked())
        .providerName(providerDto.getProviderName())
        .providerUrl(providerDto.getProviderUrl())
        .build();
  }

  @Override
  public ProviderDto toProviderDto(Provider provider) {
    if (provider == null) {
      return null;
    }

    return new ProviderDto(provider.getProviderName(),provider.getProviderUrl(), provider.getIsBlocked());
  }


}
