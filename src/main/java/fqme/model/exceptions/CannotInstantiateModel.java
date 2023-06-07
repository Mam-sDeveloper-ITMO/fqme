package fqme.model.exceptions;

import lombok.experimental.StandardException;

/**
 * Exception thrown when a model cannot be instantiated by {@code ModelFactory}.
 *
 * @see fqme.model.reflection.ModelReflection
 * @see fqme.model.ModelFactory
 */
@StandardException
public class CannotInstantiateModel extends RuntimeException {
}
