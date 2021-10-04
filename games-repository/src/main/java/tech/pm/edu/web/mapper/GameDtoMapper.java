package tech.pm.edu.web.mapper;

import tech.pm.edu.domain.model.Game;
import tech.pm.edu.web.model.GameDto;

public interface GameDtoMapper {

  Game gameDtoToGame(GameDto gameDto);

  GameDto toGameDto(Game game);


}
