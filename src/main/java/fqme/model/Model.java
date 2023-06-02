package fqme.model;

import java.util.HashMap;
import java.util.Map;

import fqme.model.reflection.ModelReflection;

public abstract class Model<T extends Model<T>> {
    /**
     * A map with registered model reflections.
     */
    private static Map<Class<? extends Model<?>>, ModelReflection<?>> modelsReflections = new HashMap<>();

    /**
     * Build and register reflection for a model class.
     *
     * @see ModelReflection
     *
     * @param modelClass a model class
     * @param dbConfig   a db config
     */
    public static <K extends Model<K>> void register(Class<K> modelClass) {
        ModelReflection<K> modelReflection = ModelReflection.buildReflection(modelClass);
        modelsReflections.put(modelClass, modelReflection);
    }

    /**
     * Get a model reflection for a model class.
     *
     * @param modelClass a model class
     * @return a model reflection
     */
    public static <K extends Model<K>> ModelReflection<K> getModelReflection(Class<K> modelClass) {
        return (ModelReflection<K>) modelsReflections.get(modelClass);
    }
}
