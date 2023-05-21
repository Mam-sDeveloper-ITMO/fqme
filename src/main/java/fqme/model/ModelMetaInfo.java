package fqme.model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import fqme.connection.DBConfig;
import lombok.Data;

/**
 * Meta info for modelClass.
 *
 * Stored statically on Model class
 *
 * @see fqme.model.Model
 */
@Data
public class ModelMetaInfo {
    /**
     * Name of the table in database.
     * Generated from model class name.
     */
    private final String tableName;

    /**
     * List of columns names.
     */
    private final List<String> columnsNames;

    /**
     * Array of fields types.
     * Used for instantiating model objects.
     */
    private final Class<?>[] fieldsTypes;

    /**
     * Map with models fields that contains columns data.
     */
    private final Map<String, Field> fields;

    /**
     * Database connection details.
     */
    private final DBConfig dbConfig;
}
