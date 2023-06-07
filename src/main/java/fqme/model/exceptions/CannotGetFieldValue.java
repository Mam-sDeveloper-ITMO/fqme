package fqme.model.exceptions;

import lombok.experimental.StandardException;

/**
 * Exception thrown when {@code FieldsSupplier} cannot get a field value.
 *
 * @see fqme.model.reflection.FieldsSupplier
 * @see fqme.model.reflection.ModelReflection#getFieldsSupplier()
 */
@StandardException
public class CannotGetFieldValue extends RuntimeException {
}
