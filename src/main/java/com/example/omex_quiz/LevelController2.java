package com.example.omex_quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LevelController2  implements Initializable {
    private static Connection connection;
    @FXML
    private Label ConnectionL;
    @FXML
    private Label lblUsername;
    @FXML
    private ImageView profileImage;
    @FXML
    private HBox hbox;
    @FXML
    private ImageView imageViewGear;
    @FXML
    private ImageView imageViewLogout;
    String imagePathGear = "/Images/gear.png";
    String imagePathLogout = "/Images/logout.png";
    @FXML
    private AnchorPane UKAnchorPane;
    @FXML
    private AnchorPane HKAnchorPane;
    private String username;
    EntityManager em = Persistence.createEntityManagerFactory("Quiz").createEntityManager();

    @FXML
    private StackPane stackPane;

    @FXML
    private Rectangle rectangle;

    @FXML
    private ImageView imageView;

    @FXML
    private Label label;

    // Methode zum Einstellen der Farbe des Rechtecks
    public void setRectangleColor(Color color) {
        rectangle.setStroke(color);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageViewGear.setImage(new Image(getClass().getResourceAsStream(imagePathGear)));
        imageViewLogout.setImage(new Image(getClass().getResourceAsStream(imagePathLogout)));

        //anzeigenAnzahlFragen();
        //anzeigenGemachteAnzahlFragen();

        int anzahlGemachteFragen = getAnzahlGemachteFragenInDatenbank();
        int anzahlFragen = getAnzahlFragenInDatenbank();
        AusgabeText(anzahlGemachteFragen, anzahlFragen);

        // Verbindung zur Datenbank herstellen
        ConnectionMethod();

        //-------------------------------------------------------------------------------

        pickImageButton.setOnAction(event -> openImagePicker());

        //-------------------------------------------------------------------

        StackPane.setAlignment(lblUsername, Pos.CENTER);
        //setUser(username);
    }

    public LevelController2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Level2.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            // Weitere Initialisierungen oder Manipulationen des FXML-Layouts
            // können hier durchgeführt werden

            // Optional: Eine Scene erstellen und das Layout anzeigen
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String currentUsername;

    public void setUser(String user) {
        if (currentUsername == null || !currentUsername.equals(user)) {
            lblUsername.setText(user);
            currentUsername = user;
            //String level = retrieveUserLevelFromDatabase(user);
            //label.setText(level);
        }
    }

    private String retrieveUserLevelFromDatabase(String username) {
        String levelQuery = "SELECT U_Level FROM U_User WHERE U_Username = ?";
        try (Connection connection = DriverManager.getConnection("dbc:sqlite:OMEX.db");
             PreparedStatement statement = connection.prepareStatement(levelQuery)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("U_Level");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*private String retrieveUserLevelFromDatabase(String user) {
        // Establish a database connection
        try (Connection connection = DriverManager.getConnection("dbc:sqlite:OMEX.db", "sophie", "admin")) {
            // Prepare a SQL query to retrieve the U_Level for the given user
            String query = "SELECT U_Level FROM your_table WHERE username = current_user";
            PreparedStatement statement = connection.prepareStatement(query);
            //statement.setString(1, user);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the U_Level value from the result set
            if (resultSet.next()) {
                return resultSet.getString("U_Level");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential exceptions
        }

        return "N/A"; // Return a default value if the user or U_Level is not found
    }*/

    @FXML
    private VBox vbox;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressLabel;
    private int anzahlCount;
    private int gesamtCount;
    private int percentage;

    public void setAnzahlCount(int count) {
        this.anzahlCount = count;
    }

    public int getAnzahlCount() {
        return anzahlCount;
    }

    public int getAnzahlFragenInDatenbank() {
        int anzahlFragen = 0;

        String url = "dbc:sqlite:OMEX.db";

        EntityManager em= Persistence.createEntityManagerFactory("Quiz").createEntityManager();
        anzahlFragen = Integer.parseInt(String.valueOf(em.createQuery("select count(f) from F_Fragen f", Long.class).getSingleResult()));

        return anzahlFragen;
    }

    @FXML
    private Label anzahlFragenLabel;
    public void anzeigenAnzahlFragen() {
        int anzahlFragen = getAnzahlFragenInDatenbank();
        anzahlFragenLabel.setText("Anzahl der Fragen: " + anzahlFragen);
    }

    public int getAnzahlGemachteFragenInDatenbank() {
        int anzahlGemachteFragen = 0;

        String url = "dbc:sqlite:OMEX.db";

        EntityManager em= Persistence.createEntityManagerFactory("Quiz").createEntityManager();
        anzahlGemachteFragen = Integer.parseInt(String.valueOf(em.createQuery("select count(f) from US_UserFragen f", Long.class).getSingleResult()));

        return anzahlGemachteFragen;
    }

    @FXML
    private Label anzahlGemachteFragenLabel;
    public void anzeigenGemachteAnzahlFragen() {
        int anzahlGemachteFragen = getAnzahlGemachteFragenInDatenbank();
        anzahlGemachteFragenLabel.setText("Anzahl der Gemachten Fragen: " + anzahlGemachteFragen);

        // Aktualisiere den Fortschrittsbalken basierend auf dem gemachten Fortschritt
        //updateProgressBar(anzahlGemachteFragen);
    }

    @FXML
    private Label ausgabeLabel;
    public void AusgabeText(int anzahlGemachteFragen, int anzahlFragen) {
        String text = anzahlGemachteFragen + " von " + anzahlFragen + " Fragen richtig beantwortet";
        ausgabeLabel.setText(text);
    }

    /*public void updateProgressBar(int anzahlGemachteFragen, int anzahlFragen) {
        double fortschritt = (double) anzahlGemachteFragen / anzahlFragen;
        progressBar.setProgress(fortschritt);
        progressBar.setStyle("-fx-accent: green;"); // Fortschritt in grün anzeigen

        // Längen der ProgressBar anpassen
        double breite = progressBar.getWidth();
        double fortschrittBreite = fortschritt * breite;
        String style = String.format("-fx-background-color: white; -fx-background-insets: 0, 0, 0, %f;", fortschrittBreite);
        progressBar.setStyle(style);
    }*/

    //----------------------------------------------------------------------------------------

    /*@FXML
    private Label level;

    public void setLevelText(String username) {
        String levelQuery = "SELECT U_Level FROM U_User WHERE U_Username = ?";
        try (Connection connection = DriverManager.getConnection("dbc:sqlite:OMEX.db");
             PreparedStatement statement = connection.prepareStatement(levelQuery)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String level = resultSet.getString("U_Level");
                label.setText(level);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    //----------------------------------------------------------------------------------------

    private void ConnectionMethod() {
        try {
            if (connection != null)
                return;
            ConnectionL.setText("Creating Connection to Database...");
            ConnectionL.setStyle("-fx-text-fill: #252585; -fx-background-color: #ee6060; -fx-background-radius: 70;");
            connection = DriverManager.getConnection("jdbc:sqlite:OMEX.db");
            if (!connection.isClosed()) {
                ConnectionL.setText("Connection established");
                ConnectionL.setStyle("-fx-text-fill: #252585; -fx-background-color: #b4f1a7; -fx-background-radius: 70;");
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
                            ConnectionL.setText("Connection to Database closed");
                        ConnectionL.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-label-padding: 5,10,5,10; -fx-background-radius: 5;-fx-background-color: Blue");

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //-------------------------------------------------------------------------------------------------------------------------------------



    //--------------------------------------------------------------------------------------------

    /*@FXML
    private void UsernameAusgeben(ActionEvent event) {
        ConnectionMethod();
        String query = "SELECT U_Username FROM U_User";
        try {
            ResultSet rs = connection.createStatement().executeQuery(query);
            if (rs.next()) {
                lblUsername.setText(rs.getString("U_Username"));
            } else {
                lblUsername.setText("Kein Benutzername gefunden.");
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen des Benutzernamens: " + e.getMessage());
        }
    }*/

    private void setAnchorPaneBackground(AnchorPane anchorPane, Color color) {
        BackgroundFill backgroundFill = new BackgroundFill(color, null, null);
        Background background = new Background(backgroundFill);
        anchorPane.setBackground(background);
    }

    // Konvertierung von Color-Objekten in RGB-Code
    private String toRgbCode(Color color) {
        int r = (int) Math.round(color.getRed() * 255.0);
        int g = (int) Math.round(color.getGreen() * 255.0);
        int b = (int) Math.round(color.getBlue() * 255.0);
        return String.format("#%02X%02X%02X", r, g, b);
    }

//    @FXML
//    private void handleSettingsButtonAction(ActionEvent event) throws Exception {
//        // Erstelle eine neue Stage für die Einstellungs-Box
//        Stage settingsStage = new Stage();
//        settingsStage.initModality(Modality.APPLICATION_MODAL);
//        settingsStage.setTitle("Einstellungen");
//        settingsStage.setResizable(false);
//
//        // Erstelle die Inhalte der Einstellungs-Box
//        GridPane gridPane = new GridPane();
//        gridPane.setPadding(new Insets(20));
//        gridPane.setHgap(20);
//        gridPane.setVgap(20);
//
////-----------------------------------------------------------------------------------------------
//// Lautstärke-Slider
//        /*
//        Label volumeLabel = new Label("Lautstärke");
//        Slider volumeSlider = new Slider(0, 100, 50);
//        volumeSlider.setShowTickMarks(true);
//        volumeSlider.setShowTickLabels(true);
//        volumeSlider.setMajorTickUnit(50);
//        volumeSlider.setMinorTickCount(5);
//        gridPane.add(volumeLabel, 0, 0);
//        gridPane.add(volumeSlider, 1, 0);
//
//         */
//
////-----------------------------------------------------------------------------------------------
//// Farb-Steuerung
//        /*
//        Label colorLabel = new Label("Hintergrundfarbe");
//        ColorPicker colorPicker = new ColorPicker();
//        Slider brightnessSlider = new Slider(0, 100, 50);
//        brightnessSlider.setShowTickMarks(true);
//        brightnessSlider.setShowTickLabels(true);
//        brightnessSlider.setMajorTickUnit(50);
//        brightnessSlider.setMinorTickCount(5);
//
//         */
//
//// Erstelle die Szene und weise das AnchorPane als Wurzelknoten zu
//        AnchorPane anchorPane = new AnchorPane();
//        //anchorPane.setId("mainAnchorPane");
//        Scene scene = new Scene(anchorPane);
///*
//// Farbauswahl-Elemente hinzufügen
//        VBox colorBox = new VBox(10, colorLabel, colorPicker, brightnessSlider);
//        colorBox.setAlignment(Pos.CENTER);
//        anchorPane.getChildren().add(colorBox);
//
//        colorPicker.setOnAction(e -> {
//            Color pickedColor = colorPicker.getValue();
//            double brightness = brightnessSlider.getValue() / 100.0;
//            Color adjustedColor = pickedColor.deriveColor(0, 1, brightness, 1);
//            anchorPane.setStyle("-fx-background-color: " + toRgbCode(adjustedColor) + ";");
//        });*/
//        /*
//        // Verwenden der Methode in der Event-Handler-Methode für die Farbauswahl
//        colorPicker.setOnAction(e -> {
//            Color pickedColor = colorPicker.getValue();
//            double brightness = brightnessSlider.getValue() / 100.0;
//            Color adjustedColor = pickedColor.deriveColor(0, 1, brightness, 1);
//            setAnchorPaneBackground(anchorPane, adjustedColor);
//            setAnchorPaneBackground(UKAnchorPane, adjustedColor);
//            setAnchorPaneBackground(HKAnchorPane, adjustedColor);
//        });
//
//        VBox colorBox = new VBox(10, colorLabel, colorPicker, brightnessSlider);
//        colorBox.setAlignment(Pos.CENTER);
//        colorBox.setMaxWidth(150); // Setzen Sie die maximale Breite des Farbauswahl-Containers
//        anchorPane.getChildren().add(colorBox);
//
//         */
//
////-----------------------------------------------------------------------------------------------
//// Darkmode-Button
//        /*
//        Button darkModeButton = new Button("Darkmode");
//        darkModeButton.setPrefWidth(105); // setzen Sie die gewünschte Breite für den Button
//        gridPane.add(darkModeButton, 0, 3);
//
//// setzen Sie den Standardstil und die Schriftart
//        darkModeButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
////darkModeButton.setFont(Font.createFont("Arial", FontWeight.BOLD, 14));
//
//        darkModeButton.setOnAction(e -> {
//            if (darkModeButton.getText().equals("Darkmode")) {
//                // Wenn der Button im Normal-Modus ist
//                darkModeButton.setText("Lightmode");
//                darkModeButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
//                anchorPane.setStyle("-fx-background-color: black; -fx-text-fill: white;");
//            } else {
//                // Wenn der Button bereits im Darkmode ist
//                darkModeButton.setText("Darkmode");
//                darkModeButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
//                anchorPane.setStyle("-fx-background-color: white; -fx-text-fill: black;");
//            }
//        });
//
//         */
//
////--------------------------------------------------------------------------------------------
//
//        // META-Bereich-Button
//        String currentUser = lblUsername.getText();
//        if (currentUser.equals("yazan") || currentUser.equals("sophie") || currentUser.equals("medine")) {
//            Button questionButton = new Button("Fragen verwalten");
//            questionButton.setOnAction(e -> {
//                try {
//                    Parent root = FXMLLoader.load(getClass().getResource("META-Bereich.fxml"));
//                    Stage stage = new Stage();
//                    stage.initModality(Modality.APPLICATION_MODAL);
//                    stage.setTitle("Fragen verwalten");
//                    stage.setScene(new Scene(root));
//                    stage.show();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            });
//            gridPane.add(questionButton, 0, 2, 2, 1);
//        }
//
//        //-----------------------------------------------------------------------------------------------
//
//        // Erstelle die Szene und füge die Inhalte hinzu
//        //Scene scene = new Scene(gridPane);
//        settingsStage.setScene(scene);
//
//        // Zeige die Einstellungs-Box an
//        settingsStage.showAndWait();
//    }

    @FXML
    private AnchorPane hauptkategorieAnchorPane;

    @FXML
    private AnchorPane fragenAnchorPane;

    @FXML
    private AnchorPane unterkategorieAnchorPane;

    private SplitPane splitPane;

    public SplitPane getSplitPane() {
        return splitPane;
    }


    private void updateBackgroundColors(Color color) {
        /*String backgroundColorStyle = "-fx-background-color: " + toRgbCode(color) + ";";
        hauptkategorieAnchorPane.setStyle(backgroundColorStyle);
        fragenAnchorPane.setStyle(backgroundColorStyle);
        unterkategorieAnchorPane.setStyle(backgroundColorStyle);*/
    }

    @FXML
    private void handleSettingsButtonAction(ActionEvent event) throws Exception {
        // Erstelle eine neue Stage für die Einstellungs-Box
        Stage settingsStage = new Stage();
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.setTitle("Einstellungen");
        settingsStage.setResizable(false);

        // Erstelle die Inhalte der Einstellungs-Box
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(20);
        gridPane.setVgap(10); // Verringerte Zeilenhöhe auf 10

        //-----------------------------------------------------------------------------------------------
        // Lautstärke-Slider
        Label volumeLabel = new Label("Lautstärke");
        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setMajorTickUnit(50);
        volumeSlider.setMinorTickCount(5);
        gridPane.add(volumeLabel, 0, 0);
        gridPane.add(volumeSlider, 1, 0);

        //-----------------------------------------------------------------------------------------------

        // Farb-Steuerung
        Label colorLabel = new Label("Hintergrundfarbe");
        ColorPicker colorPicker = new ColorPicker();
        Slider brightnessSlider = new Slider(0, 100, 50);
        brightnessSlider.setShowTickMarks(true);
        brightnessSlider.setShowTickLabels(true);
        brightnessSlider.setMajorTickUnit(50);
        brightnessSlider.setMinorTickCount(5);

        colorPicker.setOnAction(e -> {
            Color pickedColor = colorPicker.getValue();
            double brightness = brightnessSlider.getValue() / 100.0;
            Color adjustedColor = pickedColor.deriveColor(0, 1, brightness, 1);

            updateBackgroundColors(adjustedColor);
        });

        VBox colorBox = new VBox(10, colorLabel, colorPicker, brightnessSlider);
        colorBox.setAlignment(Pos.CENTER);
        colorBox.setMaxWidth(150);
        gridPane.add(colorBox, 0, 2, 2, 1);

        //--------------------------------------------------------------------------------------------
        /*
        // META-Bereich-Button
        String currentUser = lblUsername.getText();
        if (currentUser.equals("yazan") || currentUser.equals("sophie") || currentUser.equals("medine")) {
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
            gridPane.add(questionButton, 0, 3, 2, 1);
        }
         */
        //-----------------------------------------------------------------------------------------------

        Scene anchorPaneScene = new Scene(gridPane); // Verwende den GridPane direkt als Szene

        settingsStage.setScene(anchorPaneScene); // Verwende die anchorPaneScene

        // Zeige die Einstellungs-Box an und warte, bis sie geschlossen wird
        settingsStage.showAndWait();
    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setWidth(850);
        stage.setHeight(450);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/omex_quiz/Login.fxml"));
        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            ConnectionMethod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void setProfileImageBorder() {
        profileImage.setStyle("-fx-border-color: red; -fx-border-width: 5px !important;");
    }*/

    private MainApp mainApp;

    private Stage mainStage;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private Button pickImageButton;

    @FXML
    private Label statusLabel;

    @FXML
    private void openImagePicker() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Muskel", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Knochen", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Nervensystem", "*.png"));

        Stage stage = (Stage) pickImageButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                statusLabel.setText("Bild ausgewählt: " + selectedFile.getName());
            } catch (Exception e) {
                statusLabel.setText("Fehler beim Laden des Bildes.");
            }
        }
    }

}
