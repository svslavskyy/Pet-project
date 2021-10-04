package tech.pm.edu.domain.service;

import tech.pm.edu.domain.model.Player;
import tech.pm.edu.domain.model.Session;

public interface SessionService {

  Player getPlayerDetails(Session session);

  Session createSession(Player pLayer);


}
