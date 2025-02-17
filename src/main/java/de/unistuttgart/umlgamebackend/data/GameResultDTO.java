package de.unistuttgart.umlgamebackend.data;

import de.unistuttgart.umlgamebackend.Constants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * The GameResultDTO.class contains all data that is saved after one finitequiz game.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class GameResultDTO {

    /**
     * A unique identifier for the game result.
     */
    @Nullable
    private UUID id;


    /**
     * The score achieved in the game.
     */
    @Min(value = Constants.MIN_SCORE, message = "Score cannot be less than " + Constants.MIN_SCORE)
    @Max(value = Constants.MAX_SCORE, message = "Score cannot be higher than " + Constants.MAX_SCORE)
    private long score;


    /**
     * The ID of the configuration used for this game.
     */
    @NotNull(message = "configurationAsUUID cannot be null")
    private UUID configurationAsUUID;

    /**
     * The time spent in seconds on the game for this run.
     */
    private long timeSpent;

    /**
     * The reward-coins that the player achieved in the current round.
     */
    @Min(value = Constants.MIN_REWARDS, message = "Rewards cannot be less than " + Constants.MIN_REWARDS)
    @Max(value = Constants.MAX_REWARDS, message = "Rewards cannot be higher than " + Constants.MAX_REWARDS)
    private int rewards;

    public GameResultDTO(
        final long score,
        final long timeSpent,
        final int rewards,
        final UUID configurationAsUUID
    ) {
        this.score = score;
        this.timeSpent = timeSpent;
        this.rewards = rewards;
        this.configurationAsUUID = configurationAsUUID;
    }

    public boolean equalsContent(final GameResultDTO other) {
        if (this == other) return true;
        if (other == null) return false;

        if (id != other.id) return false;
        if (score != other.score) return false;
        if(rewards != other.rewards) return false;
        return configurationAsUUID.equals(other.configurationAsUUID);
    }
}
