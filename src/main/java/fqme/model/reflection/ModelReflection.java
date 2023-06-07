package fqme.model.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

import fqme.column.Column;
import fqme.model.Model;
import fqme.model.exceptions.CannotAccessModelColumn;
import fqme.model.exceptions.CannotGetFieldValue;
import fqme.model.exceptions.CannotInstantiateModel;
import fqme.model.exceptions.SuitableConstructorNotFound;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Utility class that allows to get information about a model class.
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelReflection<T extends Model<T>> {
    /**
     * Table name.
     */
    @Getter
    private final String tableName;

    /**
     * Map with columns names as key and columns objects as value.
     *
     * @see Column
     */
    @Getter
    private final LinkedHashMap<String, Column<?>> columns;

    /**
     * A functional interface that allows to get a list of fields values from a
     * model.
     * Returns a list of fields values as objects.
     */
    @Getter
    private final FieldsSupplier<T> fieldsSupplier;

    /**
     * A functional interface that allows to create a model from a list of fields
     * values.
     */
    @Getter
    private final ModelFactory<T> modelFactory;

    /**
     * Use Reflection API to extract information about a model class
     * and create a new instance of ModelReflection.
     *
     * @param modelClass a model class
     * @return a new instance of ModelReflection
     */
    public static <K extends Model<K>> ModelReflection<K> buildReflection(Class<K> modelClass) {
        String tableName = buildTableName(modelClass);
        LinkedHashMap<String, Column<?>> columns = buildColumns(modelClass);
        FieldsSupplier<K> fieldsSupplier = buildFieldsSupplier(modelClass);
        ModelFactory<K> modelFactory = buildModelFactory(modelClass);
        return new ModelReflection<>(tableName, columns, fieldsSupplier, modelFactory);
    }

    /**
     * Build table name for a model class.
     *
     * Table name is the model class name in lower case without "Model" suffix.
     * Example: "UserModel" -> "user"
     *
     * @param modelClass a model class
     * @return a table name
     */
    private static <K extends Model<K>> String buildTableName(Class<K> modelClass) {
        String tableName = modelClass.getSimpleName().toLowerCase();
        tableName = tableName.replace("model", "");
        return tableName;
    }

    /**
     * Build columns map from fields of model class that are subclasses of Column.
     *
     * @see Column
     *
     * @param modelClass a model class
     * @return a list of columns names
     */
    private static <K extends Model<K>> LinkedHashMap<String, Column<?>> buildColumns(Class<K> modelClass) {
        Field[] fields = modelClass.getDeclaredFields();

        LinkedHashMap<String, Column<?>> columns = new LinkedHashMap<>();
        for (Field field : fields) {
            if (Column.class.isAssignableFrom(field.getType())) {
                try {
                    Column<?> column = (Column<?>) field.get(null);
                    columns.put(column.getName(), column);
                } catch (IllegalAccessException e) {
                    throw new CannotAccessModelColumn(e);
                }
            }
        }
        return columns;
    }

    /**
     * Find fields of model class annotated with ColumnData and return a functional
     * interface that allows to get a map with fields names as keys and fields
     * values as values in order required by auto-generated constructor.
     *
     * @see ColumnData
     *
     * @param modelClass a model class
     * @return a functional interface that allows to get a list of
     */
    private static <K extends Model<K>> FieldsSupplier<K> buildFieldsSupplier(Class<K> modelClass) {
        Field[] fields = modelClass.getDeclaredFields();
        List<Field> columnsDataFields = List.of(fields).stream()
                .filter(field -> field.isAnnotationPresent(ColumnData.class))
                .toList();

        return (model) -> {
            LinkedHashMap<String, Object> fieldsValues = new LinkedHashMap<>();
            for (Field field : columnsDataFields) {
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(model);
                    fieldsValues.put(field.getName(), fieldValue);
                } catch (IllegalAccessException e) {
                    throw new CannotGetFieldValue(e);
                }
            }
            return fieldsValues;
        };
    }

    /**
     * Create a functional interface that allows to create a model
     * from a list of columns values.
     *
     * @see ColumnData
     *
     * @param modelClass a model class
     * @return a functional interface that allows to create a model
     */
    private static <K extends Model<K>> ModelFactory<K> buildModelFactory(Class<K> modelClass) {
        Field[] fields = modelClass.getDeclaredFields();
        Class<?>[] columnsTypes = List.of(fields).stream()
                .filter(field -> field.isAnnotationPresent(ColumnData.class))
                .map(field -> (Class<?>) field.getType())
                .toArray(Class<?>[]::new);

        Constructor<K> constructor;
        try {
            constructor = modelClass.getConstructor(columnsTypes);
        } catch (NoSuchMethodException e) {
            throw new SuitableConstructorNotFound();
        }

        return (fieldsValues) -> {
            try {
                return constructor.newInstance(fieldsValues.toArray());
            } catch (Exception e) {
                throw new CannotInstantiateModel(e);
            }
        };
    }
}
