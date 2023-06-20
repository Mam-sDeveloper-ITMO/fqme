package example.fqme.ex1.models;

import fqme.column.common.BooleanColumn;
import fqme.column.common.StringColumn;
import fqme.column.common.numeric.IntegerColumn;
import fqme.column.common.numeric.SerialColumn;
import fqme.model.Model;
import fqme.model.reflection.ColumnData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Example model for user.
 */

// You can use lombok to generate getters, setters, equals, hashCode, toString
@Data

// AllArgsConstructor is required for Model. You can use lombok to generate it,
// or write it yourself
@AllArgsConstructor

// Handy for creating models without id, to allow database to generate it
@RequiredArgsConstructor

public class UsersModel extends Model<UsersModel> {
    // ColumnData annotation is required for every field that should be saved in
    // database
    @ColumnData
    private Integer id;
    // Columns used to access database, make queries and specify SQL-types.
    // You can use any name for Columns, but name should be same as ColumnData name
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private String name;
    // Columns are nullable and not unique by default, but you can change it
    public static final StringColumn name_ = StringColumn.of("name").nullable(false).unique();

    @ColumnData
    @NonNull
    private Integer age;
    public static final IntegerColumn age_ = IntegerColumn.of("age").nullable(false);

    @ColumnData
    @NonNull
    private Boolean active;
    public static final BooleanColumn active_ = BooleanColumn.of("active").nullable(false);
}
