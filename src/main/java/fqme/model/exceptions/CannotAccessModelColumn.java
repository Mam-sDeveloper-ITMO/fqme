package fqme.model.exceptions;

import lombok.experimental.StandardException;

/**
 * Exception thrown when a model column cannot be accessed.
 *
 * @see fqme.model.reflection.ModelReflection#buildColumns
 */
@StandardException
public class CannotAccessModelColumn extends RuntimeException {
}
