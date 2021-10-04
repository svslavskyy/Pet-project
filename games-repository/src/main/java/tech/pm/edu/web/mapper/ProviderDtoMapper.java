package tech.pm.edu.web.mapper;

import tech.pm.edu.domain.model.Provider;
import tech.pm.edu.web.model.ProviderDto;

public interface ProviderDtoMapper {

  Provider providerDtoToProvider(ProviderDto providerDto);

  ProviderDto toProviderDto(Provider provider);


}
