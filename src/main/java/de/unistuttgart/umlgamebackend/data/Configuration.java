package de.unistuttgart.umlgamebackend.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * The Configuration.class contains all data that has to be stored to configure a finitequiz game.
 */
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<UmlTask> taskList;

    Integer volumeLevel;

    public void setVolumeLevel(Integer volumeLevel) {

    }
}
