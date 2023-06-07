package fqme.model.reflection;

import java.util.List;

import fqme.model.Model;
import fqme.model.exceptions.CannotInstantiateModel;
import fqme.model.exceptions.SuitableConstructorNotFound;

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
     * @throws CannotInstantiateModel      if a model cannot be instantiated
     * @throws SuitableConstructorNotFound if suitable constructor not exists
     */
    T fromFields(List<Object> fields) throws CannotInstantiateModel, SuitableConstructorNotFound;
}
