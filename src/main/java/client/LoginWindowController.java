package client;


import database.SQLiteConnector;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.RemoteInterface;
import service.User;

import java.rmi.RemoteException;

public class LoginWindowController {
    private Scene mainScene;
    private Stage mainStage;
    private MainWindowController mw;
    @FXML
    private TextField nick;
    @FXML
    private PasswordField password;
    @FXML
    private Text mess;

    @FXML
    private Button button;
    private RemoteInterface remoteInterface;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setMw(MainWindowController mw) {
        this.mw = mw;
    }

    public void setRemoteInterface(RemoteInterface remoteInterface) {
        this.remoteInterface = remoteInterface;
    }

    private void showMainScene()
    {
        mainStage.setScene(mainScene);
    }
    public void checkAccount() throws RemoteException {
        boolean f= remoteInterface.logIn(nick.getText(),password.getText());
        if(!f)
        {
            mess.setText("Login lub hasło niepoprawne");
            mess.setFill(Color.RED);
        }
        else
        {
            mw.setNick(nick.getText());
            showMainScene();
        }
    }
}
