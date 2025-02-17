package de.unistuttgart.umlgamebackend.service;

import de.unistuttgart.umlgamebackend.data.Configuration;
import de.unistuttgart.umlgamebackend.data.GameResult;
import de.unistuttgart.umlgamebackend.data.statistic.ProblematicQuestion;
import de.unistuttgart.umlgamebackend.data.statistic.TimeSpentDistribution;
import de.unistuttgart.umlgamebackend.repositories.GameResultRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class StatisticService {

    static final int MAX_PROBLEMATIC_QUESTIONS = 5;
    static final int[] TIME_SPENT_DISTRIBUTION_PERCENTAGES = {0, 25, 50, 75, 100};

    @Autowired
    private ConfigService configService;

    @Autowired
    private GameResultRepository gameResultRepository;


    /**
     * Sorts a list of game results by played time
     *
     * @param gameResults the list of game results to sort
     */
    private void sortGameResultsByPlayedTime(List<GameResult> gameResults) {
        gameResults.sort((o1, o2) -> {
            if (o1.getTimeSpent() > o2.getTimeSpent()) {
                return 1;
            } else if (o1.getTimeSpent() < o2.getTimeSpent()) {
                return -1;
            } else {
                return 0;
            }
        });
    }
}
