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
    public static void createTestTable()
    {
        String checkTableExistsSQL = "SELECT name FROM sqlite_master WHERE type='table' AND name='testTable';";
        String createTableSQL = "CREATE TABLE testTable ( id INTEGER PRIMARY KEY AUTOINCREMENT, testName TEXT NOT NULL);";
        createTable(checkTableExistsSQL,createTableSQL);

    }
    public static void createQuestionTable()
    {
        String checkTableExistsSQL = "SELECT name FROM sqlite_master WHERE type='table' AND name='questionTable';";
        String createTableSQL = "CREATE TABLE questionTable ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT NOT NULL, a TEXT NOT NULL, b TEXT NOT NULL, c TEXT NOT NULL, correct TEXT NOT NULL," +
                " testId INTEGER, FOREIGN KEY (testId) REFERENCES tests(id) );";
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

            statement.executeUpdate();
            System.out.println("Dane wstawiono poprawnie");
        }
        catch (SQLException e)
        {
            System.out.println("Wystąpił błąd podczas wstawiania konta:"  +e);
        }
    }
    public static void addTest(String name)
    {
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/myDatabase.db");
            PreparedStatement statement= conn.prepareStatement("INSERT INTO testTable (testName) VALUES ( ?)"))
        {
            statement.setString(1,name);
            statement.executeUpdate();
            System.out.println("Test wstawiono poprawnie");
        }
        catch (SQLException e)
        {
            System.out.println("Wystąpił błąd podczas wstawiania konta:"  +e);
        }
    }
    public static void addQuestion(String name,String a,String b,String c,String correct,int id)
    {
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/myDatabase.db");
            PreparedStatement statement= conn.prepareStatement("INSERT INTO questionTable (question,a,b,c,correct,testId) VALUES ( ?,?,?,?,?,?)"))
        {
            statement.setString(1,name);
            statement.setString(2,a);
            statement.setString(3,b);
            statement.setString(4,c);
            statement.setString(5,correct);
            statement.setInt(6,id);
            statement.executeUpdate();
            System.out.println("Pytanie dodane poprawnie");
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
    public static boolean isAdmin(String name) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/myDatabase.db");
             PreparedStatement statement =conn.prepareStatement( "SELECT isAdmin FROM accountTable WHERE name=(?)")) {

           statement.setString(1,name);
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next())
                return resultSet.getBoolean("isAdmin");
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
    public static int getTestId(String name)
    {
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/myDatabase.db");
            PreparedStatement statement= conn.prepareStatement("SELECT id FROM testTable WHERE testName=(?) "))
        {
            statement.setString(1,name);
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next())
                return resultSet.getInt("id");
            else
                return -1;

        }
        catch (SQLException e)
        {
            System.out.println("Wystąpił błąd podczas wstawiania konta:"  +e);
            return -1;
        }
    }

}
