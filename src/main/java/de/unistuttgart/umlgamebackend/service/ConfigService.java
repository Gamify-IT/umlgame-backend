package de.unistuttgart.umlgamebackend.service;

import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import de.unistuttgart.umlgamebackend.clients.OverworldClient;
import de.unistuttgart.umlgamebackend.data.*;
import de.unistuttgart.umlgamebackend.data.mapper.ConfigurationMapper;
import de.unistuttgart.umlgamebackend.data.mapper.UmlTaskMapper;
import de.unistuttgart.umlgamebackend.repositories.ConfigurationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    UmlTaskMapper umlTaskMapper;

    @Autowired
    ConfigurationRepository configurationRepository;

    @Autowired
    private OverworldClient overworldClient;

    @Autowired
    private JWTValidatorService jwtValidatorService;

    @EventListener(ApplicationReadyEvent.class)
    public void createConfig() {
        List<UmlTask> tasks = new ArrayList<>();
        UmlTask t = new UmlTask();
        t.setGraph("{\"cells\":[{\"type\":\"Rect\",\"attrs\":{\"line2\":{\"y1\":40,\"y2\":40},\"label\":{\"text\":\"Dog\",\"y\":12},\"secondaryLabel\":{\"text\":\"dogAttribut\",\"y\":32.5},\"thirdLabel\":{\"text\":\"dogMethode\",\"y\":47.5}},\"position\":{\"x\":122,\"y\":151},\"size\":{\"width\":100,\"height\":60},\"angle\":0,\"id\":\"23fec6c2-0ee1-4dc6-a5d4-202eea5a58e7\",\"z\":1},{\"type\":\"Rect\",\"attrs\":{\"line2\":{\"y1\":40,\"y2\":40},\"label\":{\"text\":\"Cat\",\"y\":12},\"secondaryLabel\":{\"text\":\"catAttribut\",\"y\":32.5},\"thirdLabel\":{\"text\":\"catMethode\",\"y\":47.5}},\"position\":{\"x\":172,\"y\":414},\"size\":{\"width\":100,\"height\":60},\"angle\":0,\"id\":\"fbbd00db-3668-482b-9cf4-480de9cfc801\",\"z\":2},{\"type\":\"standard.Link\",\"attrs\":{\"line\":{\"stroke\":\"black\",\"targetMarker\":{\"d\":\"M 20 0 L 10 5 L 0 0 L 10 -5 Z\",\"fill\":\"white\",\"stroke\":\"black\"}}},\"source\":{\"id\":\"23fec6c2-0ee1-4dc6-a5d4-202eea5a58e7\"},\"target\":{\"id\":\"fbbd00db-3668-482b-9cf4-480de9cfc801\"},\"id\":\"d4aee3ad-0140-431e-879c-1cdd93b896d0\",\"z\":3,\"labels\":[{\"attrs\":{\"text\":{\"fill\":\"black\",\"fontSize\":12,\"text\":\"1\"}},\"position\":{\"distance\":0.2,\"offset\":{\"x\":0,\"y\":-15},\"args\":{\"keepDirection\":true,\"keepAngle\":true}}},{\"attrs\":{\"text\":{\"fill\":\"black\",\"fontSize\":12,\"text\":\"dog\"}},\"position\":{\"distance\":0.2,\"offset\":{\"x\":0,\"y\":10},\"args\":{\"keepDirection\":true,\"keepAngle\":true}}},{\"attrs\":{\"text\":{\"fill\":\"black\",\"fontSize\":12,\"text\":\"2\"}},\"position\":{\"distance\":0.8,\"offset\":{\"x\":0,\"y\":-15},\"args\":{\"keepDirection\":true,\"keepAngle\":true}}},{\"attrs\":{\"text\":{\"fill\":\"black\",\"fontSize\":12,\"text\":\"cat\"}},\"position\":{\"distance\":0.8,\"offset\":{\"x\":0,\"y\":10},\"args\":{\"keepDirection\":true,\"keepAngle\":true}}},{\"attrs\":{\"text\":{\"fill\":\"black\",\"fontSize\":14,\"text\":\"blabla\"}},\"position\":{\"distance\":0.5,\"offset\":{\"x\":0,\"y\":10},\"args\":{\"keepDirection\":true,\"keepAngle\":true}}}]}]}");
        t.setTaskNumber("1");
        t.setText("test text");
        t.setTaskType(UmlTask.TaskType.COMPLETION);
        tasks.add(t);
        Configuration c = new Configuration();
        c.setTaskList(tasks);
        configurationRepository.save(c);
    }

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

        KeybindingDTO keyBindingVolumeLevel = overworldClient.getKeybindingStatistic(
            userId,
            "VOLUME_LEVEL",
            accessToken
        );
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
        configuration.setTaskList(umlTaskMapper.umlTaskDTOsToUmlTasks(configurationDTO.getTaskList()));
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
