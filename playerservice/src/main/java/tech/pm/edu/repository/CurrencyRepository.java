package tech.pm.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.pm.edu.repository.entity.CurrencyEntity;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {

  Optional<CurrencyEntity> findByCode(String code);


}
