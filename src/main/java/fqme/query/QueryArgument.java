package fqme.query;

import fqme.column.Column;
import lombok.Data;
import lombok.Getter;

/**
 * Structure for query arguments.
 *
 * Contains value and {@code Column}, representing the column to which the value
 * belongs.
 *
 * Column will be used for value serialization/deserialization
 * in context of sql types.
 */
@Data
public class QueryArgument<T extends Column<T, K>, K> {
    /**
     * Column to which the value belongs.
     */
    @Getter
    private final Column<T, K> column;

    /**
     * Value of the argument.
     */
    @Getter
    private final Object value;

    /**
     * Factory method for creating a query argument.
     *
     * @param column column to which the value belongs.
     * @param value  value of the argument.
     * @return query argument.
     */
    public static <T extends Column<T, K>, K> QueryArgument<T, K> of(Column<T, K> column, Object value) {
        return new QueryArgument<>(column, value);
    }
}
