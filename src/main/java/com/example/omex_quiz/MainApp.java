package com.example.omex_quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.example.omex_quiz.UnterkategorieController;
import com.example.omex_quiz.HauptkategorieController;

import java.io.InputStream;

public class MainApp extends Application {
    private static MainApp instance;
    private Stage primaryStage;
    private Stage loginStage;
    private Stage mainStage;

    private HauptkategorieController hauptkategorieController;
    private LevelController2 levelController;

    public void setHauptkategorieController(HauptkategorieController controller) {
        this.hauptkategorieController = controller;
    }

    public void start(Stage primaryStage) {
        // Initialisierung und Erstellung des LevelController2
        levelController = new LevelController2();

        // Aufruf der Methode setUser() in LevelController2 mit 'username'
        //levelController.setUser(username);

        try {
            this.primaryStage = primaryStage;
            instance = this;

            // Laden der FXML-Datei für Login
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginRoot = loginLoader.load();
            LoginController loginController = loginLoader.getController();
            loginController.setMainApp(this);

            // Erstellen einer Stage für den Login
            loginStage = new Stage();
            loginStage.setScene(new Scene(loginRoot));
            loginStage.setResizable(false);
            loginStage.show();
            loginStage.setTitle("OMEX-QUIZ Login");

            // Laden der FXML-Datei für Unterkateogire
            FXMLLoader unterkatLoader = new FXMLLoader(getClass().getResource("Unterkategorie.fxml"));
            Parent unterkatRoot = unterkatLoader.load();
            UnterkategorieController unterkatController = unterkatLoader.getController();
            unterkatController.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginSuccessful() {
        try {
            // Laden der FXML-Dateien für Hauptkategorie.fxml und Level2.fxml
            FXMLLoader hauptkategorieLoader = new FXMLLoader(getClass().getResource("Hauptkategorie.fxml"));
            Parent hauptkategorieRoot = hauptkategorieLoader.load();
            HauptkategorieController hauptkategorieController = hauptkategorieLoader.getController();
            hauptkategorieController.setMainApp(this);

            FXMLLoader level2Loader = new FXMLLoader(getClass().getResource("Level2.fxml"));
            Parent level2Root = level2Loader.load();
            LevelController2 level2Controller = level2Loader.getController();
            level2Controller.setMainApp(this);

            // Erstellen eines SplitPane und Hinzufügen der beiden FXML-Roots in umgekehrter Reihenfolge
            SplitPane splitPane = new SplitPane();
            splitPane.getItems().addAll(level2Root, hauptkategorieRoot);
            splitPane.setDividerPositions(0.7);

            // Styling des Trennstrichs des SplitPane
            splitPane.setStyle("-fx-divider-color: transparent;");

            // Erstellen der Scene mit dem SplitPane und Hintergrundfarbe "#3636c0"
            Scene scene = new Scene(splitPane);
            scene.getRoot().setStyle("-fx-background-color: #3636c0;");

            // Erstellen einer Stage für das Hauptfenster
            mainStage = new Stage();
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.setWidth(1350);
            mainStage.setHeight(750);
            mainStage.setTitle("OMEX-QUIZ");

            // Beim Schließen der Hauptfenster-Stage die Login-Stage schließen
            mainStage.setOnCloseRequest(event -> {
                loginStage.close();
            });

            // Anzeigen der Hauptfenster-Stage und Schließen der Login-Stage
            mainStage.show();
            loginStage.close();

            // Öffnen der Unterkategorie
            //hauptkategorieController.openUnterkategorie(); // Hier rufen Sie die Methode openUnterkategorie() ohne Parameter auf

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnterkategorieContent(String ausgewaehlteKategorie) {
        try {
            FXMLLoader unterkatLoader = new FXMLLoader(getClass().getResource("Unterkategorie.fxml"));
            Parent unterkatRoot = unterkatLoader.load();
            UnterkategorieController unterkatController = unterkatLoader.getController();
            unterkatController.setMainApp(this);

            SplitPane splitPane = (SplitPane) mainStage.getScene().getRoot();
            splitPane.getItems().set(1, unterkatRoot);

            // Methode loadUnterkategorien aufrufen
            unterkatController.loadUnterkategorien(ausgewaehlteKategorie);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setHauptkategorieContent() {
        try {
            FXMLLoader hauptkatLoader = new FXMLLoader(getClass().getResource("Hauptkategorie.fxml"));
            Parent hauptkatRoot = hauptkatLoader.load();
            HauptkategorieController hauptkatController = hauptkatLoader.getController();
            hauptkatController.setMainApp(this);

            SplitPane splitPane = (SplitPane) mainStage.getScene().getRoot();
            splitPane.getItems().set(1, hauptkatRoot);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MainApp getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }
}