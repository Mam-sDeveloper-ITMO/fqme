package fqme.utils;

import fqme.column.common.NumericColumn;
import fqme.column.common.StringColumn;
import fqme.model.Model;
import lombok.Data;
import lombok.NonNull;

@Data
public class TestModel extends Model<TestModel> {
    @NonNull
    private String name;
    public static final StringColumn name_ = new StringColumn("name");

    @NonNull
    private Integer x;
    public static final NumericColumn<Integer> x_ = new NumericColumn<>("x");

    @NonNull
    private Integer y;
    public static final NumericColumn<Integer> y_ = new NumericColumn<>("y");
}
