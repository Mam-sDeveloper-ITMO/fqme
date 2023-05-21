package fqme.column.common;

import fqme.column.Column;
import fqme.query.Query;

/**
 * Column realization for string values.
 *
 * @see fqme.column.Column
 */
public class StringColumn extends Column<String> {
    /**
     * Default constructor.
     *
     * @param name
     */
    public StringColumn(String name) {
        super(name);
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
        return new Query(this.getName() + " LIKE ?", value);
    }

    /**
     * Return query for not like matching.
     *
     * @see fqme.query.Query
     *
     * @param value pattern to match with.
     * @return query for not like matching.
     */
    public Query notLike(String value) {
        return new Query(this.getName() + " NOT LIKE ?", value);
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
        return new Query(this.getName() + " LIKE ?", value + "%");
    }

    /**
     * Return query for not prefix matching.
     *
     * @see fqme.query.Query
     *
     * @param value prefix to match with.
     * @return query for not prefix matching.
     */
    public Query notPrefix(String value) {
        return new Query(this.getName() + " NOT LIKE ?", value + "%");
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
        return new Query(this.getName() + " LIKE ?", "%" + value);
    }

    /**
     * Return query for not suffix matching.
     *
     * @see fqme.query.Query
     *
     * @param value suffix to match with.
     * @return query for not suffix matching.
     */
    public Query notSuffix(String value) {
        return new Query(this.getName() + " NOT LIKE ?", "%" + value);
    }
}
