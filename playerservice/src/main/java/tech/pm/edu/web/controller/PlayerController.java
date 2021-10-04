package tech.pm.edu.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.pm.edu.domain.model.Player;
import tech.pm.edu.domain.service.PlayerService;
import tech.pm.edu.web.mapper.PlayerDtoMapper;
import tech.pm.edu.web.model.PlayerRegistrationDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/player-service/api/v1/players", produces = APPLICATION_JSON_VALUE)
@Validated
public class PlayerController {

  private final PlayerService playerService;

  private final PlayerDtoMapper playerDtoMapper;

  public PlayerController(PlayerService playerService, PlayerDtoMapper playerDtoMapper) {
    this.playerService = playerService;
    this.playerDtoMapper = playerDtoMapper;
  }

  @Operation(summary = "Player update")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully update",
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRegistrationDto.class))}),
          @ApiResponse(responseCode = "401", description = "Bad credentials"),
  })
  @PutMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<PlayerRegistrationDto> playerUpdate(
          @Valid @RequestBody PlayerRegistrationDto playerRegistrationDto,
          BindingResult bindingResult
  ) throws NoSuchAlgorithmException {

    Player player = playerDtoMapper.playerRegistrationDtoToPlayer(playerRegistrationDto);
    PlayerRegistrationDto playerResponse = playerDtoMapper.toPlayerRegistrationDto(playerService.updatePlayer(player));

    return ResponseEntity.ok(playerResponse);
  }

  @Operation(summary = "Player blocking")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully blocked",
                  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRegistrationDto.class))}),
          @ApiResponse(responseCode = "401", description = "Bad credentials"),
  })
  @DeleteMapping(path = "/{email}")
  public ResponseEntity<PlayerRegistrationDto> playerBlock(
          @Valid @NotBlank @PathVariable String email
  ) {

    PlayerRegistrationDto playerResponse = playerDtoMapper.toPlayerRegistrationDto(playerService.blockPlayer(email));

    return ResponseEntity.ok(playerResponse);
  }


}
