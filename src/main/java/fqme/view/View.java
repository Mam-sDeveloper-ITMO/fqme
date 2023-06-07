package fqme.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fqme.column.Column;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.model.Model;
import fqme.model.reflection.ModelFactory;
import fqme.model.reflection.ModelReflection;
import fqme.query.Query;

/**
 * A class that provides to manipulate model data in the database.
 *
 * Example:
 *
 * <pre>
 * {@code
 * View<User> userView = new View<>(User.class, connection);
 * Set<User> users = userView.get(User.name_.eq("John"));
 *
 * User user = new User("John", "Doe");
 * Set<User> users = userView.put(user);
 *
 * Set<User> users = userView.delete(new Query("id", 1));
 * }
 * </pre>
 */
public class View<T extends Model<T>> {
    /**
     * A model class that is associated with this view.
     */
    private final ModelReflection<T> modelReflection;

    /**
     * A statement builder that is associated with this view.
     */
    private final StatementBuilder<T> statementBuilder;

    /**
     * Create a view of model for given model class and connection.
     *
     * @param modelClass a model class
     * @param connection a connection to the database
     * @throws SQLException if a database access error occurs
     */
    public View(Class<T> modelClass, Connection connection) throws SQLException {
        this.modelReflection = Model.getModelReflection(modelClass);
        this.statementBuilder = new StatementBuilder<>(this.modelReflection, connection);

        createTable();
    }

    /**
     * Static View fabric method.
     *
     * @param modelClass a model class
     * @param connection a connection to the database
     * @return a view of model
     * @throws SQLException if a database access error occurs
     */
    public static <K extends Model<K>> View<K> of(Class<K> modelClass, Connection connection) throws SQLException {
        return new View<>(modelClass, connection);
    }

    /**
     * Get models by a query.
     *
     * @param query a query
     * @return a set of models
     * @throws SQLException         if a database access error occurs
     * @throws UnsupportedValueType if query contains argument with unsupported
     *                              value type
     */
    public Set<T> get(Query query) throws SQLException, UnsupportedValueType {
        PreparedStatement statement = statementBuilder.buildGetStatement(query);
        statement.execute();

        ResultSet resultSet = statement.getResultSet();
        Set<T> models = new HashSet<>();
        while (resultSet.next()) {
            models.add(buildModelFromResultSet(resultSet));
        }
        return models;
    }

    /**
     * Delete models by a query.
     *
     * @param query a query
     * @return a set of deleted models
     * @throws SQLException         if a database access error occurs
     * @throws UnsupportedValueType if query contains argument with unsupported
     *                              value type
     */
    public Set<T> delete(Query query) throws SQLException, UnsupportedValueType {
        PreparedStatement statement = statementBuilder.buildDeleteStatement(query);
        statement.execute();

        ResultSet resultSet = statement.getResultSet();
        Set<T> models = new HashSet<>();
        while (resultSet.next()) {
            models.add(buildModelFromResultSet(resultSet));
        }
        return models;
    }

    /**
     * Put a model.
     *
     * @param model a model
     * @return a set of models
     * @throws SQLException         if a database access error occurs
     * @throws UnsupportedValueType if query contains argument with unsupported
     *                              value type
     */
    public Set<T> put(Iterable<T> models) throws SQLException, UnsupportedValueType {
        Set<T> result = new HashSet<>();
        for (T model : models) {
            PreparedStatement statement = statementBuilder.buildPutStatement(model);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                result.add(buildModelFromResultSet(resultSet));
            }
        }
        return result;
    }

    /**
     * Overload of {@link #put(Iterable)} for a single model.
     *
     * @param models an array of models
     * @return a set of models
     * @throws SQLException         if a database access error occurs
     * @throws UnsupportedValueType if query contains argument with unsupported
     *                              value type
     */
    public Set<T> put(T model) throws SQLException, UnsupportedValueType {
        return put(List.of(model));
    }

    /**
     * Build a model from result set.
     *
     * @param resultSet a result set
     * @return a model
     * @throws SQLException         if a database access error occurs
     * @throws UnsupportedValueType if query contains argument with unsupported
     *                              value type
     */
    public T buildModelFromResultSet(ResultSet resultSet) throws SQLException {
        List<Object> fields = new ArrayList<>();
        for (Column<?> column : modelReflection.getColumns().values()) {
            fields.add(resultSet.getObject(column.getName()));
        }
        ModelFactory<T> modelFactory = modelReflection.getModelFactory();
        return modelFactory.fromFields(fields);
    }

    /**
     * Create a table for this view.
     *
     * @throws SQLException if a database access error occurs
     *
     */
    private void createTable() throws SQLException {
        PreparedStatement statement = statementBuilder.buildCreateTableStatement();
        statement.execute();
    }
}
