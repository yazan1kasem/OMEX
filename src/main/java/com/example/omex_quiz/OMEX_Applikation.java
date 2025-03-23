package com.example.omex_quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class OMEX_Applikation extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OMEX_Applikation.class.getResource("Omex.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setTitle("OMEX_QUIZ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}