package fqme.column.common.numeric;

import fqme.column.Column;
import fqme.query.Query;
import fqme.query.QueryArgument;

/**
 * Generic column realization for all Numeric values.
 *
 * @see fqme.column.Column
 */
public abstract class NumericColumn<T extends Column<T, K>, K extends Number> extends Column<T, K> {
    /**
     * Default constructor.
     *
     * @param name name of the clumn.
     */
    protected NumericColumn(String name) {
        super(name);
    }

    /**
     * Return query for greater comparison.
     *
     * @param value value to compare with.
     * @return query for greater comparison.
     */
    public Query gt(K value) {
        return new Query(this.getName() + " > ?", QueryArgument.of(this, value));
    }

    /**
     * Return query for less comparison.
     *
     * @param value value to compare with.
     * @return query for less comparison.
     */
    public Query lt(K value) {
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
    public Query geq(K value) {
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
    public Query leq(K value) {
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
    public Query between(K from, K to) {
        return new Query(this.getName() + " BETWEEN ? AND ?", QueryArgument.of(this, from), QueryArgument.of(this, to));
    }
}
