package fqme.column;

import fqme.query.Query;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Column class represent single data column in the model.
 *
 * It contains attribute name and methods for sql queries
 * {@link fqme.query.Query} generation.
 *
 * Columns must be used in models as {@code public static final} members.
 * It is quite inconsistent with java conventions,
 * but it is the only way to make it simple in usage.
 *
 * Example:
 *
 * <pre>
 * &#64;Table("groups")
 * public class GroupModel extends Model {
 *     &#64;NonNull
 *     private String name;
 *     public static final StringColumn name_ = new StringColumn("name");
 *
 *     &#64NonNull
 *     private Integer age;
 *     public static final NumericColumn<Integer> age_ = new NumericColumn<>("age");
 * }
 * </pre>
 */
@RequiredArgsConstructor
public abstract class Column<T> {
    /**
     * Name of the column in the table.
     * Must be the same as the name of the field in the model.
     */
    @Getter
    private final String name;

    /**
     * Return query for equal comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for equal comparison.
     */
    public Query equal(T value) {
        return new Query(this.getName() + " = ?", value);
    }

    /**
     * Return query for not equal comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for not equal comparison.
     */
    public Query notEqual(T value) {
        return new Query(this.getName() + " <> ?", value);
    }

    /**
     * Return query for greater comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for greater comparison.
     */
    public Query isNull() {
        return new Query(this.getName() + " IS NULL");
    }
}
