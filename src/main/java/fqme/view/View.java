package fqme.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import fqme.connection.ConnectionProvider;
import fqme.connection.DBConfig;
import fqme.model.Model;
import fqme.model.ModelMetaInfo;
import lombok.Cleanup;

public class View<T extends Model<T>> {
    /**
     * Type of a model that table view will operate on.
     */
    private final Class<T> modelClass;

    /**
     * Meta info for modelClass.
     */
    private final ModelMetaInfo modelMetaInfo;

    /**
     * Create table view for modelClass.
     *
     * @param modelClass type of a model that table view will operate on.
     */
    private View(Class<T> modelClass) {
        this.modelClass = modelClass;
        this.modelMetaInfo = Model.getModelMetaInfo(modelClass);
    }

    /**
     * Create table view for modelClass.
     *
     * @param <K>        type of a model that table view will operate on.
     * @param modelClass type of a model that table view will operate on.
     * @return table view for modelClass.
     */
    public static <K extends Model<K>> View<K> of(Class<K> modelClass) {
        return new View<>(modelClass);
    }

    /**
     * Create table for modelClass.
     *
     * @throws SQLException if table creation fails.
     * @throws IOException  if modelClass is not registered.
     * @return true if table was created, false if table already exists.
     */
    public Optional<T> get(Integer id) throws SQLException {
        initTable();
        
        @Cleanup
        Connection connection = ConnectionProvider.getConnection(this.modelMetaInfo);

        StatementBuilder statementBuilder = new StatementBuilder(connection, this.modelMetaInfo);
        PreparedStatement statement = statementBuilder.forIdSearch(id);

        ResultSet result = statement.executeQuery();
        if (result.next()) {
            Object[] fieldsValues = getResultSetObjects(result, modelMetaInfo.getColumnsNames().size());
            try {
                T modelInstance = Model.fromFieldsValues(fieldsValues, modelClass);

                return Optional.of(modelInstance);
            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Create database table for modelClass if it does not exist.
     *
     * @throws SQLException if table creation fails.
     * @throws IOException  if modelClass is not registered.
     */
    private void initTable() throws SQLException {
        @Cleanup
        Connection connection = ConnectionProvider.getConnection(this.modelMetaInfo);

        StatementBuilder statementBuilder = new StatementBuilder(connection, this.modelMetaInfo);
        PreparedStatement statement = statementBuilder.forTableCreation();

        statement.execute();
    }

    /**
     * Get N objects from result set.
     * Used for instantiating model.
     *
     * @param resultSet result set to get objects from.
     * @param count     number of objects to get.
     * @return array of objects from result set.
     */
    private static Object[] getResultSetObjects(ResultSet resultSet, Integer count) throws SQLException {
        Object[] objects = new Object[count];
        for (int i = 0; i < count; i++) {
            objects[i] = resultSet.getObject(i + 1);
        }
        return objects;
    }
}
