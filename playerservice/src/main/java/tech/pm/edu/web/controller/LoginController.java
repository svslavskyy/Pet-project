package tech.pm.edu.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.pm.edu.domain.model.Player;
import tech.pm.edu.domain.service.PlayerService;
import tech.pm.edu.web.mapper.PlayerDtoMapper;
import tech.pm.edu.web.mapper.SessionDtoMapper;
import tech.pm.edu.web.model.PlayerLoginDto;
import tech.pm.edu.web.model.SessionDto;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping
        (
                value = "/player-service/api/v1/login",
                produces = APPLICATION_JSON_VALUE,
                consumes = APPLICATION_JSON_VALUE
        )
@Validated
public class LoginController {

  private final PlayerService playerService;

  private final PlayerDtoMapper playerDtoMapper;
  private final SessionDtoMapper sessionDtoMapper;

  public LoginController(PlayerService playerService, PlayerDtoMapper playerDtoMapper, SessionDtoMapper sessionDtoMapper) {
    this.playerService = playerService;
    this.playerDtoMapper = playerDtoMapper;
    this.sessionDtoMapper = sessionDtoMapper;
  }

  @Operation(summary = "Player authorization")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully logged in",
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PlayerLoginDto.class))}),
          @ApiResponse(responseCode = "401", description = "Email or password not match"),
          @ApiResponse(responseCode = "403", description = "Player is blocked")
  })
  @PostMapping
  public ResponseEntity<SessionDto> playerLogin(
          @Valid @RequestBody PlayerLoginDto playerLoginDto,
          BindingResult bindingResult
  ) throws NoSuchAlgorithmException {
    Player player = playerDtoMapper.playerLoginDtoToPlayer(playerLoginDto);
    return ResponseEntity.ok(sessionDtoMapper.toSessionDto(playerService.loginPlayer(player)));
  }


}
