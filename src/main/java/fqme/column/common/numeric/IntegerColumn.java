package fqme.column.common.numeric;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;

/**
 * A column that stores integer numbers.
 */
public class IntegerColumn extends NumericColumn<IntegerColumn, Integer> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    public IntegerColumn(String name) {
        super(name);
    }

    /**
     * Factory method for creating a column.
     */
    public static IntegerColumn of(String name) {
        return new IntegerColumn(name);
    }

    /**
     * Returns the SQL definition of the column.
     *
     * @return "INTEGER"
     */
    @Override
    protected String _getSqlDefinition() {
        return "INTEGER";
    }

    /**
     * Converts a value from SQL type to Java type.
     *
     * @param value must be an instance of Integer.
     * @return the converted value.
     * @throws UnsupportedSqlType if the value is not instance of Integer.
     */
    @Override
    public Integer fromSqlType(Object value) throws UnsupportedSqlType {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedSqlType("Value cannot be null.");
            }
            return null;
        } else
        if (value instanceof Integer) {
            return (Integer) value;
        }
        throw new UnsupportedSqlType(String.format("Expected Integer got %s instead.", value.getClass().getName()));
    }

    /**
     * Sets the value to the statement at the given index.
     *
     * @param statement the statement to set the value to.
     * @param index     the index of the value to set.
     * @param value     must be an instance of Integer.
     * @throws UnsupportedValueType if the value is not instance of Integer.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedValueType("Value cannot be null");
            }
            statement.setNull(index, java.sql.Types.INTEGER);
        } else
        if (value instanceof Integer) {
            statement.setInt(index, (Integer) value);
        } else {
            throw new UnsupportedValueType(String.format("Expected Integer got %s instead.", value.getClass().getName()));
        }
    }
}
