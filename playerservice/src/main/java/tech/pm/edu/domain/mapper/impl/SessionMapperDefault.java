package tech.pm.edu.domain.mapper.impl;

import org.springframework.stereotype.Component;
import tech.pm.edu.domain.mapper.PlayerMapper;
import tech.pm.edu.domain.mapper.SessionMapper;
import tech.pm.edu.domain.model.Session;
import tech.pm.edu.repository.entity.SessionEntity;

@Component
public class SessionMapperDefault implements SessionMapper {

  private final PlayerMapper playerMapper;

  public SessionMapperDefault(PlayerMapper playerMapper) {
    this.playerMapper = playerMapper;
  }

  @Override
  public Session toSession(SessionEntity sessionEntity) {
    if (sessionEntity == null) {
      return null;
    }

    return Session.builder()
            .id(sessionEntity.getId())
            .sessionKey(sessionEntity.getSessionKey())
            .creationTimestamp(sessionEntity.getCreationTimestamp())
            .expirationTimestamp(sessionEntity.getExpirationTimestamp())
            .player(playerMapper.toPlayer(sessionEntity.getPlayer()))
            .build();
  }

  @Override
  public SessionEntity toSessionEntity(Session session) {
    if (session == null) {
      return null;
    }
    SessionEntity sessionEntity = new SessionEntity();
    sessionEntity.setId(session.getId());
    sessionEntity.setSessionKey(session.getSessionKey());
    sessionEntity.setCreationTimestamp(session.getCreationTimestamp());
    sessionEntity.setExpirationTimestamp(session.getExpirationTimestamp());
    sessionEntity.setPlayer(playerMapper.toPlayerEntity(session.getPlayer()));

    return sessionEntity;
  }


}
