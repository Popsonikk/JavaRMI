package service;

import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteInterface extends Remote {

    boolean register(String name, String password, boolean isAdmin) throws RemoteException;
    boolean logIn(String name, String password) throws RemoteException;
    boolean addTest(String testName, List<Question> questions) throws RemoteException;
    boolean addScore(String nickName,int testId,int score) throws RemoteException;

    int getTestId(String name) throws RemoteException;
    List<List<String>> getBasicScoreList() throws RemoteException;

    User getUser() throws  RemoteException;
    String sayHello() throws RemoteException;

    List<Question> getQuestionList(String name) throws RemoteException;
}

