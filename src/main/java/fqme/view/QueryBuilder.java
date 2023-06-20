package fqme.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import fqme.column.Column;
import fqme.model.Model;
import fqme.model.reflection.ModelReflection;
import fqme.query.Query;
import lombok.RequiredArgsConstructor;

/**
 * Utility to build complex queries over models.
 */
@RequiredArgsConstructor
public class QueryBuilder<T extends Model<T>> {
    /**
     * A model class that is associated with this view.
     */
    private final ModelReflection<T> modelReflection;

    /**
     * Create query that fetch by primary keys.
     *
     * @param model a model instance
     * @return query that fetch by primary keys.
     */
    public Query fetchPrimaryKeys(T model) {
        LinkedHashMap<String, Object> fields = modelReflection.getFieldsSupplier().getFieldsValues(model);

        List<Query> queries = new ArrayList<>();
        for (Column<?, ?> column : modelReflection.getColumns().values()) {
            if (column.isPrimary()) {
                @SuppressWarnings("unchecked")
                Column<?, Object> typedColumn = (Column<?, Object>) column;

                Object value = fields.get(column.getName());
                queries.add(typedColumn.eq(value));
            }
        }
        return Query.all(queries);
    }
}
