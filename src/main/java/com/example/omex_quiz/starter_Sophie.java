package com.example.omex_quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class starter_Sophie extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader= new FXMLLoader(OMEX_Applikation.class.getResource("Level2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),  1350, 700);
        stage.setTitle("OMEX_QUIZ");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
