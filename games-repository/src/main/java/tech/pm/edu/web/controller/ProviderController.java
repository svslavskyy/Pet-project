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
import tech.pm.edu.domain.model.Provider;
import tech.pm.edu.domain.service.ProviderService;
import tech.pm.edu.web.mapper.ProviderDtoMapper;
import tech.pm.edu.web.model.ProviderDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/games-repository/api/v1/providers"}, produces = APPLICATION_JSON_VALUE)
@Validated
public class ProviderController {

  private final ProviderService providerService;

  private final ProviderDtoMapper providerDtoMapper;


  public ProviderController(ProviderService providerService, ProviderDtoMapper providerDtoMapper) {
    this.providerService = providerService;
    this.providerDtoMapper = providerDtoMapper;
  }

  @Operation(summary = "Get provider by key provider name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the provider",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProviderDto.class))}),
      @ApiResponse(responseCode = "500", description = "Provider not found", content = @Content)
  })
  @GetMapping(path = "/{providerName}")
  public ResponseEntity<ProviderDto> getProvider(
    @Valid @NotBlank @PathVariable String providerName
  ) {
    Provider provider = providerService.getProvider(providerName);
    ProviderDto providerDto = providerDtoMapper.toProviderDto(provider);

    return ResponseEntity.ok(providerDto);
  }

  @Operation(summary = "Create provider")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Provider was created",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProviderDto.class))}),

  })
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ProviderDto> createProvider(
    @Valid @RequestBody ProviderDto providerDto
  ) {
    Provider provider = providerService.createProvider(providerDtoMapper.providerDtoToProvider(providerDto));
    ProviderDto providerDtoResponse = providerDtoMapper.toProviderDto(provider);

    return ResponseEntity.status(HttpStatus.CREATED).body(providerDtoResponse);
  }

  @Operation(summary = "Update provider")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Provider was updated",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProviderDto.class))}),
      @ApiResponse(responseCode = "500", description = "Provider not found", content = @Content)
  })
  @PutMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ProviderDto> updateProvider(
    @Valid @RequestBody ProviderDto providerDto
  ) {
    Provider provider = providerService.updateProvider(providerDtoMapper.providerDtoToProvider(providerDto));
    ProviderDto providerDtoResponse = providerDtoMapper.toProviderDto(provider);

    return ResponseEntity.status(HttpStatus.CREATED).body(providerDtoResponse);
  }

  @Operation(summary = "Delete provider")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Provider was deleted",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProviderDto.class))}),
      @ApiResponse(responseCode = "500", description = "Provider not found", content = @Content)
  })
  @DeleteMapping(path = "/{providerName}")
  public ResponseEntity<ProviderDto> deleteProvider(
    @Valid @NotBlank @PathVariable String providerName
  ) {
    Provider provider = providerService.deleteProvider(providerName);
    ProviderDto providerDto = providerDtoMapper.toProviderDto(provider);

    return ResponseEntity.ok(providerDto);
  }


}
