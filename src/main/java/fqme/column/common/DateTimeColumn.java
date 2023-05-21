package fqme.column.common;

import java.time.LocalDateTime;

import fqme.column.Column;
import fqme.query.Query;

/**
 * Column realization for DateTime values.
 *
 * @see fqme.column.Column
 */
public class DateTimeColumn extends Column<LocalDateTime> {
    /**
     * Default constructor.
     *
     * @param name
     */
    public DateTimeColumn(String name) {
        super(name);
    }

    /**
     * Return query for before checking.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for before checking.
     */
    public Query before(LocalDateTime value) {
        return new Query("<", value);
    }

    /**
     * Return query for after checking.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for after checking.
     */
    public Query after(LocalDateTime value) {
        return new Query(">", value);
    }

    /**
     * Return query for between checking.
     *
     * @see fqme.query.Query
     *
     * @param startTime start time to compare with.
     * @param endTime   end time to compare with.
     * @return query for between checking.
     */
    public Query between(LocalDateTime startTime, LocalDateTime endTime) {
        return new Query("BETWEEN", startTime, endTime);
    }
}
