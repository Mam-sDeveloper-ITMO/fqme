package fqme.column;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fqme.query.Query;
import fqme.query.QueryArgument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
 *
 * @param <T> type, that column will deserialize from database. Should be the
 *            same as type of {@code ColumnData} field in the model.
 *
 * @see fqme.model.Model
 * @see fqme.query.Query
 * @see fqme.query.QueryArgument
 * @see fqme.model.reflection.ColumnData
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
     * List of modifiers for the column.
     * Modifiers are used to generate sql query for table creation.
     */
    private final Set<String> modifiers = new HashSet<>();

    /**
     * Return copy of modifiers set.
     *
     * @return copy of modifiers set.
     */
    public Set<String> getModifiers() {
        return new HashSet<>(this.modifiers);
    }

    /**
     * Add modifier to the column.
     *
     * @param modifier modifier to add.
     */
    public void addModifier(String modifier) {
        this.modifiers.add(modifier);
    }

    /**
     * Remove modifier from the column.
     */
    public Boolean removeModifier(String modifier) {
        return this.modifiers.remove(modifier);
    }

    /**
     * Define if the column is primary key.
     */
    @Setter(AccessLevel.PROTECTED)
    protected Boolean primary = null;

    /**
     * Primary key flag getter. Default value is {@code false}.
     *
     * @return primary flag.
     */
    public Boolean isPrimary() {
        return this.primary == null ? false : this.primary;
    }

    /**
     * Return column with changed primary flag.
     * This value cannot be changed after the column is created.
     *
     * @param primary primary flag.
     */
    public static <K extends Column<?>> K asPrimary(K column) {
        if (column.primary != null) {
            throw new IllegalArgumentException("Primary key cannot be set twice");
        }
        column.setPrimary(true);
        column.addModifier("PRIMARY KEY");
        return column;
    }

    /**
     * Define if the column is nullable.
     */
    @Setter(AccessLevel.PROTECTED)
    protected Boolean nullable = null;

    /**
     * Nullable flag getter. Default value is {@code true}.
     *
     * @return nullable flag.
     */
    public Boolean isNullable() {
        return this.nullable == null ? true : this.nullable;
    }

    /**
     * Return sql definition of the column.
     *
     * @return sql type of the column.
     */
    protected abstract String _getSqlDefinition();

    /**
     * Return sql definition of the column with modifiers.
     */
    public final String getSqlDefinition() {
        String definition = this._getSqlDefinition();
        for (String modifier : this.modifiers) {
            definition += " " + modifier;
        }
        return definition;
    }

    /**
     * Convert value from the database to the java type.
     *
     * @param value value from the database.
     * @return value in java type.
     */
    public abstract T fromSqlType(Object value) throws Exception;

    /**
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     value to set.
     */
    public abstract void setToStatement(PreparedStatement statement, Integer index, Object value) throws Exception;

    /**
     * Return query for equal comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for equal comparison.
     */
    public Query eq(T value) {
        return new Query(this.getName() + " = ?", QueryArgument.of(this, value));
    }

    /**
     * Return query for not equal comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for not equal comparison.
     */
    public Query notEq(T value) {
        return new Query(this.getName() + " <> ?", QueryArgument.of(this, value));
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
