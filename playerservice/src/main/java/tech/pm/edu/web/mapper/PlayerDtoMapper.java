package tech.pm.edu.web.mapper;

import tech.pm.edu.domain.model.Player;
import tech.pm.edu.web.model.PlayerDetailsApiDto;
import tech.pm.edu.web.model.PlayerLoginDto;
import tech.pm.edu.web.model.PlayerRegistrationDto;

public interface PlayerDtoMapper {

  PlayerRegistrationDto toPlayerRegistrationDto(Player pLayer);

  Player playerRegistrationDtoToPlayer(PlayerRegistrationDto playerRegistrationDto);

  Player playerLoginDtoToPlayer(PlayerLoginDto playerLoginDto);

  PlayerDetailsApiDto toPlayerDetailsApiDto(Player pLayer);


}
