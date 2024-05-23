module JavaRMI {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.sql;

    opens client to javafx.fxml;
    exports client;
    exports service;
    exports database;
}