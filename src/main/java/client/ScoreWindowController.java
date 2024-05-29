package client;


import database.SQLiteConnector;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import service.RemoteInterface;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class ScoreWindowController  {

    private Scene mainScene;
    private Stage mainStage;
    private RemoteInterface remoteInterface;
    @FXML
    private TableView<ObservableList<String>> tableView;





    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }


    public void setRemoteInterface(RemoteInterface remoteInterface) {
        this.remoteInterface = remoteInterface;
    }




    public void createView() throws RemoteException {
        List<List<String>> listData= remoteInterface.getBasicScoreList();
        System.out.println(listData.size());
        ObservableList<ObservableList<String>> data;
        data = FXCollections.observableArrayList();
        for (List<String> row : listData) {
            data.add( FXCollections.observableArrayList(row.get(0), row.get(1), row.get(2)));
        }



        TableColumn<ObservableList<String>,String> nickColumn= createColumn("Name",0);
        TableColumn<ObservableList<String>,String> testColumn=createColumn("Test",1);
        TableColumn<ObservableList<String>,String> frameColumn=createColumn("Score",2);
        nickColumn.setPrefWidth(390);
        testColumn.setPrefWidth(195);

        frameColumn.setComparator((el1, el2) -> {
            int score1 = Integer.parseInt(el1);
            int score2 = Integer.parseInt(el2);
            return Integer.compare(score1, score2);
        });
        frameColumn.setPrefWidth(195);
        tableView.getColumns().add(nickColumn);
        tableView.getColumns().add(testColumn);
        tableView.getColumns().add(frameColumn);

        tableView.setItems(data);
    }

    private TableColumn<ObservableList<String>,String> createColumn(String name, int i)
    {
        TableColumn<ObservableList<String>,String> column= new TableColumn<>(name);
        //ustawienie sortowania alfabetycznego kolumny wyrazów
        column.setSortType(TableColumn.SortType.DESCENDING);
        column.setPrefWidth(290);
        //ustawienie wyswietlanego typu dla komórki tabeli
        column.setCellValueFactory(cellData -> {
            String value = cellData.getValue().get(i);
            return new SimpleStringProperty(value);
        });
        column.getStyleClass().add("defaultText");
        return column;
    }


    public void showMainScene()
    {
        mainStage.setScene(mainScene);
    }
}
