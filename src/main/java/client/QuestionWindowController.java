package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Question;
import service.RemoteInterface;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionWindowController implements Initializable {

    @FXML
    private CheckBox is_A_valid;
    @FXML
    private CheckBox is_B_valid;
    @FXML
    private CheckBox is_C_valid;
    @FXML
    private TextField testName;
    @FXML
    private TextField quest;
    @FXML
    private TextField a;
    @FXML
    private TextField b;
    @FXML
    private TextField c;

    private List<Question> questions;

    private Scene mainScene;
    private Stage mainStage;
    private RemoteInterface remoteInterface;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.questions=new ArrayList<>();

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

    public void addQuest()
    {
        String correct;
        if(is_A_valid.isSelected())
        {
            correct="a";
            is_A_valid.setSelected(false);
        }
        else if (is_B_valid.isSelected())
        {
            correct="b";
            is_B_valid.setSelected(false);
        }
        else if (is_C_valid.isSelected())
        {
            correct = "c";
            is_C_valid.setSelected(false);
        }
        else
        {
            System.out.println("Nie wybrałes poprawnej odpowiedzi");
            return;
        }
        questions.add(new Question(quest.getText(),a.getText(),b.getText(),c.getText(),correct));
        quest.clear();
        a.clear();
        b.clear();
        c.clear();
    }


    public void saveTest() throws RemoteException {
        if(testName.getText().isEmpty())
        {
            System.out.println("Nie wybrałes nazwy testu");
            return;
        }
        addQuest();
        boolean f= remoteInterface.addTest(testName.getText(),questions);
        if(f)
            System.out.println("Test wstawiony pomyslnie");
        else
            System.out.println("Wystąpił błąd podczas tworzenia testu");

        questions.clear();
        mainStage.setScene(mainScene);

    }



}
