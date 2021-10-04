package tech.pm.edu.web.service;


import tech.pm.edu.web.model.PlayerDetailsDto;

public interface GameRepositoryPlayerApiService {

  PlayerDetailsDto getPlayerDetailsDto(String sessionKey);


}