package de.unistuttgart.umlgamebackend.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UmlTask {

    @Id
    @GeneratedValue(generator = "uuid")
    UUID id;

    String taskNumber;

    String graph;

    String text;

    @Enumerated(EnumType.STRING)
    TaskType taskType;

    public enum TaskType {
        COMPLETION, ERRORHUNT, CODETOUML, UMLTOCODE
    }
}
