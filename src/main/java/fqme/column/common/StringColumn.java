package fqme.column.common;

import java.sql.PreparedStatement;

import fqme.column.Column;
import fqme.query.Query;
import fqme.query.QueryArgument;

/**
 * Column realization for string values.
 *
 * @see fqme.column.Column
 */
public class StringColumn extends Column<String> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    protected StringColumn(String name) {
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
    public String fromSqlType(Object value) {
        return (String) value;
    }

    /**
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     expect String value.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value) throws Exception {
        statement.setString(index, (String) value);
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
