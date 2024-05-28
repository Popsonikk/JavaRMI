package server;

import database.SQLiteConnector;
import service.Question;
import service.RemoteInterface;
import service.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.List;

public class RemoteInterfaceImpl extends UnicastRemoteObject implements RemoteInterface {


    private User user;

    public RemoteInterfaceImpl() throws RemoteException {
        super();

        this.user=new User();

    }

    @Override
    public boolean register(String name, String password, boolean isAdmin) throws RemoteException {
        if(!isAdmin||!SQLiteConnector.getAdmin())
        {
            SQLiteConnector.addAccount(name,password, isAdmin);
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
        user=SQLiteConnector.getAccount(name,password);
        if(user==null)
        {
            System.out.println("Błędne dane logowania");
            return false;
        }
        else
        {
            System.out.println("Zalogowano poprawnie: "+user.getName());

            return true;
        }
    }

    @Override
    public boolean addTest(String testName, List<Question> questions) throws RemoteException {
        try
        {
            SQLiteConnector.addTest(testName);
            int id=SQLiteConnector.getTestId(testName);
            for(Question q:questions)
            {
                SQLiteConnector.addQuestion(q.getQ(), q.getA(), q.getB(), q.getC(), q.getGood(), id);
            }
            return true;

        }
        catch (Exception e)
        {
            System.out.println("Nie udało się dodać testu: "+e);
            return  false;
        }
    }

    @Override
    public int getTestId(String name) throws RemoteException {
        return SQLiteConnector.getTestId(name);
    }

    @Override
    public User getUser() {
        return user;
    }


    @Override
    public String sayHello() throws RemoteException {
        return "Hello Client";
    }

    @Override
    public List<Question> getQuestionList(String name) throws RemoteException {
        return SQLiteConnector.getQuestions(name);
    }
}
