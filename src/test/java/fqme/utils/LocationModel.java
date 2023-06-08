package fqme.utils;

import fqme.column.common.StringColumn;
import fqme.column.common.numeric.IntegerColumn;
import fqme.column.common.numeric.SerialColumn;
import fqme.model.Model;
import fqme.model.reflection.ColumnData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LocationModel extends Model<LocationModel> {
    @ColumnData
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private String name;
    public static final StringColumn name_ = StringColumn.of("name");

    @ColumnData
    @NonNull
    private Integer x;
    public static final IntegerColumn x_ = IntegerColumn.of("x");

    @ColumnData
    @NonNull
    private Integer y;
    public static final IntegerColumn y_ = IntegerColumn.of("y");
}
