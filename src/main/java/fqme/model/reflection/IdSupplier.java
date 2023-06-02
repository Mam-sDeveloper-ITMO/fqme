package fqme.model.reflection;

import java.util.Optional;

import fqme.model.Model;

@FunctionalInterface
public interface IdSupplier<T extends Model<T>> {
    Optional<Integer> getIdValue(T model);
}
