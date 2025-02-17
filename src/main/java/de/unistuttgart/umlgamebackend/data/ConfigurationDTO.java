package de.unistuttgart.umlgamebackend.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * The ConfigurationDTO.class contains all data that has to be stored to configure a finitequiz game.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class ConfigurationDTO {

    /**
     * A unique identifier for the configuration.
     */
    @Nullable
    UUID id;

    Integer volumeLevel;
}
