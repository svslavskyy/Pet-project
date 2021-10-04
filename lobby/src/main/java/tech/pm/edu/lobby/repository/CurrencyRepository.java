package tech.pm.edu.lobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.pm.edu.lobby.repository.entity.CurrencyEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {

  List<CurrencyEntity> findAllByCurrencyCodeIn(Set<String> currencyCodes);


}
