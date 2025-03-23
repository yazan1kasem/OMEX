package com.example.omex_quiz;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import javax.persistence.criteria.Fetch;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class HauptkategorieController implements Initializable {
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private Button Knochen;
    @FXML
    private Button Gelenke;
    @FXML
    private Button Blut;
    @FXML
    private Button Nervensystem;
    @FXML
    private Button Organe;
    @FXML
    private AnchorPane HKAnchorPane;

    @FXML
    private ChoiceBox<String> hauptkategorieAuswahl;
    private String ausgewaehlteKategorie;

    @FXML
    private ImageView imageViewKnochen;
    @FXML
    private ImageView imageViewMuskel;
    @FXML
    private ImageView imageViewBlut;
    @FXML
    private ImageView imageViewNervensystem;
    @FXML
    private ImageView imageViewOrgane;

    private String imagePathKnochen = "/Images/Knochen.png";
    private String imagePathMuskel = "/Images/Muskeln.png";
    private String imagePathBlut = "/Images/Blut.png";
    private String imagePathNervensystem = "/Images/Nervensystem.png";
    private String imagePathOrgane = "/Images/Organe.png";

    private HauptkategorieController hauptkategorieController;

    public void setHauptkategorieController(HauptkategorieController controller) {
        this.hauptkategorieController = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageViewKnochen.setImage(new Image(getClass().getResourceAsStream(imagePathKnochen)));
        imageViewMuskel.setImage(new Image(getClass().getResourceAsStream(imagePathMuskel)));
        imageViewBlut.setImage(new Image(getClass().getResourceAsStream(imagePathBlut)));
        imageViewNervensystem.setImage(new Image(getClass().getResourceAsStream(imagePathNervensystem)));
        imageViewOrgane.setImage(new Image(getClass().getResourceAsStream(imagePathOrgane)));


    }

    public void handleHyperlinkAction(ActionEvent event) {
        try {
            String fxmlFile = null;
            String buttonId = ((Button) event.getSource()).getId();
            switch (buttonId) {
                case "Knochen":
                    fxmlFile = "Unterkategorie.fxml";
                    ausgewaehlteKategorie = "Knochen";
                    //openUnterkategorie();
                    mainApp.setUnterkategorieContent(ausgewaehlteKategorie);
                    break;
                case "Gelenke":
                    fxmlFile = "Unterkategorie.fxml";
                    ausgewaehlteKategorie = "Gelenke";
                    //openUnterkategorie();
                    mainApp.setUnterkategorieContent(ausgewaehlteKategorie);
                    break;
                case "Blut":
                    fxmlFile = "Unterkategorie.fxml";
                    ausgewaehlteKategorie = "Blut";
                    //openUnterkategorie();
                    mainApp.setUnterkategorieContent(ausgewaehlteKategorie);
                    break;
                case "Nervensystem":
                    fxmlFile = "Unterkategorie.fxml";
                    ausgewaehlteKategorie = "Nervensystem";
                    //openUnterkategorie();
                    mainApp.setUnterkategorieContent(ausgewaehlteKategorie);
                    break;
                case "Organe":
                    fxmlFile = "Unterkategorie.fxml";
                    ausgewaehlteKategorie = "Organe";
                    //openUnterkategorie();
                    mainApp.setUnterkategorieContent(ausgewaehlteKategorie);
                    break;
            }
            InputStream inputStream = getClass().getResourceAsStream(fxmlFile);
            if (inputStream == null) {
                System.out.println("Die Datei wurde nicht gefunden");
            } else {
                System.out.println("Die Datei wurde gefunden");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            UnterkategorieController controller = loader.getController();
            controller.loadUnterkategorien(ausgewaehlteKategorie);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            //MainApp.getInstance().setUnterkategorieContent();
            //mainApp.setUnterkategorieContent(ausgewaehlteKategorie);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void handleHyperlinkAction(ActionEvent event) {
        try {
            String fxmlFile = null;
            String buttonId = ((Button) event.getSource()).getId();
            switch (buttonId) {
                case "Knochen":
                    fxmlFile = "Unterkategorie.fxml";
                    MainApp.getInstance().setUnterkategorieContent();
                    ausgewaehlteKategorie = "Knochen";
                    //MainApp.getInstance().setUnterkategorieContent();
                    //openUnterkategorie(fxmlFile);
                    break;
                case "Gelenke":
                    fxmlFile = "Unterkategorie.fxml";
                    ausgewaehlteKategorie = "Gelenke";
                    MainApp.getInstance().setUnterkategorieContent();
                    break;
                case "Blut":
                    fxmlFile = "Unterkategorie.fxml";
                    ausgewaehlteKategorie = "Blut";
                    MainApp.getInstance().setUnterkategorieContent();
                    break;
                case "Nervensystem":
                    fxmlFile = "Unterkategorie.fxml";
                    ausgewaehlteKategorie = "Nervensystem";
                    MainApp.getInstance().setUnterkategorieContent();
                    break;
                case "Organe":
                    fxmlFile = "Unterkategorie.fxml";
                    ausgewaehlteKategorie = "Organe";
                    MainApp.getInstance().setUnterkategorieContent();
                    break;
            }
            InputStream inputStream = getClass().getResourceAsStream(fxmlFile);
            if (inputStream == null) {
                System.out.println("Die Datei wurde nicht gefunden");
            } else {
                System.out.println("Die Datei wurde gefunden");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            UnterkategorieController controller = loader.getController();
            controller.loadUnterkategorien(ausgewaehlteKategorie);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void vorrübergehendWeiter(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Unterkategorie.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(750);
        stage.show();
    }

    private String user;
    @FXML
    private AnchorPane rootParent;
    private LevelController levelController;
    @FXML
    private HBox levelbox;

    private LevelController2 levelController2;

    public void addLevelArea(String user) {
        // Lade den Level-FXMLLoader und erstelle eine Instanz des LevelControllers
        FXMLLoader fxmlLoaderLevel = new FXMLLoader(getClass().getResource("Level2.fxml"));
        try {
            Parent rootLevel = fxmlLoaderLevel.load();
            levelController = fxmlLoaderLevel.getController();
            levelController.setUser(user);

            // Fügen Sie den Levelbereich zum Hauptfenster hinzu
            rootParent.getChildren().add(rootLevel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*public void openUnterkategorie() {
        try {
            FXMLLoader unterkategorieLoader = new FXMLLoader(getClass().getResource("Unterkategorie.fxml"));
            Parent unterkategorieRoot = unterkategorieLoader.load();
            UnterkategorieController unterkategorieController = unterkategorieLoader.getController();
            unterkategorieController.setMainApp(this.mainApp);
            unterkategorieController.setHauptkategorieController(hauptkategorieController);

            SplitPane splitPane = (SplitPane) HKAnchorPane.getParent();
            splitPane.getItems().set(1, unterkategorieRoot);

            String ausgewaehlteKategorie = hauptkategorieAuswahl.getValue();
            UnterkategorieController controller = unterkategorieLoader.getController();
            controller.loadUnterkategorien(ausgewaehlteKategorie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private Stage mainStage;

    /*private void openUnterkategorie(URL fxmlURL) {
        try {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Schließe das aktuelle Fenster
            Stage currentStage = (Stage) HKAnchorPane.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void openUnterkategorie(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Zugriff auf den Controller der Unterkategorie
            UnterkategorieController unterkategorieController = loader.getController();
            unterkategorieController.loadUnterkategorien(ausgewaehlteKategorie);

            // Ersetzen der rechten Seite des SplitPanes
            SplitPane splitPane = (SplitPane) HKAnchorPane.getParent();
            ObservableList<Node> items = splitPane.getItems();
            items.set(1, root);

            // Schließen des aktuellen Fensters
            Stage currentStage = (Stage) HKAnchorPane.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public UnterkategorieController getUnterkategorieController() {
        FXMLLoader unterkatLoader = new FXMLLoader(getClass().getResource("Unterkategorie.fxml"));
        Parent unterkatRoot;
        try {
            unterkatRoot = unterkatLoader.load();
            return unterkatLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}