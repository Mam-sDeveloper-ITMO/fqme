package fqme.model.exceptions;

/**
 * Exception thrown when suitable constructor not exists.
 *
 * Ensure that you have a constructor for all fields, annotated with
 * {@code ColumnData}
 * or use {@code lombok.AllArgsConstructor} annotation.
 *
 * @see fqme.model.Model
 * @see fqme.model.reflection.ModelReflection
 * @see fqme.column.ColumnData
 * @see lombok.AllArgsConstructor
 */
public class SuitableConstructorNotFound extends RuntimeException {
    public SuitableConstructorNotFound() {
        super("Suitable constructor not found. Ensure that you have a constructor for all fields, annotated with @ColumnData or use lombok.AllArgsConstructor annotation.");
    }
}
