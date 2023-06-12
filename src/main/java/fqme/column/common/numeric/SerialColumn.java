package fqme.column.common.numeric;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;

/**
 * Column realization for serial values.
 */
public class SerialColumn extends NumericColumn<SerialColumn, Integer> {
    /**
     * Define if the column can be null.
     *
     * Nullable columns, skipped in the insert queries
     * if the value is null.
     */

    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    public SerialColumn(String name) {
        super(name);
    }

    /**
     * Factory method for creating a column.
     */
    public static SerialColumn of(String name) {
        return new SerialColumn(name);
    }

    /**
     * Return sql type of the column.
     *
     * @return SERIAL.
     */
    @Override
    public String _getSqlDefinition() {
        return "SERIAL";
    }

    /**
     * Convert value from the database to the java type.
     *
     * @param value expect Integer
     * @return value converted to the java Integer type.
     * @throws UnsupportedSqlType if value is not Integer.
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
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     expect Integer value.
     * @throws UnsupportedValueType if value is not Integer.
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
