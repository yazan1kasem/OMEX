package com.example.omex_quiz;

import entities.F_Fragen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.ResultSet;
import javax.persistence.*;

public class FragenController extends Node implements Initializable {

    private EntityManager entityManager;
    private static Connection connection;
    @FXML
    private TableView<F_Fragen> tableView;
    @FXML
    private Label ConnectionL;
    @FXML
    private Label frage;
    @FXML
    private Button antwort1;
    @FXML
    private Button antwort2;
    @FXML
    private Button antwort3;
    @FXML
    private Button antwort4;
    @FXML
    private Button nextbtn;
    @FXML
    private Label erklaere;
    @FXML
    private Label erklaeren;

    private int richtigeAntwort;
    int ausgewähltekategorie;
    @FXML
    private ImageView doktor2;
    String imagePathDoktor2 = "/Images/Doktor2.png";
    private int frageid;
    public FragenController() {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConnectionMethod();
        doktor2.setImage(new Image(getClass().getResourceAsStream(imagePathDoktor2)));
    }
    private void fragenauswähler(){
        EntityManager em = Persistence.createEntityManagerFactory("Quiz").createEntityManager();
        // Ermittlung des Bereichs der Frage-IDs in der ausgewählten Unterkategorie
        Integer beginn = em.createQuery("SELECT min(f.id) FROM F_Fragen f WHERE f.unterkategorie.id = :ausgewählteUnterkategorieId", Integer.class)
                .setParameter("ausgewählteUnterkategorieId", ausgewähltekategorie)
                .getSingleResult();

        Integer ende = em.createQuery("SELECT max(f.id) FROM F_Fragen f WHERE f.unterkategorie.id = :ausgewählteUnterkategorieId", Integer.class)
                .setParameter("ausgewählteUnterkategorieId", ausgewähltekategorie)
                .getSingleResult();

        int indexbeginn = (beginn != null) ? beginn.intValue() : 0;
        int indexende = (ende != null) ? ende.intValue() : 0;

        // Generierung einer zufälligen Frage-ID innerhalb des Bereichs von indexbeginn und indexende
        Random random = new Random();
        int ausgewählteFrageId = random.nextInt(indexende - indexbeginn + 1) + indexbeginn;
        this.frageid= ausgewählteFrageId;

        // Abfrage der ausgewählten Frage
        TypedQuery<String> fragenQuery = em.createQuery("SELECT f.frage FROM F_Fragen f WHERE f.id = :ausgewählteFrageId", String.class);
        fragenQuery.setParameter("ausgewählteFrageId", ausgewählteFrageId);
        String ausgewählteFrage = fragenQuery.getSingleResult();
        // Setzen des Textes der Frage auf das abgerufene Frage-Objekt
        frage.setText(ausgewählteFrage);
        // Abfrage und Setzen der Antwortoptionen für die verschiedenen Antworten
        TypedQuery<String> antwort1query = em.createQuery("SELECT f.antwort1 FROM F_Fragen f WHERE f.id = :ausgewähltefrageid", String.class);
        antwort1.setText(antwort1query.setParameter("ausgewähltefrageid", ausgewählteFrageId).getSingleResult());
        TypedQuery<String> antwort2query = em.createQuery("SELECT f.antwort2 FROM F_Fragen f WHERE f.id = :ausgewähltefrageid", String.class);
        antwort2.setText(antwort2query.setParameter("ausgewähltefrageid", ausgewählteFrageId).getSingleResult());
        TypedQuery<String> antwort3query = em.createQuery("SELECT f.antwort3 FROM F_Fragen f WHERE f.id = :ausgewähltefrageid", String.class);
        antwort3.setText(antwort3query.setParameter("ausgewähltefrageid", ausgewählteFrageId).getSingleResult());
        TypedQuery<String> antwort4query = em.createQuery("SELECT f.antwort4 FROM F_Fragen f WHERE f.id = :ausgewähltefrageid", String.class);
        antwort4.setText(antwort4query.setParameter("ausgewähltefrageid", ausgewählteFrageId).getSingleResult());
        TypedQuery<String> erklaerenQuery = em.createQuery("SELECT f.erklaeren FROM F_Fragen f WHERE f.id = :ausgewähltefrageid", String.class);
        erklaeren.setText(erklaerenQuery.setParameter("ausgewähltefrageid", ausgewählteFrageId).getSingleResult());
        // Beispielaufruf in deiner fragenauswähler()-Methode für Antwort-Button 1
        int ausgewählteAntwort = 1; // Hier den Wert für die ausgewählte Antwort setzen
        checkAntwort(antwort1, ausgewählteFrageId, ausgewählteAntwort);
    }
    private void checkAntwort(Button antwortButton, int ausgewählteFrageId, int ausgewählteAntwort) {
        EntityManager em = Persistence.createEntityManagerFactory("Quiz").createEntityManager();
        // Abfrage der richtigen Antwort für die ausgewählte Frage
        TypedQuery<Integer> richtigeAntwortQuery = em.createQuery("SELECT f.richtigeAntwort FROM F_Fragen f WHERE f.id = :ausgewählteFrageId", Integer.class);
        richtigeAntwortQuery.setParameter("ausgewählteFrageId", ausgewählteFrageId);
        int richtigeAntwort = richtigeAntwortQuery.getSingleResult();
        // Überprüfung, ob die ausgewählte Antwort richtig oder falsch ist
        if (ausgewählteAntwort == richtigeAntwort) {
            antwortButton.setStyle("-fx-background-color: green;");
        } else {
            antwortButton.setStyle("-fx-background-color: red;");
        }
    }
    public void Unterkategoriewähler(int unterkategorieid){
        this.ausgewähltekategorie=unterkategorieid;
        fragenauswähler();
    }
    /*private boolean checkAnswer(Integer selectedAnswer) {
        EntityManager em = Persistence.createEntityManagerFactory("Quiz").createEntityManager();
        TypedQuery<String> richtigeAntwortQuery = em.createQuery("SELECT f.richtigeAntwort FROM F_Fragen f WHERE f.frage = :ausgewählteFrage", String.class);
        richtigeAntwortQuery.setParameter("ausgewählteFrage", frage.getText());
        String correctAnswer = richtigeAntwortQuery.getSingleResult();
        return selectedAnswer.equals(correctAnswer);
    }
    @FXML
    public void onAnswerSelected(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        int selectedAnswer = Integer.parseInt(selectedButton.getText());
        boolean isCorrect = checkAnswer(selectedAnswer);
        //selectedAnswer.equals(selectedButton.getText())
        if (isCorrect) {
            selectedButton.setStyle("-fx-background-fill: green");
        } else {
            selectedButton.setStyle("-fx-background-fill: red");
        }
    }*/
    public void Buttonclicked(ActionEvent actionEvent) {
        if (actionEvent.getSource() == nextbtn) {

        } else {
            System.out.println("Bitte klicken Sie auf die Nächste Frage Button");
        }
    }
    public void ConnectionMethod() {
        try {
            if (connection != null)
                return;
            ConnectionL.setText("Creating Connection to Database...");
            ConnectionL.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-label-padding: 5,10,5,10; -fx-background-radius: 5;-fx-background-color: #ee2525");
            connection = DriverManager.getConnection("jdbc:sqlite:OMEX.db");
            if (!connection.isClosed()) {
                ConnectionL.setText("Connection established");
                ConnectionL.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-label-padding: 5,10,5,10; -fx-background-radius: 5;-fx-background-color: #31f831");
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
    public void loadData() {
        ObservableList<F_Fragen> data = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:OMEX.sqlite")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM F_Fragen");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.getItems().addAll(data);
    }
    public void nächstefrage(ActionEvent actionEvent) {
        fragenauswähler();
    }

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void zurueckButtonAction(ActionEvent actionEvent) {
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
    }

    public void Checkantwort(ActionEvent actionEvent) {
        EntityManager em = Persistence.createEntityManagerFactory("Quiz").createEntityManager();
        Button clickedButton = (Button) actionEvent.getSource();
        String selectedAnswer = clickedButton.getText();
        String datenantwortzahl=String.valueOf( em.createQuery("select f.richtigeAntwort from F_Fragen f where f.id=:ausgewählteid", Integer.class).setParameter("ausgewählteid", frageid).getSingleResult());
        if(selectedAnswer==datenantwortzahl){
            clickedButton.setStyle("-fx-background-color: green");
        }else{
            clickedButton.setStyle("-fx-background-color: red");
        }

    }
}
