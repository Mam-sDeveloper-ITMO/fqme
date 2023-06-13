package fqme.column.common.numeric;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;

/**
 * A column that stores double numbers.
 */
public class DoubleColumn extends NumericColumn<DoubleColumn, Double> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    public DoubleColumn(String name) {
        super(name);
    }

    /**
     * Factory method for creating a column.
     */
    public static DoubleColumn of(String name) {
        return new DoubleColumn(name);
    }

    /**
     * Return sql type of the column.
     *
     * @return "DOUBLE PRECISION"
     */
    @Override
    protected String _getSqlDefinition() {
        return "DOUBLE PRECISION";
    }

    /**
     * Converts a value from SQL type to Java type.
     *
     * @param value must be an instance of Double.
     * @return the converted value.
     * @throws UnsupportedSqlType if the value is not instance of Double.
     */
    @Override
    public Double fromSqlType(Object value) throws UnsupportedSqlType {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedSqlType("Value cannot be null.");
            }
            return null;
        } else if (value instanceof Double) {
            return (Double) value;
        }
        throw new UnsupportedSqlType(String.format("Expected Double got %s instead.", value.getClass().getName()));
    }

    /**
     * Sets the value to the statement at the given index.
     *
     * @param statement the statement to set the value to.
     * @param index     the index of the value to set.
     * @param value     must be an instance of Double.
     * @throws UnsupportedValueType if the value is not instance of Double.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedValueType("Value cannot be null.");
            }
            statement.setNull(index, java.sql.Types.DOUBLE);

        } else if (value instanceof Double) {
            statement.setDouble(index, (Double) value);
        } else {
            throw new UnsupportedValueType(
                    String.format("Expected Double got %s instead.", value.getClass().getName()));
        }
    }
}
