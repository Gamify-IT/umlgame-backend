package de.unistuttgart.umlgamebackend.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The Configuration.class contains all data that has to be stored to configure a finitequiz game.
 */
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class Configuration {

    /**
     * A unique identifier for the configuration.
     */
    @Id
    @GeneratedValue(generator = "uuid")
    UUID id;


    /**
     * The volume level that is setted by the player.
     */
    Integer volumeLevel;


}
