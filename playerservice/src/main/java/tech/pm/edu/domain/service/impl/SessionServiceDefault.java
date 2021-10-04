package tech.pm.edu.domain.service.impl;

import org.springframework.stereotype.Service;
import tech.pm.edu.domain.config.model.SessionDurationProperties;
import tech.pm.edu.domain.mapper.PlayerMapper;
import tech.pm.edu.domain.mapper.SessionMapper;
import tech.pm.edu.domain.model.Player;
import tech.pm.edu.domain.model.Session;
import tech.pm.edu.domain.service.SessionService;
import tech.pm.edu.repository.PlayerRepository;
import tech.pm.edu.repository.SessionRepository;
import tech.pm.edu.repository.entity.PlayerEntity;
import tech.pm.edu.repository.entity.SessionEntity;
import tech.pm.edu.web.exception.EntityNotFoundException;
import tech.pm.edu.web.exception.PlayerBlockedException;
import tech.pm.edu.web.exception.SessionExpiredException;
import tech.pm.edu.web.exception.SessionKeyMismatchException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionServiceDefault implements SessionService {

  private final SessionRepository sessionRepository;

  private final SessionMapper sessionMapper;
  private final PlayerMapper playerMapper;
  private final PlayerRepository playerRepository;

  private final SessionDurationProperties sessionDurationProperties;

  public SessionServiceDefault(SessionRepository sessionRepository,
                               SessionMapper sessionMapper, PlayerMapper playerMapper,
                               PlayerRepository playerRepository, SessionDurationProperties sessionDurationProperties) {
    this.sessionRepository = sessionRepository;
    this.sessionMapper = sessionMapper;
    this.playerMapper = playerMapper;
    this.playerRepository = playerRepository;
    this.sessionDurationProperties = sessionDurationProperties;
  }

  @Override
  public Player getPlayerDetails(Session session) {
    Session sessionFromRepository = sessionMapper.toSession(sessionRepository.findBySessionKey(session.getSessionKey())
            .orElseThrow(() -> new SessionKeyMismatchException(String.format("Session %s not exists", session.getSessionKey()))));

    if (sessionFromRepository.getPlayer().getIsBlocked()) {
      throw new PlayerBlockedException(String.format("Player with session %s was blocked", session.getSessionKey()));
    }
    if (sessionFromRepository.getExpirationTimestamp().isBefore(Instant.now())) {
      throw new SessionExpiredException(String.format("Session %s expired", session.getSessionKey()));
    }

    Session prolongedSession = Session.builder()
            .id(sessionFromRepository.getId())
            .sessionKey(sessionFromRepository.getSessionKey())
            .creationTimestamp(sessionFromRepository.getCreationTimestamp())
            .player(sessionFromRepository.getPlayer())
            .expirationTimestamp(Instant.now().plus(sessionDurationProperties.getDuration(), ChronoUnit.MINUTES))
            .build();

    SessionEntity sessionEntity = sessionMapper.toSessionEntity(prolongedSession);
    return sessionMapper.toSession(sessionRepository.save(sessionEntity)).getPlayer();
  }

  @Override
  public Session createSession(Player player) {

    Optional<PlayerEntity> playerEntity = playerRepository.findByEmail(player.getEmail());

    if (playerEntity.isPresent()) {
      Session session = Session.builder()
              .sessionKey(UUID.randomUUID().toString())
              .player(playerMapper.toPlayer(playerEntity.get()))
              .creationTimestamp(Instant.now())
              .expirationTimestamp(Instant.now().plus(sessionDurationProperties.getDuration(), ChronoUnit.MINUTES))
              .build();

      SessionEntity sessionEntity = sessionMapper.toSessionEntity(session);
      return sessionMapper.toSession(sessionRepository.save(sessionEntity));
    } else {
      throw new EntityNotFoundException(String.format("Player with specified email %s not found", player.getEmail()));
    }

  }


}
