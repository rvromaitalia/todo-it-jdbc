package se.lexicon.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Factory class responsible for creating database connections using JDBC.
 * <p>
 * This class centralizes database connection configuration and provides a
 * single access point for obtaining {@link java.sql.Connection} instances..
 * </p>
 */
public class ConnectionFactory {
    //Connection information to the DB
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/todoit?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "1234";

    private ConnectionFactory() {}

    /**
     * Creates and returns a new {@link Connection} to the database.
     * <p>
     * The connection parameters are read from system properties if present,
     * otherwise default values are used. This allows flexible configuration
     * without modifying source code.
     * </p>
     *
     * @return an active database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
            String url = System.getProperty("db.url", DEFAULT_URL).trim();
            String user = System.getProperty("db.user", DEFAULT_USER);
            String pass = System.getProperty("db.password", DEFAULT_PASSWORD);
            return DriverManager.getConnection(url, user, pass);
    }
}