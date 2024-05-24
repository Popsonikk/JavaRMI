package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.RemoteInterfaceImpl;
import service.RemoteInterface;

import java.rmi.Naming;

public class Client extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        try {
            // Pobieranie referencji do zdalnego obiektu
            RemoteInterface remoteObject = (RemoteInterface) Naming.lookup("//localhost:5555/remoteObject");
            System.out.println(remoteObject.sayHello());

            FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent mainWindow = mainWindowLoader.load();
            MainWindowController mainWindowController = mainWindowLoader.getController();
            mainWindowController.setStage(stage);

            FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("registerWindow.fxml"));
            Parent registerWindow= registerLoader.load();
            RegisterWindowController registerWindowController = registerLoader.getController();
            registerWindowController.setMainStage(stage);

            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
            Parent loginWindow =loginLoader.load();
            LoginWindowController loginWindowController= loginLoader.getController();
            loginWindowController.setMainStage(stage);

            FXMLLoader questLoader = new FXMLLoader(getClass().getResource("QuestionWindow.fxml"));
            Parent questWindow= questLoader.load();
            QuestionWindowController questionWindowController = questLoader.getController();
            questionWindowController.setMainStage(stage);

            Scene mainScene=new Scene(mainWindow, 800, 600);
            registerWindowController.setMainScene(mainScene);
            loginWindowController.setMainScene(mainScene);
            questionWindowController.setMainScene(mainScene);
            loginWindowController.setMw(mainWindowController);

            mainWindowController.setRegisterScene(new Scene(registerWindow, 800, 600));
            mainWindowController.setLoginScene(new Scene(loginWindow,800,600));
            mainWindowController.setTestScene(new Scene(questWindow,800,600));

            mainWindowController.setRemoteInterface(remoteObject);
            loginWindowController.setRemoteInterface(remoteObject);
            registerWindowController.setRemoteInterface(remoteObject);
            questionWindowController.setRemoteInterface(remoteObject);


            stage.setTitle("RMI");
            stage.setScene(mainScene);
            stage.show();


        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }
}
