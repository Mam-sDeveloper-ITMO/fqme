package fqme.column;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;
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
public abstract class Column<T extends Column<T, K>, K> {
    /**
     * Name of the column in the table.
     * Must be the same as the name of the field in the model.
     */
    @Getter
    private final String name;

    /**
     * Define if column can be null. Default is true.
     */
    @Getter
    private boolean nullable = true;

    /**
     * Define if column is unique. Default is false.
     */
    @Getter
    private boolean unique = false;

    /**
     * Define if column is primary key. Default is false.
     */
    @Getter
    private boolean primary = false;

    /**
     * Define column nullable property.
     *
     * @param nullable nullable property.
     * @return this.
     * @throws IllegalArgumentException on trying to set nullable to true on primary
     *                                  key.
     */
    public T nullable(boolean nullable) {
        if (primary && nullable) {
            throw new IllegalArgumentException("Primary key cannot be nullable");
        }
        this.nullable = nullable;
        return (T) this;
    }

    /**
     * Define column as unique.
     *
     * @param unique unique property.
     * @return this.
     */
    public T unique() {
        this.unique = true;
        return (T) this;
    }

    /**
     * Define column as primary key.
     * Redefine nullable to false and unique to true.
     *
     * @param primary primary property.
     * @return this.
     */
    public T primary() {
        this.primary = true;
        this.nullable = false;
        this.unique = true;
        return (T) this;
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
        if (this.primary) {
            definition += " PRIMARY KEY";
        }
        if (this.unique && !this.primary) {
            definition += " UNIQUE";
        }
        if (this.nullable) {
            definition += " NULL";
        } else if (!this.nullable && !this.primary) {
            definition += " NOT NULL";
        }
        return definition;
    }

    /**
     * Convert value from the database to the java type.
     *
     * @param value value from the database.
     * @return value in java type.
     * @throws UnsupportedSqlType if value cannot be converted.
     */
    public abstract K fromSqlType(Object value) throws UnsupportedSqlType;

    /**
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     value to set.
     * @throws UnsupportedValueType if value cannot be converted.
     */
    public abstract void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException;

    /**
     * Return query for equal comparison.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for equal comparison.
     */
    public Query eq(K value) {
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
    public Query notEq(K value) {
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

    // /**
    // * PropertiesBuilder is used to set up properties of the column
    // * in safe way.
    // *
    // * @param <K> type of the column.
    // */
    // @RequiredArgsConstructor
    // protected static class PropertiesBuilder<K extends Column<?>> {
    // /**
    // * Instance of the column to set up.
    // */
    // private final K column;

    // /**
    // * Define if column can be null. Default is true.
    // */
    // private boolean nullable = true;

    // /**
    // * Define if column is unique. Default is false.
    // */
    // private boolean unique = false;

    // /**
    // * Define if column is primary key. Default is false.
    // */
    // private boolean primary = false;

    // /**
    // * Set nullable property of the column.
    // *
    // * @param nullable nullable property of the column.
    // * @return this.
    // * @throws IllegalArgumentException if try to set nullable to false for
    // primary
    // * key.
    // */
    // public PropertiesBuilder<K> nullable(Boolean nullable) {
    // if (this.primary && nullable != false) {
    // throw new IllegalArgumentException("Primary key cannot be nullable");
    // }
    // this.nullable = nullable;
    // return this;
    // }

    // /**
    // * Set unique property of the column.
    // *
    // * @param unique unique property of the column.
    // * @return this.
    // */
    // public PropertiesBuilder<K> unique() {
    // this.unique = true;
    // return this;
    // }

    // /**
    // * Set primary property of the column.
    // * Will redefine nullable to false and unique to true.
    // *
    // * @param primary primary property of the column.
    // * @return this.
    // */
    // public PropertiesBuilder<K> primary() {
    // this.primary = true;
    // this.unique = true; // will be ignored
    // this.nullable = false;
    // return this;
    // }

    // /**
    // * Set up properties to the column.
    // *
    // * @return column with set up properties.
    // */
    // public K build() {
    // column.setNullable(this.nullable);
    // column.setUnique(this.unique);
    // column.setPrimary(this.primary);
    // return column;
    // }
    // }
}
