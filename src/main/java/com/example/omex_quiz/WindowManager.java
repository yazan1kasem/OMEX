package com.example.omex_quiz;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WindowManager {

    public static <T> T setNeuesFenster(String altesFenster, String neuesFenster) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(neuesFenster));
            Parent root = loader.load();
            Stage oldStage = (Stage) root.getScene().getWindow();
            oldStage.close();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Übernahme der Größe des neuen Fensters aus der FXML-Datei
            Scene scene = stage.getScene();
            double width = scene.getWidth();
            double height = scene.getHeight();
            stage.setWidth(width);
            stage.setHeight(height);

            // Zugriff auf den Controller des neuen Fensters
            T controller = loader.getController();

            stage.show();

            return controller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T, U> void addFenster(String hauptFenster, String neuesFenster) {
        try {
            FXMLLoader hauptLoader = new FXMLLoader(WindowManager.class.getResource(hauptFenster));
            FXMLLoader neuesLoader = new FXMLLoader(WindowManager.class.getResource(neuesFenster));

            Parent hauptRoot = hauptLoader.load();
            Parent neuesRoot = neuesLoader.load();

            T hauptController = hauptLoader.getController();
            U neuesController = neuesLoader.getController();

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(hauptRoot, neuesRoot);

            Stage stage = new Stage();
            stage.setScene(new Scene(stackPane));

            stage.show();

            // Zugriff auf die Controller beider Fenster
            // Hier kannst du die gewünschten Methoden aufrufen
            // Beispiel: hauptController.someMethod() und neuesController.anotherMethod()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T setNeuesFensterMitDaten(String altesFenster, String neuesFenster, Object daten) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(neuesFenster));
            Parent root = loader.load();
            Stage oldStage = (Stage) root.getScene().getWindow();
            oldStage.close();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            T controller = loader.getController();
            übergebeDatenAnController(controller, daten);

            stage.show();

            return controller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void übergebeDatenAnController(T controller, Object daten) {
        try {
            Method setDatenMethod = controller.getClass().getMethod("setDaten", daten.getClass());
            setDatenMethod.invoke(controller, daten);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // Controller hat keine setDaten-Methode oder es ist ein Fehler aufgetreten
            e.printStackTrace();
        }
    }

    public static <T> T öffneModalfenster(String fxmlDatei) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(fxmlDatei));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            T controller = loader.getController();

            stage.showAndWait();

            return controller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T, U> void übertrageDaten(T controller1, U controller2) {
        Class<?> controller1Class = controller1.getClass();
        Class<?> controller2Class = controller2.getClass();

        Method[] controller1Methods = controller1Class.getDeclaredMethods();
        Method[] controller2Methods = controller2Class.getDeclaredMethods();

        for (Method method1 : controller1Methods) {
            for (Method method2 : controller2Methods) {
                if (method1.getName().equals(method2.getName())) {
                    try {
                        method2.invoke(controller2, method1.invoke(controller1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static <T> T schließeAktuellesFensterUndÖffneNeues(String neuesFenster, Double breite, Double höhe) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(neuesFenster));
            Parent root = loader.load();

            Stage aktuellesStage = (Stage) root.getScene().getWindow();
            aktuellesStage.close();

            Stage neueStage = new Stage();
            neueStage.setScene(new Scene(root));

            // Anpassen der Größe des neuen Fensters
            if (breite != null) {
                neueStage.setWidth(breite);
            }
            if (höhe != null) {
                neueStage.setHeight(höhe);
            }

            T controller = loader.getController();

            neueStage.show();

            return controller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
