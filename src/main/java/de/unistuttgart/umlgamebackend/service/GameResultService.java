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

    public void saveGameResult() {
    }

    /**
     * calculates the score a player made
     *
     * @param correctAnswers    correct answer count
     * @param numberOfQuestions available question count
     * @return score as int in %
     * @throws IllegalArgumentException if correctAnswers < 0 || numberOfQuestions < correctAnswers
     */
    public int calculateResultScore(final int correctAnswers, final int numberOfQuestions) {
        if (correctAnswers < 0 || numberOfQuestions < correctAnswers) {
            throw new IllegalArgumentException(
                    String.format(
                            "correctAnswers (%s) or numberOfQuestions (%s) is not possible",
                            correctAnswers,
                            numberOfQuestions
                    )
            );
        }
        return (int) ((100.0 * correctAnswers) / numberOfQuestions);
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
