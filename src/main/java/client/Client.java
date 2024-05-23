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
            //wczytanie pliku fxml zawierający opis sceny
            Parent mainWindow = mainWindowLoader.load();
            MainWindowController controller = mainWindowLoader.getController();
            stage.setTitle("RMI");

            stage.setScene(new Scene(mainWindow, 800, 600));
            stage.show();


        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }
}
