package fqme.model.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ColumnData annotation is used to mark fields in the model,
 * that will be used as data for the column.
 *
 * Example:
 *
 * <pre>
 * &#64;Table("groups")
 * public class TestModel extends Model<TestModel> {
 *     &#64;ColumnData
 *     private Integer id;
 *     public static final SerialColumn id_ = asPrimary(SerialColumn.of("id"));
 *
 *     &#64;ColumnData
 *     &#64;NonNull
 *     private String name;
 *     public static final StringColumn name_ = StringColumn.of("name");
 *
 *     &#64;ColumnData
 *     &#64;NonNull
 *     private Integer x;
 *     public static final IntegerColumn x_ = IntegerColumn.of("x");
 *
 *     @ColumnData
 *     &#64;NonNull
 *     private Integer y;
 *     public static final IntegerColumn y_ = IntegerColumn.of("y");
 * }
 *
 * </pre>
 *
 * @see fqme.model.Model
 * @see fqme.column.Column
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnData {
}
