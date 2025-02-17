package de.unistuttgart.umlgamebackend.data;

import de.unistuttgart.umlgamebackend.Constants;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The GameResult.class contains all data that is saved after one finitequiz game.
 */
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class GameResult {

    /**
     * A unique identifier for the game result.
     */
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    /**
     * The score achieved in the game.
     */
    @Min(value = Constants.MIN_SCORE, message = "Score cannot be less than " + Constants.MIN_SCORE)
    @Max(value = Constants.MAX_SCORE, message = "Score cannot be higher than " + Constants.MAX_SCORE)
    private long score;

    /**
     * The ID of the configuration that was used for the game.
     */
    @NotNull(message = "configurationAsUUID cannot be null")
    private UUID configurationAsUUID;

    @NotNull(message = "playerId cannot be null")
    private String playerId;

    @NotNull(message = "playedTime cannot be null")
    @CreationTimestamp
    private Date playedTime = new Date();

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


    public GameResult(
        final long score,
        final long timeSpent,
        final int rewards,
        final UUID configurationAsUUID,
        final String playerId
    ) {
        this.score = score;
        this.timeSpent = timeSpent;
        this.rewards = rewards;
        this.configurationAsUUID = configurationAsUUID;
        this.playerId = playerId;
    }
}
