package tech.pm.edu.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pm.edu.domain.mapper.ProviderMapper;
import tech.pm.edu.domain.model.Provider;
import tech.pm.edu.domain.service.ProviderService;
import tech.pm.edu.repository.ProviderRepository;
import tech.pm.edu.web.exception.EntityNotFoundException;

@Service
public class ProviderServiceDefault implements ProviderService {

  private final ProviderRepository providerRepository;

  private final ProviderMapper providerMapper;

  @Autowired
  public ProviderServiceDefault(ProviderRepository providerRepository, ProviderMapper providerMapper) {
    this.providerRepository = providerRepository;
    this.providerMapper = providerMapper;
  }

  @Override
  public Provider createProvider(Provider provider) {
    Provider newProvider = Provider.builder()
        .id(null)
        .isBlocked(false)
        .providerName(provider.getProviderName())
        .providerUrl(provider.getProviderUrl())
        .build();

    return providerMapper.toProvider(providerRepository
        .save(providerMapper.toProviderEntity(newProvider)));
  }

  @Override
  public Provider updateProvider(Provider provider) {
    Provider providerFromRepository = providerMapper.toProvider(providerRepository.findByProviderName(provider.getProviderName())
        .orElseThrow(() -> new EntityNotFoundException("Provider not found")));

    Provider newProvider = Provider.builder()
        .id(providerFromRepository.getId())
        .isBlocked(provider.getIsBlocked())
        .providerName(provider.getProviderName())
        .providerUrl(provider.getProviderUrl())
        .build();

    return providerMapper.toProvider(providerRepository.save(providerMapper.toProviderEntity(newProvider)));
  }

  @Override
  public Provider deleteProvider(String providerName) {
    Provider providerFromRepository = providerMapper.toProvider(providerRepository.findByProviderName(providerName)
        .orElseThrow(() -> new EntityNotFoundException("Provider not found")));

    Provider newProvider = Provider.builder()
        .id(providerFromRepository.getId())
        .isBlocked(true)
        .providerName(providerFromRepository.getProviderName())
        .providerUrl(providerFromRepository.getProviderUrl())
        .build();

    return providerMapper.toProvider(providerRepository.save(providerMapper.toProviderEntity(newProvider)));
  }

  @Override
  public Provider getProvider(String providerName) {

    return providerMapper.toProvider(providerRepository.findByProviderName(providerName)
        .orElseThrow(() -> new EntityNotFoundException("Provider not found")));
  }


}
