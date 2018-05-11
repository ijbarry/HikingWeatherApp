package uk.ac.cam.interaction_design.group02.hiking_app.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
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
}
