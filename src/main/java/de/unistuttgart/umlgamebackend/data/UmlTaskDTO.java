package de.unistuttgart.umlgamebackend.data;

import java.util.UUID;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UmlTaskDTO {

    @Id
    @GeneratedValue(generator = "uuid")
    UUID id;

    String taskNumber;

    String graph;

    String text;

    @Enumerated(EnumType.STRING)
    TaskType taskType;

    public enum TaskType {
        COMPLETION,
        ERRORHUNT,
        CODETOUML,
        UMLTOCODE,
    }
}
