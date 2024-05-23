package database;

import service.User;

import java.sql.*;
import java.util.Base64;

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
    public static void addAccount(String name, String password,boolean isAdmin,Connection conn)
    {
        try(PreparedStatement statement= conn.prepareStatement("INSERT INTO accountTable (name,password,isAdmin) VALUES ( ?,?,?)"))
        {
            statement.setString(1,name);
            statement.setString(2, Base64.getEncoder().encodeToString(password.getBytes()));
            statement.setBoolean(3, isAdmin);
            statement.executeUpdate();
            System.out.println("Dane wstawiono poprawnie");
        }
        catch (SQLException e)
        {
            System.out.println("Wystąpił błąd podczas wstawiania konta:"  +e);
        }
    }
    public static boolean getAdmin(Connection conn) {
        try (Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM accountTable WHERE isAdmin=true");
            if (resultSet.next())
                return true;
            else
                return false;

        } catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas wstawiania konta:" + e);
            return false;
        }
    }
    public static User getAccount(String name, String password, Connection conn)
    {
        try(PreparedStatement statement= conn.prepareStatement("SELECT * FROM accountTable WHERE name=(?) AND password=(?)"))
        {
            statement.setString(1,name);
            statement.setString(2, Base64.getEncoder().encodeToString(password.getBytes()));
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next())
                return new User(resultSet.getString("nick"),resultSet.getBoolean("isAdmin"));
            else
                return null;

        }
        catch (SQLException e)
        {
            System.out.println("Wystąpił błąd podczas wstawiania konta:"  +e);
            return null;
        }
    }
}
