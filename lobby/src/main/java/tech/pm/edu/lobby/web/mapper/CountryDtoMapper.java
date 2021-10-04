package tech.pm.edu.lobby.web.mapper;

import tech.pm.edu.lobby.domain.model.Country;
import tech.pm.edu.lobby.web.model.CountryDto;

public interface CountryDtoMapper {

  CountryDto toCountryDto(Country country);

  Country countryDtoToCountry(CountryDto countryDto);


}
