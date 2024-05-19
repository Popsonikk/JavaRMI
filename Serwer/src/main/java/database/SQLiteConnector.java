package database;

import java.sql.*;

public class SQLiteConnector {
    public static Connection connect()
    {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/myDatabase.db"); //połączenie z bazą danych

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void createAccountTable(Connection connection)
    {
        String checkTableExistsSQL = "SELECT name FROM sqlite_master WHERE type='table' AND name='accountTable';";
        String createTableSQL = "CREATE TABLE accountTable ( name TEXT PRIMARY KEY, password TEXT NOT NULL, isAdmin bool NOT NULL);";
        createTable(checkTableExistsSQL,createTableSQL,connection);

    }
    private static void createTable(String existCommand,String createCommand, Connection conn)
    {
        try (Statement statement = conn.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(existCommand);

            if (!resultSet.next())
            {
                statement.execute(createCommand);
                System.out.println("Tabela została utworzona.");
            }
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas tworzenia lub sprawdzania tabeli: " + e.getMessage());
        }
    }
}
