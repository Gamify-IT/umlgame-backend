package de.unistuttgart.umlgamebackend.data;

import java.util.List;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

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
    @GeneratedValue(generator = "uuid")
    UUID id;

    List<UmlTaskDTO> taskList;

    Integer volumeLevel;
}
