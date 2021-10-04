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
import tech.pm.edu.web.model.LaunchUrlDto;
import tech.pm.edu.web.service.LaunchUrlService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/games-repository/api/v1/launch-url", produces = APPLICATION_JSON_VALUE)
@Validated
public class LaunchUrlController {

  private final LaunchUrlService launchUrlService;

  public LaunchUrlController(LaunchUrlService launchUrlService) {
    this.launchUrlService = launchUrlService;
  }

  @Operation(summary = "Generate launch url")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Launch url was generated",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LaunchUrlDto.class))}),
      @ApiResponse(responseCode = "401", description = "Session was expired", content = @Content),
      @ApiResponse(responseCode = "403", description = "Game is unavailable", content = @Content),
      @ApiResponse(responseCode = "404", description = "Session is invalid", content = @Content)
  })
  @GetMapping
  public ResponseEntity<LaunchUrlDto> generateLaunchUrlDto(
    @Valid @NotBlank @RequestParam(name = "lobbyGameId") String lobbyGameId,
    @Valid @NotBlank @RequestParam(name = "sessionKey") String sessionKey
  ) {

    return ResponseEntity.ok(launchUrlService.generateLaunchUrl(lobbyGameId, sessionKey));
  }


}
