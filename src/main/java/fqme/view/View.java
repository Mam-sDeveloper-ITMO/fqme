package fqme.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fqme.column.Column;
import fqme.column.exceptions.UnsupportedSqlType;
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
     * A query builder that is associated with this view.
     */
    private final QueryBuilder<T> queryBuilder;

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
        this.queryBuilder = new QueryBuilder<>(this.modelReflection);

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
    public Set<T> getMany(Query query) throws SQLException, UnsupportedValueType, UnsupportedSqlType {
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
     * Get model from database by its primary keys.
     *
     * @param models a set of models
     * @return a set of models
     * @throws SQLException         if a database access error occurs
     * @throws UnsupportedValueType if query contains argument with unsupported
     *                              value type
     */
    public Set<T> getMany(Set<T> models) throws SQLException, UnsupportedValueType, UnsupportedSqlType {
        Set<Query> queries = new HashSet<>();
        for (T model : models) {
            queries.add(queryBuilder.fetchPrimaryKeys(model));
        }
        return getMany(Query.any(queries));
    }

    /**
     * Get model from database by it primary keys.
     *
     * @param model a model
     * @return a model
     * @throws SQLException         if a database access error occurs
     * @throws UnsupportedValueType if query contains argument with unsupported
     *                              value type
     * @throws UnsupportedSqlType   if query contains argument with unsupported
     */
    public Optional<T> get(T model) throws SQLException, UnsupportedValueType, UnsupportedSqlType {
        Set<T> models = new HashSet<>(Arrays.asList(model));
        models = getMany(models);
        if (models.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(models.iterator().next());
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
    public Set<T> deleteMany(Query query) throws SQLException, UnsupportedValueType, UnsupportedSqlType {
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
     * Delete models by its primary keys.
     *
     * @param models a set of models
     * @return a set of deleted models
     * @throws SQLException         if a database access error occurs
     * @throws UnsupportedValueType if query contains argument with unsupported
     *                              value type
     */
    public Set<T> deleteMany(Iterable<T> models) throws SQLException, UnsupportedValueType, UnsupportedSqlType {
        List<Query> queries = new ArrayList<>();
        for (T model : models) {
            queries.add(queryBuilder.fetchPrimaryKeys(model));
        }
        Query query = Query.any(queries);
        return this.deleteMany(query);
    }

    /**
     * Delete model by its primary keys.
     *
     * @param model a model
     * @return a set of deleted models
     * @throws SQLException         if a database access error occurs
     * @throws UnsupportedValueType if query contains argument with unsupported
     *                              value type
     */
    public Optional<T> delete(T model) throws SQLException, UnsupportedValueType, UnsupportedSqlType {
        Set<T> models = new HashSet<>(Arrays.asList(model));
        models = deleteMany(models);
        if (models.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(models.iterator().next());
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
    public Set<T> putMany(Iterable<T> models) throws SQLException, UnsupportedValueType, UnsupportedSqlType {
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
    public Optional<T> put(T model) throws SQLException, UnsupportedValueType, UnsupportedSqlType {
        Set<T> result = putMany(Arrays.asList(model));
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.iterator().next());
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
    public T buildModelFromResultSet(ResultSet resultSet) throws SQLException, UnsupportedSqlType {
        List<Object> fields = new ArrayList<>();
        for (Column<?, ?> column : modelReflection.getColumns().values()) {
            Object sqlValue = resultSet.getObject(column.getName());
            fields.add(column.fromSqlType(sqlValue));
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
