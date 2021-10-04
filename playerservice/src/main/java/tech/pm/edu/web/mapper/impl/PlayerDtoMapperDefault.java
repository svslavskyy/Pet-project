package tech.pm.edu.web.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.model.Country;
import tech.pm.edu.domain.model.Currency;
import tech.pm.edu.domain.model.Player;
import tech.pm.edu.web.mapper.PlayerDtoMapper;
import tech.pm.edu.web.model.PlayerDetailsApiDto;
import tech.pm.edu.web.model.PlayerLoginDto;
import tech.pm.edu.web.model.PlayerRegistrationDto;

@Component
public class PlayerDtoMapperDefault implements PlayerDtoMapper {

  @Override
  public PlayerRegistrationDto toPlayerRegistrationDto(Player pLayer) {
    if (pLayer == null) {
      return null;
    }
    PlayerRegistrationDto playerRegistrationDto = new PlayerRegistrationDto();
    playerRegistrationDto.setEmail(pLayer.getEmail());
    playerRegistrationDto.setDisplayName(pLayer.getDisplayName());
    playerRegistrationDto.setCurrency(pLayer.getCurrency().getCurrencyCode());
    playerRegistrationDto.setCountry(pLayer.getCountry().getCountryCode());

    return playerRegistrationDto;
  }

  @Override
  public Player playerRegistrationDtoToPlayer(PlayerRegistrationDto playerRegistrationDto) {
    if (playerRegistrationDto == null) {
      return null;
    }
    return Player.builder()
            .email(playerRegistrationDto.getEmail())
            .displayName(playerRegistrationDto.getDisplayName())
            .password(playerRegistrationDto.getPassword())
            .currency(new Currency(null, playerRegistrationDto.getCurrency()))
            .country(new Country(null, playerRegistrationDto.getCountry()))
            .isBlocked(playerRegistrationDto.getIsBlocked())
            .build();
  }

  @Override
  public Player playerLoginDtoToPlayer(PlayerLoginDto playerLoginDto) {
    if (playerLoginDto == null) {
      return null;
    }
    return Player.builder()
            .email(playerLoginDto.getEmail())
            .password(playerLoginDto.getPassword())
            .build();
  }

  @Override
  public PlayerDetailsApiDto toPlayerDetailsApiDto(Player pLayer) {
    if (pLayer == null) {
      return null;
    }
    return new PlayerDetailsApiDto(
            pLayer.getCurrency().getCurrencyCode(),
            pLayer.getCountry().getCountryCode(),
            pLayer.getDisplayName()
    );
  }


}
