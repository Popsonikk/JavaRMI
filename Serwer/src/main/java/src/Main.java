package src;


import database.SQLiteConnector;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args)  {
        try(Connection conn= SQLiteConnector.connect())
        {
            if (conn!=null)
            {
                System.out.println("Połączono z bazą danych SQLite.");
                SQLiteConnector.createAccountTable(conn);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Nie udało się połączyć z bazą: "+e);
        }


    }
}