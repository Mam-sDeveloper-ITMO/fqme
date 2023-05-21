package fqme.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import fqme.connection.DBConfig;
import lombok.Data;

/**
 * Meta info for modelClass.
 *
 * Contains table name, columns names, fields supplier and database connection
 * details.
 * Stored statically on Model class @see fqme.model.Model
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
     * List of fields suppliers.
     */
    private final Map<String, Function<? extends Model<?>, ?>> fieldsSuppliers;

    /**
     * Database connection details.
     */
    private final DBConfig dbConfig;
}
