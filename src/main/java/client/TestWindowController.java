package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.Question;
import service.RemoteInterface;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class TestWindowController implements Initializable {

    private String testName;
    private Scene mainScene;
    private Stage mainStage;
    private RemoteInterface remoteInterface;
    Button button = new Button();

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
    @FXML
    private Text q;
    @FXML
    private Text alert;

    @FXML
    private Pane pane;
    @FXML
    private Button buttonNext;

    private List<Question> questions;
    private int questionNumber;
    private int score;

    public TestWindowController() {
        this.questionNumber = 0;
        this.score = 0;
        this.questions = new ArrayList<>();
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

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void next() throws RemoteException {
        if((is_A_valid.isSelected()&& Objects.equals(questions.get(questionNumber).getGood(), "a"))||
                (is_B_valid.isSelected()&& Objects.equals(questions.get(questionNumber).getGood(), "b"))||
                        (is_C_valid.isSelected()&& Objects.equals(questions.get(questionNumber).getGood(), "c")))
        {
            score++;
        }
        if(questionNumber<questions.size()-1)
            setQ(++questionNumber);
        else {
            buttonNext.setDisable(true);
            alert.setText("Twój wynik to " + score + " na " + questions.size());
            pane.getChildren().add(button);
            boolean b=remoteInterface.addScore(remoteInterface.getUser().getName(),remoteInterface.getTestId(testName),score*100/questions.size());
            if(b)
                System.out.println("Wykonanie testu przebiegło poprawnie");
            else
                System.out.println("Wystąpił błąd w trakcie przeprowadzania testu");
        }

    }
    public void startTest()
    {
        setQ(0);
        buttonNext.setDisable(false);
        pane.getChildren().remove(button);
        score=0;
        questionNumber=0;
    }
    private void setQ(int n)
    {
        is_A_valid.setSelected(false);
        is_B_valid.setSelected(false);
        is_C_valid.setSelected(false);
        a.setText(questions.get(n).getA());
        b.setText(questions.get(n).getB());
        c.setText(questions.get(n).getC());
        q.setText(questions.get(n).getQ());
        alert.setText("Pytanie "+(n+1)+" z "+questions.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        button.setLayoutX(15);
        button.setLayoutY(15);
        button.setText("Powrót");
        button.setPrefSize(100, 50);
        button.setOnMouseClicked((event) -> {
            mainStage.setScene(mainScene);
        });



    }
}
