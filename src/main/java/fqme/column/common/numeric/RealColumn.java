package fqme.column.common.numeric;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;

/**
 * A column that stores real numbers.
 */
public class RealColumn extends NumericColumn<RealColumn, Float> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    public RealColumn(String name) {
        super(name);
    }

    /**
     * Factory method for creating a column.
     */
    public static RealColumn of(String name) {
        return new RealColumn(name);
    }

    /**
     * Returns the SQL definition of the column.
     *
     * @return "REAL"
     */
    @Override
    protected String _getSqlDefinition() {
        return "REAL";
    }

    /**
     * Converts a value from SQL type to Java type.
     *
     * @param value must be an instance of Float.
     * @return the converted value.
     * @throws UnsupportedSqlType if the value is not instance of Float.
     */
    @Override
    public Float fromSqlType(Object value) throws UnsupportedSqlType {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedSqlType("Value cannot be null.");
            }
            return null;
        } else if (value instanceof Float) {
            return (Float) value;
        }
        throw new UnsupportedSqlType(String.format("Expected Float got %s instead.", value.getClass().getName()));
    }

    /**
     * Sets the value to the statement at the given index.
     *
     * @param statement the statement to set the value to.
     * @param index     the index of the value to set.
     * @param value     must be an instance of Float.
     * @throws UnsupportedValueType if the value is not instance of Float.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedValueType("Value cannot be null.");
            }
            statement.setNull(index, java.sql.Types.REAL);
        } else if (value instanceof Float) {
            statement.setFloat(index, (Float) value);
        } else {
            throw new UnsupportedValueType(String.format("Expected Float got %s instead.", value.getClass().getName()));
        }
    }
}
