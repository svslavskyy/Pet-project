package tech.pm.edu.lobby.domain.mapper;

import tech.pm.edu.lobby.domain.model.Country;
import tech.pm.edu.lobby.repository.entity.CountryEntity;

public interface CountryMapper {

  Country toCountry(CountryEntity countryEntity);

  CountryEntity toCountryEntity(Country country);


}
