package uk.ac.cam.interaction_design.group02.hiking_app.frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private BorderPane mainContainer;

    @FXML
    private Button mapButton;

    @FXML
    private Button addButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button settingsButton;

    private Node mapControl;
    private Node homeControl;
    private Node settingsControl;

    public MainWindow() throws IOException {
        homeControl = new HomeControl();
        settingsControl = new SettingsControl();
        mapControl = new MapControl();
    }

    /* TODO: Decide on styling */
    private void markSet(Button b) {
        b.setStyle("-fx-background-color: lightgray;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
    }

    private void markUnset(Button b) {
        b.setStyle("-fx-background-color: white;");
    }

    private void markAllUnset() {
        markUnset(settingsButton);
        markUnset(addButton);
        markUnset(mapButton);
        markUnset(homeButton);
    }

    @FXML
    private void handleMapButtonAction(ActionEvent e) {
        mainContainer.setCenter(mapControl);
        markAllUnset();
        markSet(mapButton);
        markSet(addButton);
    }

    @FXML
    private void handleHomeButtonAction(ActionEvent e) {
        mainContainer.setCenter(homeControl);
        markAllUnset();
        markSet(homeButton);
    }

    @FXML
    private void handleSettingsButtonAction(ActionEvent e) {
        mainContainer.setCenter(settingsControl);
        markAllUnset();
        markSet(settingsButton);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        Scene scene = new Scene(root, 300, 275);

        primaryStage.setTitle("Hiking Weather Application");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(100);
        primaryStage.setMinWidth(250);

        primaryStage.show();
    }

    /**
     * Method run on FXML load to initialise (ie. default to home page)
     */
    @FXML
    public void initialize() {
        markAllUnset();
        markSet(homeButton);
        mainContainer.setCenter(homeControl);
    }
}
