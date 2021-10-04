package tech.pm.edu.domain.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.mapper.CountryMapper;
import tech.pm.edu.domain.model.Country;
import tech.pm.edu.repository.entity.CountryEntity;

@Component
public class CountryMapperDefault implements CountryMapper {

  @Override
  public Country toCountry(CountryEntity countryEntity) {
    if (countryEntity == null) {
      return null;
    }

    return new Country(countryEntity.getId(), countryEntity.getCode());
  }

  @Override
  public CountryEntity toCountryEntity(Country country) {
    if (country == null) {
      return null;
    }
    CountryEntity countryEntity = new CountryEntity();
    countryEntity.setId(country.getId());
    countryEntity.setCode(country.getCountryCode());

    return countryEntity;
  }


}
