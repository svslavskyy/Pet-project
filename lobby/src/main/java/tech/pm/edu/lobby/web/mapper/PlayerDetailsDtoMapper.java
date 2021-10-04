package tech.pm.edu.lobby.web.mapper;

import tech.pm.edu.lobby.domain.model.PlayerDetails;
import tech.pm.edu.lobby.web.model.PlayerDetailsDto;

public interface PlayerDetailsDtoMapper {

  PlayerDetails toPlayerDetails(PlayerDetailsDto playerDetailsDto);


}
