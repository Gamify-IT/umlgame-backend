package de.unistuttgart.umlgamebackend.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;
import javax.validation.Valid;

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
    String id;

    String graph;

    String text;

    TaskType taskType;

    /**
     * The volume level that is setted by the player.
     */
    Integer volumeLevel;


    public Configuration(String graph, String text, TaskType taskType) {
        this.graph = graph;
        this.text = text;
        this.taskType = taskType;
    }



}
