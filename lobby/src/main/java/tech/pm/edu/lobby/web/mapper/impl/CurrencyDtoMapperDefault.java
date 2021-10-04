package tech.pm.edu.lobby.web.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.lobby.domain.model.Currency;
import tech.pm.edu.lobby.web.mapper.CurrencyDtoMapper;
import tech.pm.edu.lobby.web.model.CurrencyDto;

@Component
public class CurrencyDtoMapperDefault implements CurrencyDtoMapper {

  @Override
  public CurrencyDto toCurrencyDto(Currency currency) {
    if (currency == null) {
      return null;
    }

    return new CurrencyDto(currency.getCurrencyCode());
  }

  @Override
  public Currency currencyDtoToCurrency(CurrencyDto currencyDto) {
    if (currencyDto == null) {
      return null;
    }

    return new Currency(null, currencyDto.getCurrencyCode());
  }


}
