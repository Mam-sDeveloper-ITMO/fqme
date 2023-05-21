package fqme.column.common;

import fqme.column.Column;
import fqme.query.Query;

/**
 * Column realization for Boolean values.
 *
 * @see fqme.column.Column
 */
public class BooleanColumn extends Column<Boolean> {
    /**
     * Default constructor.
     *
     * @param name
     */
    public BooleanColumn(String name) {
        super(name);
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
