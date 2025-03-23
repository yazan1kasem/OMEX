package com.example.omex_quiz;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.application.Platform;

import org.jetbrains.annotations.NotNull;

public class UnterkategorieController implements Initializable {

    //private MainApp mainApp;
    private HauptkategorieController hauptkategorieController;

    /*public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }*/

    public void setHauptkategorieController(HauptkategorieController hauptkategorieController) {
        this.hauptkategorieController = hauptkategorieController;
    }

    /*@FXML
    private void handleZurueckButtonAction() {
        mainApp.openHauptkategorie();
    }*/
    private static Connection connection;
    @FXML
    private Label ConnectionL;
    @FXML
    private ChoiceBox<String> unterkategorieAuswahl;
    @FXML
    private AnchorPane UKAnchorPane;

    private String hauptkategorie;
    @FXML
    private AnchorPane levelPane;
    @FXML
    private Pane rootPane;

    public void setHauptkategorie(String hauptkategorie) {
        this.hauptkategorie = hauptkategorie;
    }

    private String ausgewaehlteKategorie;

    public void setAusgewaehlteKategorie(String kategorie) {
        ausgewaehlteKategorie = kategorie;
    }

    @FXML
    private ImageView imageViewDoktor1;
    @FXML
    private ImageView imageViewGear;
    String imagePathDoktor1 = "/Images/Doktor 1.png";
    @FXML
    private ListView<String> unterkategorieListView;@FXML

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageViewDoktor1.setImage(new Image(getClass().getResourceAsStream(imagePathDoktor1)));
    }

    /*public void loadUnterkategorien(String kategorie) {
        // Code zum Laden der passenden Unterkategorien basierend auf der ausgewählten Kategorie
        ObservableList<String> items = FXCollections.observableArrayList();

        switch (kategorie) {
            case "Blut":
                items.addAll("Blut allgemein", "Blutgruppe A", "Blutgruppe B", "Blutgruppe AB", "Blutgruppe 0");
                break;
            case "Gelenke":
                items.addAll("Gelenke allgemein", "echte Gelenke", "unechte Gelenke", "Amphiathrosen", "Kugelgelenke");
                break;
            case "Knochen":
                items.addAll("Knochen allgemein", "Knochenmark", "Pneumatische Knochen", "Schädelknochen", "Armknochen");
                break;
            case "Nervensystem":
                items.addAll("Zentrales Nervensystem", "peripheres Nervensystem", "somatisches Nervensystem", "vegetatives Nervensystem", "Enterisches Nervensystem");
                break;
            case "Organe":
                items.addAll("Herz", "Lunge", "Magen", "Leber", "Niere", "Bauchspeicheldrüse", "Milz", "Darm", "Blase", "Gebärmutter");
                break;
            default:
                break;
        }

        // Beispielcode zum Anzeigen von Unterkategorien in einem ListView
        unterkategorieListView.setItems(items);
    }*/

    //------------------------------------------------------------------------------------------------
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    //------------------------------------------------------------------------------------------------

    public void loadUnterkategorien(@NotNull String kategorie) {
        ConnectionMethod();
        ObservableList<String> items = FXCollections.observableArrayList();
        ausgewaehlteKategorie = kategorie;
        try {
            String query;
            switch (kategorie) {
                case "Blut":
                    query = "SELECT UK_Bez FROM UK_UnterKategorie WHERE UK_HK_ID = 1";
                    break;
                case "Gelenke":
                    query = "SELECT UK_Bez FROM UK_UnterKategorie WHERE UK_HK_ID = 2";
                    break;
                case "Knochen":
                    query = "SELECT UK_Bez FROM UK_UnterKategorie WHERE UK_HK_ID = 3";
                    break;
                case "Nervensystem":
                    query = "SELECT UK_Bez FROM UK_UnterKategorie WHERE UK_HK_ID = 4";
                    break;
                case "Organe":
                    query = "SELECT UK_Bez FROM UK_UnterKategorie WHERE UK_HK_ID = 5";
                    break;
                default:
                    query = "";
                    break;
            }
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String unterkategorie = rs.getString("UK_Bez");
                items.add(unterkategorie);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Liste der Unterkategorien setzen
        unterkategorieListView.setItems(items);
        unterkategorieListView.setCellFactory(listView -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
                } else {
                    setText(item);
                    setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
                }
            }
        });

        unterkategorieListView.setCellFactory(listView -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
                } else {
                    setText(item);
                    setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
                }
            }
        });

        unterkategorieListView.setOnMousePressed(event -> {
            ListCell<String> cell = (ListCell<String>) event.getSource();
            cell.toFront();
            cell.setStyle("-fx-background-color: #050554; -fx-text-fill: #65c3ea;");
        });

        unterkategorieListView.setOnMouseReleased(event -> {
            ListCell<String> cell = (ListCell<String>) event.getSource();
            cell.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        });


        //MainApp.getInstance().setUnterkategorieContent();

        unterkategorieListView.toFront();

        // Aufruf einer anderen Methode
        //executeAfterLoad();
    }

    public void executeAfterLoad() {
        MainApp mainApp = MainApp.getInstance();
        Platform.runLater(() -> {
            mainApp.setUnterkategorieContent(ausgewaehlteKategorie);
        });
    }


    private void ConnectionMethod() {
        try {
            if (connection != null)
                return;
            //ConnectionL.setText("Creating Connection to Database...");
            //ConnectionL.setStyle("-fx-text-fill: #252585; -fx-background-color: #ee6060; -fx-background-radius: 70;");
            connection = DriverManager.getConnection("jdbc:sqlite:OMEX.db");
            if (!connection.isClosed()) {
                //ConnectionL.setText("Connection established");
                //ConnectionL.setStyle("-fx-text-fill: #252585; -fx-background-color: #b4f1a7; -fx-background-radius: 70;");
                connection = DriverManager.getConnection("jdbc:sqlite:OMEX.sqlite");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed())
                            //ConnectionL.setText("Connection to Database closed");
                            //ConnectionL.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-label-padding: 5,10,5,10; -fx-background-radius: 5;-fx-background-color: Blue");
                            ;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    EntityManager em = Persistence.createEntityManagerFactory("Quiz").createEntityManager();
    @FXML
    private void weiterButtonAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setWidth(1200);
        stage.setHeight(750);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Fragen.fxml"));
        try {
            Parent root = loader.load();
            FragenController fragenController = loader.getController();
            fragenController.Unterkategoriewähler(em.createQuery("select Uk.id from UK_UnterKategorie Uk where Uk.bezeichnung=: kategoriename", Integer.class)
                    .setParameter("kategoriename", unterkategorieListView.getSelectionModel().getSelectedItem())
                    .getSingleResult());

            stage.setScene(new Scene(root));
            ConnectionMethod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void zurueckButtonAction(ActionEvent actionEvent) {
        // zurück zur vorherigen Seite
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setWidth(1350);
        stage.setHeight(750);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/omex_quiz/Hauptkategorie.fxml"));
        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            ConnectionMethod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void zurueckButtonAction(ActionEvent actionEvent) {
        /*// Zurück zur vorherigen Seite
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setWidth(1350);
        stage.setHeight(750);*/

        // Aufruf der setHauptkategorieContent-Methode in der MainApp, um die rechte Seite zu Hauptkategorie.fxml zu setzen
        MainApp.getInstance().setHauptkategorieContent();

        // Schließen des aktuellen Fensters
        //stage.close();
    }





    @FXML
    private void handleSettingsButtonAction(ActionEvent event) {
        // Erstelle eine neue Stage für die Einstellungs-Box
        Stage settingsStage = new Stage();
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.setTitle("Einstellungen");
        settingsStage.setResizable(false);

        // Erstelle die Inhalte der Einstellungs-Box
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        // Füge verschiedene Steuerelemente hinzu (z.B. Schieberegler für Lautstärke, Helligkeit usw.)
        Slider volumeSlider = new Slider(0, 100, 50);
        vbox.getChildren().add(new javafx.scene.control.Label("Lautstärke"));
        vbox.getChildren().add(volumeSlider);

        //-----------------------------------------------------------------------------------

        Slider brightnessSlider = new Slider(0, 100, 50);
        vbox.getChildren().add(new Label("Helligkeit"));
        vbox.getChildren().add(brightnessSlider);

// Laden der zuletzt gespeicherten Helligkeit, standardmäßig 50%
        double savedBrightness = 50.0; // Standardwert
        try (BufferedReader reader = new BufferedReader(new FileReader("settings.txt"))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                savedBrightness = Double.parseDouble(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        brightnessSlider.setValue(savedBrightness);

        brightnessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double brightness = newValue.doubleValue() / 100.0;
            String backgroundColor = String.format("-fx-background-color: rgba(0, 0, 0, %.2f);", 1.0 - brightness);
            Node rootNode = settingsStage.getScene().getRoot();
            rootNode.setStyle(backgroundColor);
        });

// Speichern der Helligkeit beim Schließen der Einstellungen
        settingsStage.setOnCloseRequest(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("settings.txt"))) {
                writer.write(Double.toString(brightnessSlider.getValue()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

// Erstellen des Speicherbuttons
        Button saveButton = new Button("Speichern");
        vbox.getChildren().add(saveButton);
        saveButton.setOnAction(e -> {
            // Speichern der Helligkeit
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("settings.txt"))) {
                writer.write(Double.toString(brightnessSlider.getValue()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Schließe die Einstellungs-Box
            settingsStage.close();
        });

        //-----------------------------------------------------------------------------------

        // Erstelle die Szene und füge die Inhalte hinzu
        Scene scene = new Scene(vbox, 200, 300);
        settingsStage.setScene(scene);

        // Zeige die Einstellungs-Box an
        settingsStage.show();

        // auf META-Bereich kommen
        Button questionButton = new Button("Fragen verwalten");
        questionButton.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("META-Bereich.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Fragen verwalten");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        vbox.getChildren().add(questionButton);
    }
}

