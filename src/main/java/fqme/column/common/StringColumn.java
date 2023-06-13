package fqme.column.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.Column;
import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.query.Query;
import fqme.query.QueryArgument;

/**
 * Column realization for string values.
 *
 * @see fqme.column.Column
 */
public class StringColumn extends Column<StringColumn, String> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    public StringColumn(String name) {
        super(name);
    }

    /**
     * Factory method for creating a column.
     */
    public static StringColumn of(String name) {
        return new StringColumn(name);
    }

    /**
     * Return sql type of the column.
     *
     * @return TEXT.
     */
    @Override
    public String _getSqlDefinition() {
        return "TEXT";
    }

    /**
     * Convert value from the database to the java type.
     *
     * @param value expect String
     * @return value converted to the java String type.
     */
    @Override
    public String fromSqlType(Object value) throws UnsupportedSqlType {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedSqlType("Value cannot be null.");
            }
            return null;
        } else if (value instanceof String) {
            return (String) value;
        }
        throw new UnsupportedSqlType(String.format("Expected String got %s instead.", value.getClass().getName()));
    }

    /**
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     expect String value.
     * @throws UnsupportedValueType if value is not String.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedValueType("Value cannot be null.");
            }
            statement.setString(index, null);
        } else if (value instanceof String) {
            statement.setString(index, (String) value);
        } else {
            throw new UnsupportedValueType(
                    String.format("Expected String got %s instead.", value.getClass().getName()));
        }
    }

    /**
     * Return query for like matching.
     *
     * @see fqme.query.Query
     *
     * @param value pattern to match with.
     * @return query for like matching.
     */
    public Query like(String value) {
        return new Query(this.getName() + " LIKE ?", QueryArgument.of(this, value));
    }

    /**
     * Return query for prefix matching.
     *
     * @see fqme.query.Query
     *
     * @param value prefix to match with.
     * @return query for prefix matching.
     */
    public Query prefix(String value) {
        return new Query(this.getName() + " LIKE ?", QueryArgument.of(this, value + "%"));
    }

    /**
     * Return query for suffix matching.
     *
     * @see fqme.query.Query
     *
     * @param value suffix to match with.
     * @return query for suffix matching.
     */
    public Query suffix(String value) {
        return new Query(this.getName() + " LIKE ?", QueryArgument.of(this, "%" + value));
    }
}
