package tech.pm.edu.domain.mapper;

import tech.pm.edu.domain.model.Provider;
import tech.pm.edu.repository.model.ProviderEntity;

public interface ProviderMapper {

  Provider toProvider(ProviderEntity providerEntity);

  ProviderEntity toProviderEntity(Provider provider);


}
