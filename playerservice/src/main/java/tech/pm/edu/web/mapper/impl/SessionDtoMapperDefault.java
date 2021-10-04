package tech.pm.edu.web.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.model.Session;
import tech.pm.edu.web.mapper.SessionDtoMapper;
import tech.pm.edu.web.model.SessionDto;

@Component
public class SessionDtoMapperDefault implements SessionDtoMapper {

  @Override
  public SessionDto toSessionDto(Session session) {
    if (session == null) {
      return null;
    }
    return new SessionDto(session.getSessionKey());
  }

  @Override
  public Session sessionDtoToSession(SessionDto sessionDto) {
    if (sessionDto == null) {
      return null;
    }
    return Session.builder()
            .sessionKey(sessionDto.getSessionKey())
            .build();
  }


}
