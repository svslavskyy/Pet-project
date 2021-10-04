package tech.pm.edu.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pm.edu.domain.mapper.GameMapper;
import tech.pm.edu.domain.model.Game;
import tech.pm.edu.domain.service.GameService;
import tech.pm.edu.domain.service.ProviderService;
import tech.pm.edu.repository.GameRepository;
import tech.pm.edu.web.exception.EntityNotFoundException;


@Service
public class GameServiceDefault implements GameService {

  private final GameRepository gameRepository;

  private final ProviderService providerService;

  private final GameMapper gameMapper;

  @Autowired
  public GameServiceDefault(GameRepository gameRepository, ProviderService providerService, GameMapper gameMapper) {
    this.gameRepository = gameRepository;
    this.providerService = providerService;
    this.gameMapper = gameMapper;
  }

  @Override
  public Game getGame(String internalId) {

    return gameMapper.toGame(gameRepository.findByInternalId(internalId)
      .orElseThrow(() -> new EntityNotFoundException("Game not found")));
  }

  @Override
  public Game createGame(Game game) {
    Game newGame = Game.builder()
        .id(null)
        .internalId(game.getInternalId())
        .externalId(game.getExternalId())
        .isBlocked(false)
        .provider(providerService.getProvider(game.getProvider().getProviderName()))
        .build();

    return gameMapper.toGame(gameRepository.save(gameMapper.toGameEntity(newGame)));
  }

  @Override
  public Game updateGame(Game game) {
    Game gameFromRepository = gameMapper.toGame(gameRepository.findByInternalId(game.getInternalId())
      .orElseThrow(() -> new EntityNotFoundException("Game not found")));

    Game newGame = Game.builder()
        .id(gameFromRepository.getId())
        .internalId(game.getInternalId())
        .externalId(game.getExternalId())
        .isBlocked(game.getIsBlocked())
        .provider(providerService.getProvider(game.getProvider().getProviderName()))
        .build();


    return gameMapper.toGame(gameRepository.save(gameMapper.toGameEntity(newGame)));
  }

  @Override
  public Game deleteGame(String internalId) {
    Game gameFromRepository = gameMapper.toGame(gameRepository.findByInternalId(internalId)
      .orElseThrow(() -> new EntityNotFoundException("Game not found")));

    Game newGame = Game.builder()
        .id(gameFromRepository.getId())
        .internalId(gameFromRepository.getInternalId())
        .externalId(gameFromRepository.getExternalId())
        .isBlocked(true)
        .provider(providerService.getProvider(gameFromRepository.getProvider().getProviderName()))
        .build();

    return gameMapper.toGame(gameRepository.save(gameMapper.toGameEntity(newGame)));
  }


}
