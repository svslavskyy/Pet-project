package tech.pm.edu.lobby.web.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.lobby.domain.model.Country;
import tech.pm.edu.lobby.web.mapper.CountryDtoMapper;
import tech.pm.edu.lobby.web.model.CountryDto;

@Component
public class CountryDtoMapperDefault implements CountryDtoMapper {

  @Override
  public CountryDto toCountryDto(Country country) {
    if (country == null) {
      return null;
    }

    return new CountryDto(country.getCountryCode());
  }

  @Override
  public Country countryDtoToCountry(CountryDto countryDto) {
    if (countryDto == null) {
      return null;
    }

    return new Country(null, countryDto.getCountryCode());
  }


}
