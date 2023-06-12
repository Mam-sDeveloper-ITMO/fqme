// package fqme.column.common.numeric;
package fqme.column.common.numeric;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;

/**
 * A column that stores bigint numbers.
 */
public class BigIntColumn extends NumericColumn<BigIntColumn, Long> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    public BigIntColumn(String name) {
        super(name);
    }

    /**
     * Factory method for creating a column.
     */
    public static BigIntColumn of(String name) {
        return new BigIntColumn(name);
    }

    /**
     * Return sql type of the column.
     *
     * @return "BIGINT"
     */
    @Override
    protected String _getSqlDefinition() {
        return "BIGINT";
    }

    /**
     * Converts a value from SQL type to Java type.
     *
     * @param value must be an instance of Long.
     * @return the converted value.
     * @throws UnsupportedSqlType if the value is not instance of Long.
     */
    @Override
    public Long fromSqlType(Object value) throws UnsupportedSqlType {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedSqlType("Value cannot be null.");
            }
            return null;
        } else
        if (value instanceof Long) {
            return (Long) value;
        }
        throw new UnsupportedSqlType(String.format("Expected Long got %s instead.", value.getClass().getName()));
    }

    /**
     * Sets the value to the statement at the given index.
     *
     * @param statement the statement to set the value to.
     * @param index     the index of the value to set.
     * @param value     must be an instance of Long.
     * @throws UnsupportedValueType if the value is not instance of Long.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedValueType("Value cannot be null.");
            }
            statement.setNull(index, java.sql.Types.BIGINT);
        }
        else if (value instanceof Long) {
            statement.setLong(index, (Long) value);
        } else {
            throw new UnsupportedValueType(String.format("Expected Long got %s instead.", value.getClass().getName()));
        }
    }
}
