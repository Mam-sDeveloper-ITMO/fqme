package fqme.column.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.Column;
import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.query.Query;

/**
 * Column realization for Boolean values.
 *
 * @see fqme.column.Column
 */
public class BooleanColumn extends Column<BooleanColumn, Boolean> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    public BooleanColumn(String name) {
        super(name);
    }

    /**
     * Factory method for creating a column.
     */
    public static BooleanColumn of(String name) {
        return new BooleanColumn(name);
    }

    /**
     * Return sql type of the column.
     *
     * @return BOOLEAN.
     */
    @Override
    public String _getSqlDefinition() {
        return "BOOLEAN";
    }

    /**
     * Convert value from the database to the java type.
     *
     * @param value expect Boolean
     * @return value converted to the java Boolean type.
     * @throws UnsupportedSqlType if value is not Boolean.
     */
    @Override
    public Boolean fromSqlType(Object value) throws UnsupportedSqlType {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        throw new UnsupportedSqlType(
                "Cannot convert value of type %s to Boolean".formatted(value.getClass().getName()));
    }

    /**
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     expect Boolean value.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException {
        if (value instanceof Boolean) {
            statement.setBoolean(index, (Boolean) value);
        } else {
            throw new UnsupportedValueType("Expected Boolean got %s instead.".formatted(value.getClass().getName()));
        }
    }

    /**
     * Return query for true checking.
     *
     * @see fqme.query.Query
     *
     * @return query for true checking.
     */
    public Query isTrue() {
        return new Query(this.getName() + " = true");
    }

    /**
     * Return query for false checking.
     *
     * @see fqme.query.Query
     *
     * @return query for false checking.
     */
    public Query isFalse() {
        return new Query(this.getName() + " = false");
    }
}
