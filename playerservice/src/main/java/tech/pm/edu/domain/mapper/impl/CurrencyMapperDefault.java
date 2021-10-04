package tech.pm.edu.domain.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.mapper.CurrencyMapper;
import tech.pm.edu.domain.model.Currency;
import tech.pm.edu.repository.entity.CurrencyEntity;

@Component
public class CurrencyMapperDefault implements CurrencyMapper {

  @Override
  public Currency toCurrency(CurrencyEntity currencyEntity) {
    if (currencyEntity == null) {
      return null;
    }

    return new Currency(currencyEntity.getId(), currencyEntity.getCode());
  }

  @Override
  public CurrencyEntity toCurrencyEntity(Currency currency) {
    if (currency == null) {
      return null;
    }
    CurrencyEntity currencyEntity = new CurrencyEntity();
    currencyEntity.setId(currency.getId());
    currencyEntity.setCode(currency.getCurrencyCode());

    return currencyEntity;
  }


}
