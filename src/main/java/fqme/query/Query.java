package fqme.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import lombok.Data;
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
    private List<QueryArgument<?, ?>> whereArgs;

    /**
     * Constructor.
     *
     * @param whereClause SQL query string.
     * @param whereArgs   List of arguments.
     *
     * @see fqme.query.QueryArgument
     */
    public Query(String whereClause, QueryArgument<?, ?>... whereArgs) {
        this.whereClause = whereClause;
        this.whereArgs = new ArrayList<>(Arrays.asList(whereArgs));
    }

    /**
     * Make negation of this query.
     *
     * @return this query with NOT operator.
     */
    public Query not() {
        this.whereClause = "(NOT (%s))".formatted(whereClause);
        return this;
    }

    /**
     * Add another query to this one with AND operator.
     *
     * Also adds the other query's arguments to this one.
     *
     * @param other
     * @return this query with union of where clause and arguments.
     *
     * @see fqme.query.QueryArgument
     */
    public Query and(Query other) {
        this.whereClause = "(%s) AND (%s)".formatted(whereClause, other.whereClause);
        this.whereArgs.addAll(other.whereArgs);
        return this;
    }

    /**
     * Add another query to this one with OR operator.
     *
     * Also adds the other query's arguments to this one.
     *
     * @param other
     * @return this query with union of where clause and arguments.
     *
     * @see fqme.query.QueryArgument
     */
    public Query or(Query other) {
        this.whereClause = "(%s) OR (%s)".formatted(whereClause, other.whereClause);
        this.whereArgs.addAll(other.whereArgs);
        return this;
    }

    /**
     * Join queries with AND operator.
     *
     * @param queries queries to join.
     * @return this query with union of where clause and arguments.
     * @see fqme.query.QueryArgument
     */
    public static Query all(Iterable<Query> queries) {
        Iterator<Query> iterator = queries.iterator();
        Query query = iterator.next();
        while (iterator.hasNext()) {
            query.and(iterator.next());
        }
        return query;
    }

    /**
     * Join this queries with OR operator.
     *
     * @param queries queries to join.
     * @return this query with union of where clause and arguments.
     * @see fqme.query.QueryArgument
     */
    public static Query any(Iterable<Query> queries) {
        Iterator<Query> iterator = queries.iterator();
        Query query = iterator.next();
        while (iterator.hasNext()) {
            query.and(iterator.next());
        }
        return query;
    }
}
