package tech.pm.edu.lobby.domain.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.lobby.domain.mapper.CountryMapper;
import tech.pm.edu.lobby.domain.model.Country;
import tech.pm.edu.lobby.repository.entity.CountryEntity;

@Component
public class CountryMapperDefault implements CountryMapper {

  @Override
  public Country toCountry(CountryEntity countryEntity) {
    if (countryEntity == null) {
      return null;
    }

    return new Country(countryEntity.getId(), countryEntity.getCountryCode());
  }

  @Override
  public CountryEntity toCountryEntity(Country country) {
    if (country == null) {
      return null;
    }

    CountryEntity countryEntity = new CountryEntity();
    countryEntity.setId(country.getId());
    countryEntity.setCountryCode(country.getCountryCode());

    return countryEntity;
  }


}
