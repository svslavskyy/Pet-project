package tech.pm.edu.lobby.domain.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.lobby.domain.mapper.CurrencyMapper;
import tech.pm.edu.lobby.domain.model.Currency;
import tech.pm.edu.lobby.repository.entity.CurrencyEntity;

@Component
public class CurrencyMapperDefault implements CurrencyMapper {

  @Override
  public Currency toCurrency(CurrencyEntity currencyEntity) {
    if (currencyEntity == null) {
      return null;
    }

    return new Currency(currencyEntity.getId(), currencyEntity.getCurrencyCode());
  }

  @Override
  public CurrencyEntity toCurrencyEntity(Currency currency) {
    if (currency == null) {
      return null;
    }

    CurrencyEntity currencyEntity = new CurrencyEntity();
    currencyEntity.setId(currency.getId());
    currencyEntity.setCurrencyCode(currency.getCurrencyCode());

    return currencyEntity;
  }


}
