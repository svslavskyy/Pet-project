package tech.pm.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.pm.edu.repository.model.ProviderEntity;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderEntity, Integer> {

  Optional<ProviderEntity> findByProviderName(String providerName);


}
