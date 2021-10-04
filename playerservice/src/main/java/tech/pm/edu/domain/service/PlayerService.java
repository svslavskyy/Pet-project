package tech.pm.edu.domain.service;

import tech.pm.edu.domain.model.Player;
import tech.pm.edu.domain.model.Session;

import java.security.NoSuchAlgorithmException;

public interface PlayerService {

  Player registerPlayer(Player pLayer) throws NoSuchAlgorithmException;

  Session loginPlayer(Player pLayer) throws NoSuchAlgorithmException;

  Player updatePlayer(Player player) throws NoSuchAlgorithmException;

  Player blockPlayer(String email);

  Player getPlayer(String email);


}
