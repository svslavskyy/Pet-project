package tech.pm.edu.lobby.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.pm.edu.lobby.web.model.GameDto;
import tech.pm.edu.lobby.web.service.GameDtoService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/lobby/api/v1/games", produces = APPLICATION_JSON_VALUE)
@Validated
public class GameController {

  private final GameDtoService gameDtoService;

  public GameController(GameDtoService gameDtoService) {
    this.gameDtoService = gameDtoService;
  }

  @Operation(summary = "Getting the game")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Get the game",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))}),
    @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
  })
  @GetMapping(path = "/{lobbyGameId}")
  public ResponseEntity<GameDto> getGame(
    @Valid @NotBlank @PathVariable String lobbyGameId
  ) {

    return ResponseEntity.ok(gameDtoService.getGame(lobbyGameId));
  }

  @Operation(summary = "Game creating")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Create the game",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))}),
    @ApiResponse(responseCode = "401", description = "Game already exists", content = @Content),
    @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
  })
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<GameDto> createGame(
    @Valid @RequestBody GameDto gameDto,
    BindingResult bindingResult
  ) {

    return ResponseEntity.status(HttpStatus.CREATED)
      .body(gameDtoService.createGameDto(gameDto));
  }

  @Operation(summary = "Game updating")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Update the game",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))}),
    @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
  })
  @PutMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<GameDto> updateGame(
    @Valid @RequestBody GameDto gameDto,
    BindingResult bindingResult
  ) {

    return ResponseEntity.ok(gameDtoService.updateGameDto(gameDto));
  }

  @Operation(summary = "Game blocking")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Block the game",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))}),
    @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)
  })
  @DeleteMapping(path = "/{lobbyGameId}")
  public ResponseEntity<GameDto> blockGame(
    @Valid @NotBlank @PathVariable String lobbyGameId
  ) {

    return ResponseEntity.ok(gameDtoService.blockGameDto(lobbyGameId));
  }


}
