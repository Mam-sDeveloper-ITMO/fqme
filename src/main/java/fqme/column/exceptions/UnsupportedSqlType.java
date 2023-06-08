package fqme.column.exceptions;

import lombok.experimental.StandardException;

/**
 * An UnsupportedSqlType exception is thrown when a column cannot deserialize a
 * value from a SQL type.
 *
 * @see fqme.column.Column#fromSqlType(Object)
 */
@StandardException
public class UnsupportedSqlType extends Exception {
}
