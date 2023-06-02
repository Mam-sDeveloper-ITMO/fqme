package fqme.model.reflection;

import java.util.List;

import fqme.model.Model;

/**
 * A functional interface that allows to create a model from a list of fields.
 *
 * Fields values are supplied from FieldsSupplier.
 *
 * @see FieldsSupplier
 */
@FunctionalInterface
public interface ModelFactory<T extends Model<T>> {
    /**
     * Creates a model from a list of fields.
     *
     * @param fields a list of fields
     * @return a model
     * @throws Exception
     */
    T fromFields(List<Object> fields) throws Exception;
}
