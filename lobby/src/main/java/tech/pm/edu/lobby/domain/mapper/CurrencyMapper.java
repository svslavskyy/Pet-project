package tech.pm.edu.lobby.domain.mapper;

import tech.pm.edu.lobby.domain.model.Currency;
import tech.pm.edu.lobby.repository.entity.CurrencyEntity;

public interface CurrencyMapper {

  Currency toCurrency(CurrencyEntity currencyEntity);

  CurrencyEntity toCurrencyEntity(Currency currency);


}
