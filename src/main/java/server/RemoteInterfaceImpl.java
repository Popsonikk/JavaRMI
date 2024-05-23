package server;

import database.SQLiteConnector;
import service.RemoteInterface;
import service.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class RemoteInterfaceImpl extends UnicastRemoteObject implements RemoteInterface {

    private Connection connection;
    private User user;

    public RemoteInterfaceImpl(Connection connection) throws RemoteException {
        super();
        this.connection = connection;
        this.user=new User();

    }

    @Override
    public boolean register(String name, String password, boolean isAdmin) throws RemoteException {
        if(!isAdmin||!SQLiteConnector.getAdmin(connection))
        {
            SQLiteConnector.addAccount(name,password, false,connection);
            System.out.println("Użytkownik "+name+" został dodany do bazy");
            return true;
        }
        else
        {
            System.out.println("Rejestracja nieudana");
            return false;
        }

    }

    @Override
    public boolean logIn(String name, String password) throws RemoteException {
        user=SQLiteConnector.getAccount(name,password,connection);
        if(user==null)
        {
            System.out.println("Błędne dane logowania");
            return false;
        }
        else
        {
            System.out.println("Zalogowano poprawnie");
            return true;
        }
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello Client";
    }
}
