package tech.pm.edu.domain.mapper;

import tech.pm.edu.domain.model.Session;
import tech.pm.edu.repository.entity.SessionEntity;

public interface SessionMapper {

  Session toSession(SessionEntity sessionEntity);

  SessionEntity toSessionEntity(Session session);


}
