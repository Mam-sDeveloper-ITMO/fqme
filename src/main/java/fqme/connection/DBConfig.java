package fqme.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import lombok.Cleanup;
import lombok.Data;

/**
 * Container for database connection details.
 */
@Data
public class DBConfig {
    /**
     * Url of the database.
     */
    private final String url;

    /**
     * Username for database connection.
     */
    private final String username;

    /**
     * Password for database connection.
     */
    private final String password;

    /**
     * Create a new DBConfig instance from a configuration file.
     *
     * @param configPath path to the configuration file
     * @return a new DBConfig instance
     */
    public static DBConfig fromConfigFile(String configPath) throws IOException, FileNotFoundException {
        Properties properties = new Properties();

        @Cleanup
        FileInputStream inputStream = new FileInputStream(configPath);
        properties.load(inputStream);

        return new DBConfig(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"));
    }
}
