package fqme.query;

import fqme.column.Column;
import lombok.Data;

/**
 * Structure for query arguments.
 *
 * Contains value and {@code Column}, representing the column to which the value
 * belongs.
 *
 * Column will be used for value serialization/deserialization
 * in context of sql types.
 */
@Data(staticConstructor = "of")
public class QueryArgument<T extends Column<T, K>, K> {
    /**
     * Column to which the value belongs.
     */
    private final Column<T, K> column;

    /**
     * Value of the argument.
     */
    private final Object value;
}
