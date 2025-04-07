package de.unistuttgart.umlgamebackend.data;

/**
 * Enum representing the possible types of tasks in the UML game.
 */
public enum TaskType {
    COMPLETION(0),
    ERRORHUNT(1),
    CODETOUML(2),
    UMLTOCODE(3);

    private final int value;

    TaskType(int value) {
        this.value = value;
    }
}
