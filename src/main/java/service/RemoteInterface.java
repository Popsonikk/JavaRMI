package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {

    boolean register(String name, String password, boolean isAdmin) throws RemoteException;
    boolean logIn(String name, String password) throws RemoteException;
    String sayHello() throws RemoteException;
}

