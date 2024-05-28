package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.Question;
import service.RemoteInterface;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestWindowController implements Initializable {

    private  String testName;
    private Scene mainScene;
    private Stage mainStage;
    private RemoteInterface remoteInterface;

    @FXML
    private CheckBox is_A_valid;
    @FXML
    private CheckBox is_B_valid;
    @FXML
    private CheckBox is_C_valid;

    @FXML
    private Text a;
    @FXML
    private Text b;
    @FXML
    private Text c;

    private List<Question> questions;
    private int questionNumber;
    private int score;

    TestWindowController()
    {
        this.questionNumber=0;
        this.score=0;
        this.questions=new ArrayList<>();
    }


    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setRemoteInterface(RemoteInterface remoteInterface) {
        this.remoteInterface = remoteInterface;
    }

    public void next(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
