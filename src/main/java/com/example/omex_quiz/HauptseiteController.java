package com.example.omex_quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HauptseiteController {
    @FXML
    private BorderPane mainborder;
    @FXML
    private BorderPane Levelborder;

    public BorderPane getLevelborder() {
        return Levelborder;
    }

    public void setLevelborder(BorderPane levelborder) {
        Levelborder = levelborder;
    }

    private HauptseiteController() {

    }

    public static HauptseiteController create() {
        FXMLLoader fxmlLoader = new FXMLLoader(HauptseiteController.class.getResource("Hauptseite.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fxmlLoader.getController();
    }

    public void setMain(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
        Parent levelRoot = fxmlLoader.load();
        mainborder.getChildren().removeAll();
        mainborder.getChildren().add(levelRoot);
    }
}
