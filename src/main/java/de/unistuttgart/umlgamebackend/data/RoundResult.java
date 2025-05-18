package de.unistuttgart.umlgamebackend.data;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

/**
 * The RoundResult.class contains the round result related information.
 */
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class RoundResult {

    /**
     * A unique identifier for the round result.
     */
    @Id
    @GeneratedValue(generator = "uuid")
    UUID id;
}
