package es.ubu.agenda.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
	 // Constructors -------------------------------------------------------------------------------

    private DatabaseUtil() {
        // Utility class, hide constructor.
    }

    // Actions ------------------------------------------------------------------------------------



    /**
     * Quietly close the Connection. Any errors will be printed to the stderr.
     * @param connection The Connection to be closed quietly.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Closing Connection failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the Statement. Any errors will be printed to the stderr.
     * @param statement The Statement to be closed quietly.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Closing Statement failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the ResultSet. Any errors will be printed to the stderr.
     * @param resultSet The ResultSet to be closed quietly.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Closing ResultSet failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the Connection and Statement. Any errors will be printed to the stderr.
     * @param connection The Connection to be closed quietly.
     * @param statement The Statement to be closed quietly.
     */
    public static void close(Connection connection, Statement statement) {
        close(statement);
        close(connection);
    }

    /**
     * Quietly close the Connection, Statement and ResultSet. Any errors will be printed to the stderr.
     * @param connection The Connection to be closed quietly.
     * @param statement The Statement to be closed quietly.
     * @param resultSet The ResultSet to be closed quietly.
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(resultSet);
        close(statement);
        close(connection);
    }
}
