package de.unistuttgart.umlgamebackend.controller;

import de.unistuttgart.umlgamebackend.data.statistic.ProblematicQuestion;
import de.unistuttgart.umlgamebackend.data.statistic.TimeSpentDistribution;
import de.unistuttgart.umlgamebackend.service.StatisticService;
import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This controller handles all game-configuration-related REST-APIs
 */
@Tag(name = "Statistic", description = "Get statistics of minigame configurations")
@RestController
@RequestMapping("/statistics")
@Import({ JWTValidatorService.class })
@Slf4j
@Validated
public class StatisticController {


}
