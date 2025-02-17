package de.unistuttgart.umlgamebackend.clients;

import de.unistuttgart.umlgamebackend.data.OverworldResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This client submits OverworldResultDTOs to the overworld backend
 */
@FeignClient(value = "resultClient", url = "${overworld.url}/internal")
public interface ResultClient {
    /**
     * Submits the resultDTO to the Overworld-Backend
     *
     * @param accessToken the users access token
     * @param resultDTO the player submitted result, trimmed down to the data needed for the overworld
     */
    @PostMapping("/submit-game-pass")
    void submit(@CookieValue("access_token") final String accessToken, final OverworldResultDTO resultDTO);
}
