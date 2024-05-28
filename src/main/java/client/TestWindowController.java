package client;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.RemoteInterface;

public class TestWindowController {

    private  String testName;
    private Scene mainScene;
    private Stage mainStage;
    private RemoteInterface remoteInterface;

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
}
