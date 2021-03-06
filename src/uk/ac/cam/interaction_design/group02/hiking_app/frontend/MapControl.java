package uk.ac.cam.interaction_design.group02.hiking_app.frontend;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MapControl extends GridPane {
    public MapControl() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MapControl.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        //Controller HAS to be set here, otherwise you get a self-cyclic instantiation => VERY BAD

        fxmlLoader.load();
    }
}
