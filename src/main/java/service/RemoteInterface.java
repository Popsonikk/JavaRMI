package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteInterface extends Remote {

    boolean register(String name, String password, boolean isAdmin) throws RemoteException;
    boolean logIn(String name, String password) throws RemoteException;
    boolean addTest(String testName, List<Question> questions);
    String sayHello() throws RemoteException;
}

