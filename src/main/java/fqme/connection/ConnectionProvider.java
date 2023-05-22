package fqme.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fqme.model.ModelMetaInfo;

/**
 * Provides connection to database.
 */
public class ConnectionProvider {
    /**
     * Get connection to database.
     *
     * @param modelMetaInfo meta info for modelClass.
     * @return connection to database.
     */
    public static Connection getConnection(ModelMetaInfo modelMetaInfo) throws SQLException {
        DBConfig dbConfig = modelMetaInfo.getDbConfig();
        return DriverManager.getConnection(
                dbConfig.getUrl(),
                dbConfig.getUsername(),
                dbConfig.getPassword());
    }
}
