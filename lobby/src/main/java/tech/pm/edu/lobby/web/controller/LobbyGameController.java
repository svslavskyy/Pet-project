package tech.pm.edu.lobby.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.pm.edu.lobby.web.model.LaunchUrlDto;
import tech.pm.edu.lobby.web.model.LobbyGameDto;
import tech.pm.edu.lobby.web.service.LobbyGameDtoService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/lobby/api/v1/lobby-games", produces = APPLICATION_JSON_VALUE)
@Validated
public class LobbyGameController {

  private final LobbyGameDtoService lobbyGameDtoService;

  public LobbyGameController(LobbyGameDtoService lobbyGameDtoService) {
    this.lobbyGameDtoService = lobbyGameDtoService;
  }

  @Operation(summary = "Get the available games for player")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Found the available games",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LobbyGameDto.class))}),
    @ApiResponse(responseCode = "401", description = "Session was expired", content = @Content),
    @ApiResponse(responseCode = "403", description = "Lobby game is blocked", content = @Content),
    @ApiResponse(responseCode = "404", description = "Session is invalid", content = @Content)
  })
  @GetMapping(value = "/available")
  public Page<LobbyGameDto> getAvailableLobbyGames(
    @Valid @NotBlank @RequestParam(name = "sessionKey") String sessionKey,
    @ParameterObject
    @PageableDefault(size = 20)
    @SortDefault.SortDefaults({@SortDefault(sort = "lobbyGameId", direction = Sort.Direction.DESC)})
      Pageable pageable
  ) {

    return lobbyGameDtoService.getAvailableLobbyGames(sessionKey, pageable);
  }

  @Operation(summary = "Get the launch url for current game")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Get a launch url",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LobbyGameDto.class))}),
    @ApiResponse(responseCode = "401", description = "Session was expired", content = @Content),
    @ApiResponse(responseCode = "403", description = "Lobby game is blocked", content = @Content),
    @ApiResponse(responseCode = "404", description = "Session is invalid", content = @Content)
  })
  @GetMapping(path = "/launch")
  public ResponseEntity<LaunchUrlDto> getLaunchUrl(
    @Valid @NotBlank @RequestParam(name = "lobbyGameId") String lobbyGameId,
    @Valid @NotBlank @RequestParam(name = "sessionKey") String sessionKey
  ) {

    return ResponseEntity.ok(lobbyGameDtoService.getLaunchUrl(lobbyGameId, sessionKey));
  }


}
