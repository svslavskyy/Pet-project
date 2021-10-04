package tech.pm.edu.lobby.web.mapper;

import tech.pm.edu.lobby.domain.model.Currency;
import tech.pm.edu.lobby.web.model.CurrencyDto;

public interface CurrencyDtoMapper {

  CurrencyDto toCurrencyDto(Currency currency);

  Currency currencyDtoToCurrency(CurrencyDto currencyDto);


}
