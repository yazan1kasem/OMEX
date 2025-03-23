package com.example.omex_quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class starter extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader= new FXMLLoader(OMEX_Applikation.class.getResource("META-Bereich.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),  1350, 700);
        stage.setTitle("OMEX_QUIZ");
        stage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getBounds();
        // Set the minimum width of the stage to the screen's width
        stage.setMinWidth(screenBounds.getWidth()/2);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
