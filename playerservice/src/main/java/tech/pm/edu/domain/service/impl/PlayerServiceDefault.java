package tech.pm.edu.domain.service.impl;

import org.springframework.stereotype.Service;
import tech.pm.edu.domain.mapper.CountryMapper;
import tech.pm.edu.domain.mapper.CurrencyMapper;
import tech.pm.edu.domain.mapper.PlayerMapper;
import tech.pm.edu.domain.model.Country;
import tech.pm.edu.domain.model.Currency;
import tech.pm.edu.domain.model.Player;
import tech.pm.edu.domain.model.Session;
import tech.pm.edu.domain.service.PasswordEncryptionService;
import tech.pm.edu.domain.service.PlayerService;
import tech.pm.edu.domain.service.SessionService;
import tech.pm.edu.repository.CountryRepository;
import tech.pm.edu.repository.CurrencyRepository;
import tech.pm.edu.repository.PlayerRepository;
import tech.pm.edu.repository.entity.PlayerEntity;
import tech.pm.edu.web.exception.EntityNotFoundException;
import tech.pm.edu.web.exception.PasswordNotMatchException;
import tech.pm.edu.web.exception.PlayerAlreadyExistsException;
import tech.pm.edu.web.exception.PlayerBlockedException;

import java.security.NoSuchAlgorithmException;

@Service
public class PlayerServiceDefault implements PlayerService {

  private final SessionService sessionService;

  private final PlayerRepository playerRepository;
  private final CountryRepository countryRepository;
  private final CurrencyRepository currencyRepository;

  private final PasswordEncryptionService passwordEncryptionService;

  private final CountryMapper countryMapper;
  private final CurrencyMapper currencyMapper;
  private final PlayerMapper playerMapper;

  public PlayerServiceDefault(PlayerRepository playerRepository, CountryRepository countryRepository,
                              CurrencyRepository currencyRepository,
                              CountryMapper countryMapper, CurrencyMapper currencyMapper,
                              PlayerMapper playerMapper, SessionService sessionService,
                              PasswordEncryptionService passwordEncryptionService) {
    this.sessionService = sessionService;
    this.playerRepository = playerRepository;
    this.countryRepository = countryRepository;
    this.currencyRepository = currencyRepository;
    this.countryMapper = countryMapper;
    this.currencyMapper = currencyMapper;
    this.playerMapper = playerMapper;
    this.passwordEncryptionService = passwordEncryptionService;
  }

  @Override
  public Player registerPlayer(Player player) throws NoSuchAlgorithmException {

    if (playerRepository.findByEmail(player.getEmail()).isPresent()) {
      throw new PlayerAlreadyExistsException(String.format("Player with specified email: %s, already registered", player.getEmail()));
    }

    Country country = countryMapper.toCountry(countryRepository.findByCode(player.getCountry().getCountryCode())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Country %s not exists", player.getCountry().getCountryCode()))));

    Currency currency = currencyMapper.toCurrency(currencyRepository.findByCode(player.getCurrency().getCurrencyCode())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Currency %s not exists", player.getCurrency().getCurrencyCode()))));

    Player newPlayer = Player.builder()
            .email(player.getEmail())
            .displayName(player.getDisplayName().trim())
            .password(passwordEncryptionService.encryptPassword(player.getPassword()))
            .currency(currency)
            .country(country)
            .isBlocked(false)
            .build();

    PlayerEntity playerEntity = playerMapper.toPlayerEntity(newPlayer);
    return playerMapper.toPlayer(playerRepository.save(playerEntity));
  }

  @Override
  public Session loginPlayer(Player player) throws NoSuchAlgorithmException {

    Player playerFromRepository = playerMapper.toPlayer(playerRepository.findByEmail(player.getEmail())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Player with specified email: %s not exists", player.getEmail()))));

    if (playerFromRepository.getIsBlocked()) {
      throw new PlayerBlockedException(String.format("Player with specified email: %s is blocked", player.getEmail()));
    }

    if (passwordEncryptionService.encryptPassword(player.getPassword()).equals(playerFromRepository.getPassword())) {
      return sessionService.createSession(playerFromRepository);
    } else {
      throw new PasswordNotMatchException("Wrong password, try again.");
    }
  }

  @Override
  public Player updatePlayer(Player player) throws NoSuchAlgorithmException {
    Player playerFromRepository = playerMapper.toPlayer(playerRepository.findByEmail(player.getEmail())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Player with specified email: %s not exists", player.getEmail()))));

    Country country = countryMapper.toCountry(countryRepository.findByCode(player.getCountry().getCountryCode())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Country %s not exists", player.getCountry().getCountryCode()))));

    Currency currency = currencyMapper.toCurrency(currencyRepository.findByCode(player.getCurrency().getCurrencyCode())
            .orElseThrow(() -> new EntityNotFoundException(String.format("Currency %s not exists", player.getCurrency().getCurrencyCode()))));

    Player newPlayer = Player.builder()
            .id(playerFromRepository.getId())
            .email(playerFromRepository.getEmail())
            .displayName(player.getDisplayName())
            .password(passwordEncryptionService.encryptPassword(player.getPassword()))
            .country(country)
            .currency(currency)
            .isBlocked(player.getIsBlocked())
            .build();

    PlayerEntity playerEntity = playerRepository.save(playerMapper.toPlayerEntity(newPlayer));
    return playerMapper.toPlayer(playerEntity);
  }

  @Override
  public Player blockPlayer(String email) {
    Player playerFromRepository = playerMapper.toPlayer(playerRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Player with specified email: %s not exists", email))));

    Player newPlayer = Player.builder()
            .id(playerFromRepository.getId())
            .email(playerFromRepository.getEmail())
            .displayName(playerFromRepository.getDisplayName())
            .password(playerFromRepository.getPassword())
            .country(playerFromRepository.getCountry())
            .currency(playerFromRepository.getCurrency())
            .isBlocked(true)
            .build();

    PlayerEntity playerEntity = playerMapper.toPlayerEntity(newPlayer);
    return playerMapper.toPlayer(playerRepository.save(playerEntity));
  }

  @Override
  public Player getPlayer(String email) {
    return playerMapper.toPlayer(playerRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Player with specified email: %s not exists", email))));
  }


}
