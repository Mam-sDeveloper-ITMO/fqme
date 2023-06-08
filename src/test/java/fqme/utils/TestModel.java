package fqme.utils;

import fqme.column.common.ForeignColumn;
import fqme.column.common.StringColumn;
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
public class TestModel extends Model<TestModel> {
    @ColumnData
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private String name;
    public static final StringColumn name_ = StringColumn.of("name");

    @ColumnData
    @NonNull
    private Integer locationId;
    public static final ForeignColumn locationId_ = ForeignColumn.of("locationId", LocationModel.class,
            LocationModel.id_);
}
