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
    public static void createAccountTable()
    {
        String checkTableExistsSQL = "SELECT name FROM sqlite_master WHERE type='table' AND name='accountTable';";
        String createTableSQL = "CREATE TABLE accountTable ( name TEXT PRIMARY KEY, password TEXT NOT NULL, isAdmin BOOLEAN NOT NULL);";
        createTable(checkTableExistsSQL,createTableSQL);

    }
    private static void createTable(String existCommand,String createCommand)
    {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/myDatabase.db");
             Statement statement = conn.createStatement())
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
    public static void addAccount(String name, String password,boolean isAdmin)
    {
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/myDatabase.db");
            PreparedStatement statement= conn.prepareStatement("INSERT INTO accountTable (name,password,isAdmin) VALUES ( ?,?,?)"))
        {
            statement.setString(1,name);
            statement.setString(2, Base64.getEncoder().encodeToString(password.getBytes()));
            statement.setBoolean(3, isAdmin);
            System.out.println(isAdmin);
            statement.executeUpdate();
            System.out.println("Dane wstawiono poprawnie");
        }
        catch (SQLException e)
        {
            System.out.println("Wystąpił błąd podczas wstawiania konta:"  +e);
        }
    }
    public static boolean getAdmin() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/myDatabase.db");
             Statement statement = conn.createStatement()) {

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
    public static User getAccount(String name, String password)
    {
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/myDatabase.db");
            PreparedStatement statement= conn.prepareStatement("SELECT * FROM accountTable WHERE name=(?) AND password=(?)"))
        {
            statement.setString(1,name);
            statement.setString(2, Base64.getEncoder().encodeToString(password.getBytes()));
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next())
                return new User(resultSet.getString("name"),resultSet.getBoolean("isAdmin"));
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
