package fqme.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import fqme.model.Model;

public class ConnectionManager {
    /**
     * A map of model classes and database configurations.
     */
    private static Map<Class<? extends Model<?>>, DBConfig> dbConfigs = new HashMap<>();

    /* Prevent the ConnectionManager class from being instantiated. */
    private ConnectionManager() {
    }

    /**
     * Binds a model class to a database configuration.
     *
     * @param modelClass The model class.
     * @param dbConfig   The database configuration.
     */
    public static void bind(Class<? extends Model<?>> modelClass, DBConfig dbConfig) {
        dbConfigs.put(modelClass, dbConfig);
    }

    /**
     * Create a connection to the database for a model class.
     *
     * @param modelClass The model class.
     * @return A connection to the database.
     * @throws Exception If an error occurs while getting the connection.
     */
    public static Connection getConnection(Class<? extends Model<?>> modelClass) throws Exception {
        DBConfig dbConfig = dbConfigs.get(modelClass);
        Connection connection = DriverManager.getConnection(dbConfig.getUrl(),
                dbConfig.getUsername(),
                dbConfig.getPassword());

        return connection;
    }
}
