package tech.pm.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.pm.edu.repository.entity.CountryEntity;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

  Optional<CountryEntity> findByCode(String code);


}
