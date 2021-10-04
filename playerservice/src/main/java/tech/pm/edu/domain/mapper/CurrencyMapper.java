package tech.pm.edu.domain.mapper;

import tech.pm.edu.domain.model.Currency;
import tech.pm.edu.repository.entity.CurrencyEntity;

public interface CurrencyMapper {

  Currency toCurrency(CurrencyEntity currencyEntity);

  CurrencyEntity toCurrencyEntity(Currency currency);


}
