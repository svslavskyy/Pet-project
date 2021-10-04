package tech.pm.edu.lobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.pm.edu.lobby.repository.entity.CountryEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

  List<CountryEntity> findAllByCountryCodeIn(Set<String> countryCodes);


}
