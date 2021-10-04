package tech.pm.edu.web.mapper;

import tech.pm.edu.domain.model.Session;
import tech.pm.edu.web.model.SessionDto;

public interface SessionDtoMapper {

  SessionDto toSessionDto(Session session);

  Session sessionDtoToSession(SessionDto sessionDto);


}
