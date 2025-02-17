package de.unistuttgart.umlgamebackend.repositories;

import de.unistuttgart.umlgamebackend.data.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, UUID> {}
