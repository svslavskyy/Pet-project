package tech.pm.edu.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
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
import tech.pm.edu.web.model.PlayerRegistrationDto;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping
        (
                value = "/player-service/api/v1/registration",
                produces = APPLICATION_JSON_VALUE,
                consumes = APPLICATION_JSON_VALUE
        )
@Validated
public class RegistrationController {

  private final PlayerService playerService;

  private final PlayerDtoMapper playerDtoMapper;

  public RegistrationController(PlayerService playerService, PlayerDtoMapper playerDtoMapper) {
    this.playerService = playerService;
    this.playerDtoMapper = playerDtoMapper;
  }

  @Operation(summary = "Player registration")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully registered",
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRegistrationDto.class))}),
          @ApiResponse(responseCode = "401", description = "Bad credentials"),
  })
  @PostMapping
  public ResponseEntity<?> playerRegistration(
          @Valid @RequestBody PlayerRegistrationDto playerRegistrationDto,
          BindingResult bindingResult
  ) throws NoSuchAlgorithmException {

    Player playerDetails = playerDtoMapper.playerRegistrationDtoToPlayer(playerRegistrationDto);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(playerDtoMapper.toPlayerRegistrationDto(playerService
                    .registerPlayer(playerDetails)));

  }


}
