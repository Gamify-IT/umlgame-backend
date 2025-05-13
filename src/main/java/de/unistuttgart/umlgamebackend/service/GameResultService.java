package de.unistuttgart.umlgamebackend.service;

import de.unistuttgart.umlgamebackend.clients.ResultClient;
import de.unistuttgart.umlgamebackend.data.*;
import de.unistuttgart.umlgamebackend.repositories.GameResultRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This service handles the logic for the GameResultController.class
 */
@Service
@Slf4j
@Transactional
public class GameResultService {

    @Autowired
    ResultClient resultClient;

    @Autowired
    GameResultRepository gameResultRepository;


    private static int hundredScoreCount = 0;

    /**
     * Casts a GameResultDTO to GameResult and saves it in the Database
     *
     * @param gameResultDTO extern gameResultDTO
     * @param userId id of the user
     * @param accessToken accessToken of the user
     * @throws IllegalArgumentException if at least one of the arguments is null
     */
    public void saveGameResult(
            final @Valid GameResultDTO gameResultDTO,
            final String userId,
            final String accessToken
    ) {
        if (gameResultDTO == null || userId == null || accessToken == null) {
            throw new IllegalArgumentException("gameResultDTO or userId is null");
        }
        final int resultScore = calculateResultScore();

        final int rewards = calculateRewards(resultScore);
        gameResultDTO.setScore(resultScore);
        gameResultDTO.setRewards(rewards);

        final OverworldResultDTO resultDTO = new OverworldResultDTO(
                gameResultDTO.getConfigurationAsUUID(),
                resultScore,
                userId,
                rewards
        );
        try {
            resultClient.submit(accessToken, resultDTO);
            final GameResult result = new @Valid GameResult(
                    gameResultDTO.getScore(),
                    rewards,
                    gameResultDTO.getConfigurationAsUUID(),
                    userId
            );
            gameResultRepository.save(result);
        } catch (final FeignException.BadGateway badGateway) {
            final String warning =
                    "The Overworld backend is currently not available. The result was NOT saved. Please try again later";
            log.error(warning + badGateway);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, warning);
        } catch (final FeignException.NotFound notFound) {
            final String warning = "The result could not be saved. Unknown User";
            log.error(warning + notFound);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, warning);
        }
    }

    /**
     * calculates the score a player made
     *
     * @return score as int in %
     * @throws IllegalArgumentException if correctAnswers < 0 || numberOfQuestions < correctAnswers
     */
    public int calculateResultScore() {
        return 0;
    }

    /**
     * Calculates the rewards for a finite quiz round based on the score achieved in the current round.
     * <p>
     * Reward logic:
     * - First three rounds with a score of 100%: 10 coins per round.
     * - After the third round, if the score is 100%: 5 coins per round.
     * - If the score is less than 100%, the reward is calculated as: score / 10.
     *
     * @param resultScore the score achieved in the game (must be >= 0)
     * @return the number of rewards as an integer
     * @throws IllegalArgumentException if resultScore is less than 0
     */
    private int calculateRewards(final int resultScore) {
        if (resultScore < 0) {
            throw new IllegalArgumentException("Result score cannot be less than zero");
        }
        if (resultScore == 100 && hundredScoreCount < 3) {
            hundredScoreCount++;
            return 10;
        } else if (resultScore == 100 && hundredScoreCount >= 3) {
            return 5;
        }
        return resultScore / 10;
    }
}
