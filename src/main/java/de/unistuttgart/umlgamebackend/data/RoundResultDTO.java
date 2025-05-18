package de.unistuttgart.umlgamebackend.data;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

/**
 * The RoundResultDTO.class contains the round result related information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class RoundResultDTO {

    /**
     * A unique identifier for the round result.
     */
    @Nullable
    UUID id;

    /**
     * The text of the answer that the user selected.
     */
    @NotNull(message = "answer cannot be null")
    @NotBlank(message = "answer cannot be blank")
    String answer;
}
