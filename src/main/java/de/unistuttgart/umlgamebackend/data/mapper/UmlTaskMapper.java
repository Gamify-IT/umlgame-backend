package de.unistuttgart.umlgamebackend.data.mapper;

import de.unistuttgart.umlgamebackend.data.UmlTask;
import de.unistuttgart.umlgamebackend.data.UmlTaskDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UmlTaskMapper {
    UmlTaskDTO umlTaskToUmlTaskDTO(final UmlTask umlTask);

    UmlTask umlTaskDTOToUmlTask(final UmlTaskDTO umlTaskDTO);

    List<UmlTask> umlTaskDTOsToUmlTasks(final List<UmlTaskDTO> umlTaskDTOs);

    List<UmlTaskDTO> umlTasksToUmlTaskDTOs(final List<UmlTask> umlTasks);
}
