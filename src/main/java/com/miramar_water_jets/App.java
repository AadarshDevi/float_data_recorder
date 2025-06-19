package com.miramar_water_jets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("float_data_plotter.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        FloatDataPlotter fdp = fxmlLoader.getController();

        scene = new Scene(anchorPane);
        stage.setTitle("Float Data Plotter");
        stage.setScene(scene);
        stage.show();

        fdp.run();
    }

    public static void main(String[] args) {
        launch();
    }

}