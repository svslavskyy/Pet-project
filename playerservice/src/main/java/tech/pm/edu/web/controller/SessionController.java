package tech.pm.edu.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.pm.edu.domain.model.Session;
import tech.pm.edu.domain.service.SessionService;
import tech.pm.edu.web.mapper.PlayerDtoMapper;
import tech.pm.edu.web.mapper.SessionDtoMapper;
import tech.pm.edu.web.model.PlayerDetailsApiDto;
import tech.pm.edu.web.model.SessionDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/player-service/api/v1/sessions", produces = APPLICATION_JSON_VALUE)
@Validated
public class SessionController {

  private final SessionService sessionService;

  private final SessionDtoMapper sessionDtoMapper;
  private final PlayerDtoMapper playerDtoMapper;

  public SessionController(SessionService sessionService, SessionDtoMapper sessionDtoMapper, PlayerDtoMapper playerDtoMapper) {
    this.sessionService = sessionService;
    this.sessionDtoMapper = sessionDtoMapper;
    this.playerDtoMapper = playerDtoMapper;
  }

  @Operation(summary = "Player details by session key")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Session was found",
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDetailsApiDto.class))}),
          @ApiResponse(responseCode = "401", description = "Session expired"),
          @ApiResponse(responseCode = "403", description = "Player with current session was blocked"),
          @ApiResponse(responseCode = "404", description = "Session not found")
  })
  @GetMapping
  public ResponseEntity<PlayerDetailsApiDto> getPlayerDetails(
          @Valid @NotBlank @RequestParam(name = "sessionKey") String sessionKey
  ) {
    Session session = sessionDtoMapper.sessionDtoToSession(new SessionDto(sessionKey));
    return ResponseEntity.ok(playerDtoMapper.toPlayerDetailsApiDto(sessionService
            .getPlayerDetails(session)));
  }


}
