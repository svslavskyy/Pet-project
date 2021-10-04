package tech.pm.edu.lobby.web.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.lobby.domain.model.PlayerDetails;
import tech.pm.edu.lobby.web.mapper.PlayerDetailsDtoMapper;
import tech.pm.edu.lobby.web.model.PlayerDetailsDto;

@Component
public class PlayerDetailsDtoMapperDefault implements PlayerDetailsDtoMapper {

  @Override
  public PlayerDetails toPlayerDetails(PlayerDetailsDto playerDetailsDto) {
    if (playerDetailsDto == null) {
      return null;
    }

    return new PlayerDetails(playerDetailsDto.getCurrency(), playerDetailsDto.getCountry());
  }


}
