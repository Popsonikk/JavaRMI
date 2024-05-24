package server;

import database.SQLiteConnector;
import service.RemoteInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try(Connection conn= SQLiteConnector.connect())
        {
            if (conn!=null)
            {
                System.out.println("Połączono z bazą danych SQLite.");
                SQLiteConnector.createAccountTable();
                SQLiteConnector.createTestTable();
                SQLiteConnector.createQuestionTable();
                RemoteInterface remoteObject = new RemoteInterfaceImpl();
                LocateRegistry.createRegistry(5555);
                Naming.rebind("//localhost:5555/remoteObject", remoteObject);
                System.out.println("Server ready");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Nie udało się połączyć z bazą: "+e);
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Nie udało się połączyć z serwerem: "+e);
        }


    }
}