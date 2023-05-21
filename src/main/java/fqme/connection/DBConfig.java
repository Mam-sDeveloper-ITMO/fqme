package fqme.connection;

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
}
