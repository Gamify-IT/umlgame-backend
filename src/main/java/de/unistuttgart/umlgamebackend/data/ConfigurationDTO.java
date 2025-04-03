package de.unistuttgart.umlgamebackend.data;

import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

/**
 * The ConfigurationDTO.class contains all data that has to be stored to configure a UML game.
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
    String id;

    /**
     * A graph representation for the UML game.
     */
    @Valid
    Object graph;

    /**
     * A textual description of the task.
     */
    String text;

    /**
     * The type of task for the UML game.
     */
    TaskType taskType;

    public ConfigurationDTO(final Object graph, final String text, final TaskType taskType) {
        this.graph = graph;
        this.text = text;
        this.taskType = taskType;
    }

    /**
     * Checks whether the content of the current configuration is equal to another configuration.
     */
    public boolean equalsContent(final ConfigurationDTO other) {
        if (this == other) return true;
        if (other == null) return false;
        return Objects.equals(graph, other.graph) && Objects.equals(text, other.text) && taskType == other.taskType;
    }

    /**
     * Enum representing the possible types of tasks in the UML game.
     */
    public enum TaskType {
        COMPLETION(0),
        ERRORHUNT(1),
        CODETOUML(2),
        UMLTOCODE(3);

        private final int value;

        TaskType(int value) {
            this.value = value;
        }
    }
}
