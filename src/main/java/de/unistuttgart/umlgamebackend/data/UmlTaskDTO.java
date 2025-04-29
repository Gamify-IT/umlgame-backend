package de.unistuttgart.umlgamebackend.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UmlTaskDTO {

    @Id
    String id;

    String graph;

    String text;

    @Enumerated(EnumType.STRING)
    TaskType taskType;

    public enum TaskType {
        COMPLETION, ERRORHUNT, CODETOUML, UMLTOCODE
    }
}
