package de.unistuttgart.umlgamebackend.errorhandling;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public final class ApiError {

    private final HttpStatus status;
    private final List<String> errors;

    public ApiError(final HttpStatus status, final List<String> errors) {
        this.status = status;
        this.errors = errors;
    }
}
