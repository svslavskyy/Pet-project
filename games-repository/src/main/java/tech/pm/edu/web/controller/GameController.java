package tech.pm.edu.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.pm.edu.domain.model.Game;
import tech.pm.edu.domain.service.GameService;
import tech.pm.edu.web.mapper.GameDtoMapper;
import tech.pm.edu.web.model.GameDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/games-repository/api/v1/games"}, produces = APPLICATION_JSON_VALUE)
@Validated
public class GameController {

  private final GameService gameService;

  private final GameDtoMapper gameDtoMapper;


  public GameController(GameService gameService, GameDtoMapper gameDtoMapper) {
    this.gameService = gameService;
    this.gameDtoMapper = gameDtoMapper;
  }

  @Operation(summary = "Get game by key internal id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the game",
          content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))}),
      @ApiResponse(responseCode = "500", description = "Game not found", content = @Content),
  })
  @GetMapping(path = "/{internalId}")
  public ResponseEntity<GameDto> getGame(
      @Valid @NotBlank @PathVariable String internalId
  ) {
    GameDto responseGameDto = gameDtoMapper.toGameDto(gameService.getGame(internalId));

    return ResponseEntity.ok(responseGameDto);
  }

  @Operation(summary = "Create game")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Game was created",
          content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))}),
  })
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<GameDto> createGame(
      @Valid @RequestBody GameDto gameDto
  ) {
    Game game = gameService.createGame(gameDtoMapper.gameDtoToGame(gameDto));
    GameDto responseGameDto = gameDtoMapper.toGameDto(game);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseGameDto);
  }

  @Operation(summary = "Update game")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Game was updated",
          content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))}),
      @ApiResponse(responseCode = "500", description = "Game not found", content = @Content)
  })
  @PutMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<GameDto> updateGame(
      @Valid @RequestBody GameDto gameDto
  ) {
    Game game = gameService.updateGame(gameDtoMapper.gameDtoToGame(gameDto));
    GameDto responseGameDto = gameDtoMapper.toGameDto(game);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseGameDto);
  }

  @Operation(summary = "Delete game")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Game was deleted",
          content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))}),
      @ApiResponse(responseCode = "500", description = "Game not found", content = @Content)
  })
  @DeleteMapping(path = "/{internalId}")
  public ResponseEntity<GameDto> deleteGame(
      @Valid @NotBlank @PathVariable String internalId
  ) {
    GameDto responseGameDto = gameDtoMapper.toGameDto(gameService.deleteGame(internalId));

    return ResponseEntity.ok(responseGameDto);
  }


}
