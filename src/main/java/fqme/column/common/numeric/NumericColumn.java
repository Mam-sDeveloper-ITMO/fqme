package fqme.column.common.numeric;

import java.sql.PreparedStatement;

import fqme.column.Column;
import fqme.query.Query;
import fqme.query.QueryArgument;

/**
 * Generic column realization for all Numeric values.
 *
 * @see fqme.column.Column
 */
public class NumericColumn<T extends Number> extends Column<T> {
    /**
     * Type of the column.
     */
    private final Class<T> type;

    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    protected NumericColumn(String name, Class<T> type) {
        super(name);
        this.type = type;
    }

    /**
     * Factory method for creating a column.
     */
    public static <T extends Number> NumericColumn<T> of(String name, Class<T> type) {
        return new NumericColumn<>(name, type);
    }

    /**
     * Return sql type of the column.
     *
     * @return NUMERIC.
     */
    @Override
    public String _getSqlDefinition() {
        if (type == Integer.class) {
            return "INTEGER";
        } else if (type == Long.class) {
            return "BIGINT";
        } else if (type == Float.class) {
            return "REAL";
        } else if (type == Double.class) {
            return "DOUBLE";
        }
        throw new RuntimeException("Unsupported numeric type: " + type);
    }

    /**
     * Convert value from the database to the java type.
     *
     * @param value expect subclass of Numeric
     * @return value converted to the java Numeric type.
     */
    @Override
    public T fromSqlType(Object value) throws Exception {
        if (type == Integer.class) {
            return (T) Integer.valueOf((int) value);
        } else if (type == Long.class) {
            return (T) Long.valueOf((long) value);
        } else if (type == Float.class) {
            return (T) Float.valueOf((float) value);
        } else if (type == Double.class) {
            return (T) Double.valueOf((double) value);
        }
        throw new RuntimeException("Unsupported numeric type: " + type);
    }

    /**
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     expect subclass of Numeric.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value) throws Exception {
        if (this.type == Integer.class) {
            statement.setInt(index, (int) value);
        } else if (this.type == Long.class) {
            statement.setLong(index, (long) value);
        } else if (this.type == Float.class) {
            statement.setFloat(index, (float) value);
        } else if (this.type == Double.class) {
            statement.setDouble(index, (double) value);
        } else {
            throw new RuntimeException("Unsupported numeric type: " + this.type);
        }
    }

    /**
     * Return query for greater comparison.
     *
     * @param value value to compare with.
     * @return query for greater comparison.
     */
    public Query gt(T value) {
        return new Query(this.getName() + " > ?", QueryArgument.of(this, value));
    }

    /**
     * Return query for less comparison.
     *
     * @param value value to compare with.
     * @return query for less comparison.
     */
    public Query lt(T value) {
        return new Query(this.getName() + " < ?", QueryArgument.of(this, value));
    }

    /**
     * Return query for greater or equal comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for equal comparison.
     */
    public Query geq(T value) {
        return new Query(this.getName() + " >= ?", QueryArgument.of(this, value));
    }

    /**
     * Return query for less or equal comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for not equal comparison.
     */
    public Query leq(T value) {
        return new Query(this.getName() + " <= ?", QueryArgument.of(this, value));
    }

    /**
     * Return query for between comparison.
     *
     * @see fqme.query.Query
     *
     * @param from minimum border value.
     * @param to   maximum border value.
     * @return query for between comparison.
     */
    public Query between(T from, T to) {
        return new Query(this.getName() + " BETWEEN ? AND ?", QueryArgument.of(this, from), QueryArgument.of(this, to));
    }
}
