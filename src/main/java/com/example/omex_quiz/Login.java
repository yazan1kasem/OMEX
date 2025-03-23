package com.example.omex_quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader= new FXMLLoader(OMEX_Applikation.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setResizable(false);
        stage.setTitle("OMEX_QUIZ");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}
