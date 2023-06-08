package fqme.column.exceptions;

import lombok.experimental.StandardException;

/**
 * An UnsupportedValueType exception is thrown when a column does not support a
 * value type.
 *
 * @see fqme.column.Column#setToStatement(java.sql.PreparedStatement, Integer,
 *      Object)
 */
@StandardException
public class UnsupportedValueType extends Exception {
}
