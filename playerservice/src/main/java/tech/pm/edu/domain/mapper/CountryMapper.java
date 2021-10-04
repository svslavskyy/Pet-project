package tech.pm.edu.domain.mapper;

import tech.pm.edu.domain.model.Country;
import tech.pm.edu.repository.entity.CountryEntity;

public interface CountryMapper {

  Country toCountry(CountryEntity countryEntity);

  CountryEntity toCountryEntity(Country country);


}
