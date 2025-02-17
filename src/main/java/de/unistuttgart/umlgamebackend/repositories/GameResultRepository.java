package de.unistuttgart.umlgamebackend.repositories;

import de.unistuttgart.umlgamebackend.data.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameResultRepository extends JpaRepository<GameResult, Long> {
    List<GameResult> findByConfigurationAsUUID(UUID configurationId);
}
