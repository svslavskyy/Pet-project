package tech.pm.edu.domain.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.mapper.CountryMapper;
import tech.pm.edu.domain.mapper.CurrencyMapper;
import tech.pm.edu.domain.mapper.PlayerMapper;
import tech.pm.edu.domain.model.Player;
import tech.pm.edu.repository.entity.PlayerEntity;

@Component
public class PlayerMapperDefault implements PlayerMapper {

  private final CountryMapper countryMapper;
  private final CurrencyMapper currencyMapper;

  public PlayerMapperDefault(CountryMapper countryMapper, CurrencyMapper currencyMapper) {
    this.countryMapper = countryMapper;
    this.currencyMapper = currencyMapper;
  }

  @Override
  public Player toPlayer(PlayerEntity playerEntity) {
    if (playerEntity == null) {
      return null;
    }

    return Player.builder()
            .id(playerEntity.getId())
            .email(playerEntity.getEmail())
            .displayName(playerEntity.getDisplayName())
            .password(playerEntity.getPassword())
            .country(countryMapper.toCountry(playerEntity.getCountry()))
            .currency(currencyMapper.toCurrency(playerEntity.getCurrency()))
            .isBlocked(playerEntity.getIsBlocked())
            .build();
  }

  @Override
  public PlayerEntity toPlayerEntity(Player pLayer) {
    if (pLayer == null) {
      return null;
    }
    PlayerEntity playerEntity = new PlayerEntity();
    playerEntity.setId(pLayer.getId());
    playerEntity.setEmail(pLayer.getEmail());
    playerEntity.setDisplayName(pLayer.getDisplayName());
    playerEntity.setPassword(pLayer.getPassword());
    playerEntity.setCurrency(currencyMapper.toCurrencyEntity(pLayer.getCurrency()));
    playerEntity.setCountry(countryMapper.toCountryEntity(pLayer.getCountry()));
    playerEntity.setIsBlocked(pLayer.getIsBlocked());

    return playerEntity;
  }


}
