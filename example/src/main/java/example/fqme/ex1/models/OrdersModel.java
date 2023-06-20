package example.fqme.ex1.models;

import java.time.LocalDateTime;

import fqme.column.common.DateTimeColumn;
import fqme.column.common.ForeignColumn;
import fqme.column.common.StringColumn;
import fqme.column.common.numeric.SerialColumn;
import fqme.model.Model;
import fqme.model.reflection.ColumnData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Example model for order.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrdersModel extends Model<OrdersModel> {
    @ColumnData
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private String orderNumber;
    public static final StringColumn orderNumber_ = StringColumn.of("orderNumber").nullable(false);

    @ColumnData
    @NonNull
    private LocalDateTime orderDate;
    public static final DateTimeColumn orderDate_ = DateTimeColumn.of("orderDate").nullable(false);

    // ForeignColumn is used to specify foreign key
    // Coming soon: support to autofetch foreign models
    @ColumnData
    @NonNull
    private Integer userId;
    public static final ForeignColumn userId_ = ForeignColumn.of("userId", UsersModel.class, UsersModel.id_)
            .nullable(false);
}
