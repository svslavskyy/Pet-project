package tech.pm.edu.domain.service;

import tech.pm.edu.domain.model.Provider;

public interface ProviderService {

  Provider createProvider(Provider provider);
  Provider updateProvider(Provider provider);
  Provider deleteProvider(String providerName);
  Provider getProvider(String providerName);


}
