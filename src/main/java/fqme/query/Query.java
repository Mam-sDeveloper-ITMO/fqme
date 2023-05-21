package fqme.query;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

/**
 * Query class responsible for building SQL queries.
 *
 * It contains a sql query string and a list of arguments.
 *
 * Used by {@link fqme.view.StatementBuilder}
 */
@Data
public class Query {
    /**
     * SQL query string.
     */
    @NonNull
    private String whereClause;

    /**
     * List of arguments for query.
     */
    @NonNull
    private List<Object> whereArgs;

    /**
     * Constructor.
     *
     * @param whereClause SQL query string.
     * @param whereArgs   List of arguments.
     */
    public Query(String whereClause, Object... whereArgs) {
        this.whereClause = whereClause;
        this.whereArgs = Arrays.asList(whereArgs);
    }

    /**
     * Add another query to this one with AND operator.
     *
     * Also adds the other query's arguments to this one.
     *
     * @param other
     * @return
     */
    public Query and(Query other) {
        this.whereClause = "(%s) AND (%s)".formatted(whereClause, other.whereClause);
        this.whereArgs.addAll(Arrays.asList(other.whereArgs));
        return this;
    }

    /**
     * Add another query to this one with OR operator.
     *
     * Also adds the other query's arguments to this one.
     *
     * @param other
     * @return
     */
    public Query or(Query other) {
        this.whereClause = "(%s) OR (%s)".formatted(whereClause, other.whereClause);
        this.whereArgs.addAll(Arrays.asList(other.whereArgs));
        return this;
    }
}
