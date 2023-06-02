package fqme.model.reflection;

import java.util.LinkedHashMap;

import fqme.model.Model;

/**
 * Functional interface for getting fields values from a model.
 */
@FunctionalInterface
public interface FieldsSupplier<T extends Model<T>> {
    LinkedHashMap<String, Object> getFieldsValues(T model);
}
