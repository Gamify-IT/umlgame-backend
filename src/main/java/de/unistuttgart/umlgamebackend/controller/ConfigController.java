package de.unistuttgart.umlgamebackend.controller;

import de.unistuttgart.umlgamebackend.data.ConfigurationDTO;
import de.unistuttgart.umlgamebackend.data.mapper.ConfigurationMapper;
import de.unistuttgart.umlgamebackend.repositories.ConfigurationRepository;
import de.unistuttgart.umlgamebackend.service.ConfigService;
import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This controller handles all game-configuration-related REST-APIs
 */
@RestController
@RequestMapping("/configurations")
@Import({ JWTValidatorService.class })
@Slf4j
@Validated
public class ConfigController {

    public static final List<String> LECTURER = List.of("lecturer");
    @Autowired
    ConfigurationRepository configurationRepository;

    @Autowired
    ConfigService configService;

    @Autowired
    ConfigurationMapper configurationMapper;

    @Autowired
    private JWTValidatorService jwtValidatorService;

    @GetMapping("")
    public List<ConfigurationDTO> getConfigurations(@CookieValue("access_token") final String accessToken) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        log.debug("get all configurations");
        return configurationMapper.configurationsToConfigurationDTOs(configurationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ConfigurationDTO getConfiguration(
        @CookieValue("access_token") final String accessToken,
        @PathVariable final UUID id
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        log.debug("get configuration {}", id);
        return configurationMapper.configurationToConfigurationDTO(configService.getConfiguration(id));
    }

    @GetMapping("/{id}/volume")
    public ConfigurationDTO getAllConfiguration(
            @CookieValue("access_token") final String accessToken,
            @PathVariable final UUID id
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        log.debug("get configuration {}", id);
        return configurationMapper.configurationToConfigurationDTO(
                configService.getAllConfigurations(id, accessToken)
        );
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ConfigurationDTO createConfiguration(
        @CookieValue("access_token") final String accessToken,
        @Valid @RequestBody final ConfigurationDTO configurationDTO
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER);
        log.debug("create configuration {}", configurationDTO);
        return configService.saveConfiguration(configurationDTO);
    }

    @PutMapping("/{id}")
    public ConfigurationDTO updateConfiguration(
        @CookieValue("access_token") final String accessToken,
        @PathVariable final UUID id,
        @Valid @RequestBody final ConfigurationDTO configurationDTO
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER);
        log.debug("update configuration {} with {}", id, configurationDTO);
        return configService.updateConfiguration(id, configurationDTO);
    }

    @DeleteMapping("/{id}")
    public ConfigurationDTO deleteConfiguration(
        @CookieValue("access_token") final String accessToken,
        @PathVariable final UUID id
    ) {
        jwtValidatorService.validateTokenOrThrow(accessToken);
        jwtValidatorService.hasRolesOrThrow(accessToken, LECTURER);
        log.debug("delete configuration {}", id);
        return configService.deleteConfiguration(id);
    }


}
