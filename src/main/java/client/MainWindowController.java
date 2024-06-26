package client;


import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.RemoteInterface;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {


    @FXML
    private TextField testName;

    @FXML
    private Text alertText;

    @FXML
    private Button add;

    @FXML
    private Button write;

    private Stage mainStage; //kontener reprezentujący okno aplikacji
    private Scene loginScene;
    private Scene registerScene;
    private Scene questionScene;
    private Scene scoreScene;

    private Scene testScene;

    private TestWindowController testWindowController;

    private ScoreWindowController scoreWindowController;

    @FXML
    private Text helloMessage;
    private SimpleStringProperty nick=new SimpleStringProperty();
    private RemoteInterface remoteInterface;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.nick.set(" ");
        helloMessage.textProperty().bind(Bindings.concat("Zalogowany jako: ").concat(nick));
        add.setDisable(true);
        write.setDisable(true);


    }

    public void setQuestionScene(Scene questionScene) {this.questionScene=questionScene;}
    public void setRegisterScene(Scene registerScene) {
        this.registerScene = registerScene;
    }
    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void setTestScene(Scene testScene) {this.testScene = testScene;}

    public void setScoreScene(Scene scoreScene) {
        this.scoreScene = scoreScene;
    }

    public void setTestWindowController(TestWindowController testWindowController) {
        this.testWindowController = testWindowController;
    }

    public void setStage(Stage stage)
    {
        this.mainStage=stage;
    }
    public void setRemoteInterface(RemoteInterface remoteInterface) {this.remoteInterface = remoteInterface;}
    public void logIn() {mainStage.setScene(loginScene);}
    public void register() {
        mainStage.setScene(registerScene);
    }
    public void addTest(){mainStage.setScene(questionScene);}

    public void writeTest() throws RemoteException {
        if(testName.getText().isEmpty())
        {
            alertText.setText("Nie podałes nazwy testu!");
            alertText.setFill(Color.RED);
        }
        else if(remoteInterface.getTestId(testName.getText())==-1)
        {
            alertText.setText("Podany test nie istnieje");
            alertText.setFill(Color.RED);
        }
        else
        {
            testWindowController.setTestName(testName.getText());
            testWindowController.setQuestions(remoteInterface.getQuestionList(testName.getText()));
            testWindowController.startTest();
            alertText.setText("");
            testName.setText("");
            mainStage.setScene(testScene);
        }

    }
    public void onAddButton()
    {
        add.setDisable(false);
    }
    public void setNick(String s)
    {
        nick.set(s);
        write.setDisable(false);
        add.setDisable(true);
    }

    public void setScoreWindowController(ScoreWindowController scoreWindowController) {
        this.scoreWindowController = scoreWindowController;
    }

    public void showScore() throws RemoteException {
        scoreWindowController.createView();
        mainStage.setScene(scoreScene);
    }
}
