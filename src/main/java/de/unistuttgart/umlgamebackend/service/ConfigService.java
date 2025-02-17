package de.unistuttgart.umlgamebackend.service;

import de.unistuttgart.umlgamebackend.clients.OverworldClient;
import de.unistuttgart.umlgamebackend.data.*;
import de.unistuttgart.umlgamebackend.data.mapper.ConfigurationMapper;
import de.unistuttgart.umlgamebackend.repositories.ConfigurationRepository;
import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This service handles the logic for the ConfigController.class
 */
@Service
@Slf4j
@Transactional
public class ConfigService {    

    @Autowired
    ConfigurationMapper configurationMapper;    

    @Autowired
    ConfigurationRepository configurationRepository;    

    @Autowired
    private OverworldClient overworldClient;

    @Autowired
    private JWTValidatorService jwtValidatorService;

    /**
     * Search a configuration by given id
     *
     * @param id the id of the configuration searching for
     * @return the found configuration
     * @throws ResponseStatusException  when configuration by configurationName could not be found
     * @throws IllegalArgumentException if at least one of the arguments is null
     */
    public Configuration getConfiguration(final UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return configurationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("There is no configuration with id %s.", id)
                        )
                );
    }

    /**
     * Search a configuration by given id and get volume level from overworld-backend
     *
     * @param id          the id of the configuration searching for
     * @param accessToken the users access token
     * @return the found configuration
     * @throws ResponseStatusException  when configuration by configurationName could not be found
     * @throws IllegalArgumentException if at least one of the arguments is null
     */
    public Configuration getAllConfigurations(final UUID id, final String accessToken) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        final String userId = jwtValidatorService.extractUserId(accessToken);

        KeybindingDTO keyBindingVolumeLevel = overworldClient.getKeybindingStatistic(userId, "VOLUME_LEVEL", accessToken);
        Integer volumeLevel = Integer.parseInt(keyBindingVolumeLevel.getKey());

        Configuration config = configurationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("There is no configuration with id %s.", id)
                        )
                );
        config.setVolumeLevel(volumeLevel);
        return configurationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("There is no configuration with id %s.", id)
                        )
                );
    }


    /**
     * Save a configuration
     *
     * @param configurationDTO configuration that should be saved
     * @return the saved configuration as DTO
     * @throws IllegalArgumentException if at least one of the arguments is null
     */
    public ConfigurationDTO saveConfiguration(final @Valid ConfigurationDTO configurationDTO) {
        if (configurationDTO == null) {
            throw new IllegalArgumentException("configurationDTO is null");
        }
        final Configuration savedConfiguration = configurationRepository.save(
                configurationMapper.configurationDTOToConfiguration(configurationDTO)
        );
        return configurationMapper.configurationToConfigurationDTO(savedConfiguration);
    }

    /**
     * Update a configuration
     *
     * @param id               the id of the configuration that should be updated
     * @param configurationDTO configuration that should be updated
     * @return the updated configuration as DTO
     * @throws ResponseStatusException  when configuration with the id does not exist
     * @throws IllegalArgumentException if at least one of the arguments is null
     */
    public ConfigurationDTO updateConfiguration(final UUID id, @Valid final ConfigurationDTO configurationDTO) {
        if (id == null || configurationDTO == null) {
            throw new IllegalArgumentException("id or configurationDTO is null");
        }
        final Configuration configuration = getConfiguration(id);
        final Configuration updatedConfiguration = configurationRepository.save(configuration);
        return configurationMapper.configurationToConfigurationDTO(updatedConfiguration);
    }

    /**
     * Delete a configuration
     *
     * @param id the id of the configuration that should be updated
     * @return the deleted configuration as DTO
     * @throws ResponseStatusException  when configuration with the id does not exist
     * @throws IllegalArgumentException if at least one of the arguments is null
     */
    public ConfigurationDTO deleteConfiguration(final UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        final Configuration configuration = getConfiguration(id);
        configurationRepository.delete(configuration);
        return configurationMapper.configurationToConfigurationDTO(configuration);
    }

}
