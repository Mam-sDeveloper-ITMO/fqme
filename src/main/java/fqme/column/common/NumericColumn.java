package fqme.column.common;

import fqme.column.Column;
import fqme.query.Query;

/**
 * Generic column realization for all Numeric values.
 *
 * @see fqme.column.Column
 */
public class NumericColumn<T extends Number> extends Column<T> {
    /**
     * Default constructor.
     *
     * @param name
     */
    public NumericColumn(String name) {
        super(name);
    }

    /**
     * Return query for greater comparison.
     *
     * @param value value to compare with.
     * @return query for greater comparison.
     */
    public Query greater(T value) {
        return new Query(this.getName() + " > ?", value);
    }

    /**
     * Return query for less comparison.
     *
     * @param value value to compare with.
     * @return query for less comparison.
     */
    public Query less(T value) {
        return new Query(this.getName() + " < ?", value);
    }

    /**
     * Return query for greater or equal comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for equal comparison.
     */
    public Query greaterOrEqual(T value) {
        return new Query(this.getName() + " >= ?", value);
    }

    /**
     * Return query for less or equal comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for not equal comparison.
     */
    public Query lessOrEqual(T value) {
        return new Query(this.getName() + " <= ?", value);
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
        return new Query(this.getName() + " BETWEEN ? AND ?", from, to);
    }

    /**
     * Return query for not between comparison.
     *
     * @see fqme.query.Query
     *
     * @param from minimum border value.
     * @param to   maximum border value.
     * @return query for not between comparison.
     */
    public Query notBetween(T from, T to) {
        return new Query(this.getName() + " NOT BETWEEN ? AND ?", from, to);
    }
}
