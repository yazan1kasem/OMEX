package com.example.omex_quiz;


import entities.F_Fragen;
import entities.HK_HauptKategorie;
import entities.UK_UnterKategorie;
import entities.U_User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javax.persistence.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


public class METABereich_Controller extends AnchorPane implements Initializable {
    private static Connection connection;
    //Field für Fragen
    @FXML
    private TextField searchBar;
    @FXML
    private TextField Frageid_Field;
    @FXML
    private TextArea Frage_Field;
    @FXML
    private ChoiceBox HauptKategorieChoice;
    @FXML
    private ChoiceBox UntereKategorieChoice;
    @FXML
    private TextArea erste_A_Field;
    @FXML
    private TextArea zweite_A_Field;
    @FXML
    private TextArea dritte_A_Field;
    @FXML
    private TextArea vierte_A_Field;
    @FXML
    private TextField RA_Field;
    @FXML
    private TextArea Erklärung_Field;
    //Field für user
    @FXML
    private TextField UidField;
    @FXML
    private TextField UnameField;
    @FXML
    private TextField UpasswortField;
    @FXML
    private TextField UScoreField;
    @FXML
    private TextField ULevelField;
    //Tables
    @FXML
    private TableView tableView;
    @FXML
    private Label ConnectionL;
    @FXML
    private TableView Usertable;
    @FXML
    private RadioButton questionsRadioButton;
    @FXML
    private RadioButton userRadioButton;
    @FXML
    private VBox UserFields;
    @FXML
    private VBox FragenFields;
    @FXML
    private ScrollPane scrollPane;
    public METABereich_Controller() {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //Methoden
            ConnectionMethod();
            choiceboxes();
            gotSelected();
            //----------------------------------------------------------------
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Quiz");
            EntityManager em = emf.createEntityManager();
            //----------------------------------------------------------------
            // Daten aus Datenbank abrufen und zur Tabelle hinzufügen
            TypedQuery<F_Fragen> query = em.createQuery("SELECT f FROM F_Fragen f", F_Fragen.class);
            List<F_Fragen> fragen = query.getResultList();
            ObservableList<F_Fragen> fragenListe = FXCollections.observableArrayList(fragen);
            tableView.setItems(fragenListe);
            List<U_User> users = em.createQuery("SELECT u FROM U_User u", U_User.class).getResultList();
            ObservableList<U_User> userListe = FXCollections.observableArrayList(users);
            Usertable.setItems(userListe);

        }catch (Exception e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("fehler");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.show();
        }


    }
    private void gotSelected() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Quiz");
        EntityManager em = emf.createEntityManager();
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<F_Fragen>() {
            @Override
            public void changed(ObservableValue<? extends F_Fragen> observable, F_Fragen oldValue, F_Fragen newValue) {
                if (newValue!= null) {
                    Frageid_Field.setText(String.valueOf(newValue.getId()));
                    String hk= em.createQuery("select concat(hk.id,' ', hk.bezeichnung) from HK_HauptKategorie hk where hk.id=: hkid", String.class).setParameter("hkid",newValue.getHauptkategorie().getId()).getSingleResult();
                    HauptKategorieChoice.setValue(hk);
                    String uk= em.createQuery("select concat(uk.id,' ', uk.bezeichnung) from UK_UnterKategorie uk where uk.id=: ukid", String.class).setParameter("ukid",newValue.getUnterkategorie().getId()).getSingleResult();
                    UntereKategorieChoice.setValue(uk);
                    Frage_Field.setText(newValue.getFrage());
                    erste_A_Field.setText(newValue.getAntwort1());
                    zweite_A_Field.setText(newValue.getAntwort2());
                    dritte_A_Field.setText(newValue.getAntwort3());
                    vierte_A_Field.setText(newValue.getAntwort4());
                    RA_Field.setText(String.valueOf(newValue.getRichtigeAntwort()));
                    Erklärung_Field.setText(newValue.getErklaeren());
                }

            }

        });
        Usertable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<U_User>() {
            @Override
            public void changed(ObservableValue<? extends U_User> observable, U_User oldValue, U_User newValue) {
                if (newValue!= null) {
                    UidField.setText(String.valueOf(newValue.getId()));
                    UnameField.setText(newValue.getUsername());
                    UpasswortField.setText(newValue.getPassword());
                    UScoreField.setText(String.valueOf(newValue.getScore()));
                    ULevelField.setText(String.valueOf(newValue.getLevel()));
                }

            }
        });
    }
    @FXML
    private void radiobuttonClicked(ActionEvent actionEvent) {
        if (actionEvent.getSource() == userRadioButton) {
            Usertable.setVisible(true);
            UserFields.setVisible(true);
            FragenFields.setVisible(false);
            scrollPane.setVisible(false);
        } else if (actionEvent.getSource() == questionsRadioButton) {
            FragenFields.setVisible(true);
            Usertable.setVisible(false);
            UserFields.setVisible(false);
            scrollPane.setVisible(true);
        }
        else{
            scrollPane.setVisible(false);
        }
    }
    public void insertButtonClicked(ActionEvent actionEvent) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Quiz");
        EntityManager em = emf.createEntityManager();
        if(questionsRadioButton.isSelected()) {
            F_Fragen fragen = new F_Fragen();
            int questionId = Integer.parseInt(Frageid_Field.getText());

            // Überprüfen, ob die ID bereits existiert
            F_Fragen existingQuestion = em.find(F_Fragen.class, questionId);
            if (existingQuestion != null) {
                // Die ID existiert bereits, geben Sie eine Meldung aus und beenden Sie den Vorgang
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Die angegebene ID existiert bereits.");
                alert.showAndWait();
                em.close();
                return;
            }
            fragen.setId(Integer.parseInt(Frageid_Field.getText()));
            int hauptkategorieId = Integer.parseInt(HauptKategorieChoice.getValue().toString().split(" ")[0]);
            HK_HauptKategorie hauptkategorie = em.find(HK_HauptKategorie.class, hauptkategorieId);
            fragen.setHauptkategorie(hauptkategorie);
            int unterkategorieId = Integer.parseInt(UntereKategorieChoice.getValue().toString().split(" ")[0]);
            UK_UnterKategorie unterkategorie = em.find(UK_UnterKategorie.class, unterkategorieId);
            fragen.setUnterkategorie(unterkategorie);
            fragen.setFrage(Frage_Field.getText());
            fragen.setAntwort1(erste_A_Field.getText());
            fragen.setAntwort2(zweite_A_Field.getText());
            fragen.setAntwort3(dritte_A_Field.getText());
            fragen.setAntwort4(vierte_A_Field.getText());
            fragen.setRichtigeAntwort(Integer.parseInt(RA_Field.getText()));
            fragen.setErklaeren(Erklärung_Field.getText());

            EntityTransaction transaction = null;
            try {
                transaction = em.getTransaction();
                transaction.begin();
                em.persist(fragen);
                tableView.getItems().add(fragen);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                    tableView.getItems().remove(fragen);
                }
                throw e;
            } finally {
                em.close();
                ConnectionMethod();
            }
            refresh();

        } else if (userRadioButton.isSelected()) {
            U_User user = new U_User();
            int UserId = Integer.parseInt(UidField.getText());

            // Überprüfen, ob die ID bereits existiert
            U_User existingQuestion = em.find(U_User.class, UserId);
            if (existingQuestion != null) {
                // Die ID existiert bereits, geben Sie eine Meldung aus und beenden Sie den Vorgang
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Die angegebene ID existiert bereits.");
                alert.showAndWait();
                em.close();
                return;
            }

            user.setId(Integer.parseInt(UidField.getText()));
            user.setUsername(UnameField.getText());
            user.setPassword(UpasswortField.getText());
            user.setScore(Integer.parseInt(UScoreField.getText()));
            String level = ULevelField.getText();
            if (level.equalsIgnoreCase("bronze") || level.equalsIgnoreCase("silber") || level.equalsIgnoreCase("gold")) {
                user.setLevel(level);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Die angegebene Level existiert nicht.\n Folgende Level sind verfügbar: \n1. Gold\n2. Silber\n3. Bronze");
                alert.showAndWait();
                em.close();
                return;
            }

            EntityTransaction transaction = null;
            try {
                transaction = em.getTransaction();
                transaction.begin();
                em.persist(user);
                Usertable.getItems().add(user);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                    Usertable.getItems().remove(user);
                }
                throw e;
            } finally {
                em.close();
                ConnectionMethod();
            }
            refresh();
        }

    }
    public void updateButtonClicked(ActionEvent actionEvent) {
        F_Fragen fragen = (F_Fragen) tableView.getSelectionModel().getSelectedItem();
        EntityManager entityManager = Persistence.createEntityManagerFactory("Quiz").createEntityManager();
        if(questionsRadioButton.isSelected()) {
            fragen.setId(Integer.parseInt(Frageid_Field.getText()));
            int hauptkategorieId = Integer.parseInt(HauptKategorieChoice.getValue().toString());
            HK_HauptKategorie hauptkategorie = entityManager.find(HK_HauptKategorie.class, hauptkategorieId);
            fragen.setHauptkategorie(hauptkategorie);
            int unterkategorieId = Integer.parseInt(UntereKategorieChoice.getValue().toString());
            UK_UnterKategorie unterkategorie = entityManager.find(UK_UnterKategorie.class, unterkategorieId);
            fragen.setUnterkategorie(unterkategorie);
            fragen.setFrage(Frage_Field.getText());
            fragen.setAntwort1(erste_A_Field.getText());
            fragen.setAntwort2(zweite_A_Field.getText());
            fragen.setAntwort3(dritte_A_Field.getText());
            fragen.setAntwort4(vierte_A_Field.getText());
            fragen.setRichtigeAntwort(Integer.parseInt(RA_Field.getText()));
            fragen.setErklaeren(Erklärung_Field.getText());

            EntityTransaction transaction = null;
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.merge(fragen);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            } finally {
                entityManager.close();
                ConnectionMethod();
            }
            refresh();
        }
        else if (userRadioButton.isSelected()) {
            U_User user = (U_User) Usertable.getSelectionModel().getSelectedItem();
            user.setId(Integer.parseInt(UidField.getText()));
            user.setUsername(UnameField.getText());
            user.setPassword(UpasswortField.getText());
            user.setScore(Integer.parseInt(UScoreField.getText()));

            String level = ULevelField.getText();
            if (level.equalsIgnoreCase("bronze") || level.equalsIgnoreCase("silber") || level.equalsIgnoreCase("gold")) {
                user.setLevel(level);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Der angegebene Level existiert nicht.\n Folgende Level sind verfügbar: \n1. Gold\n2. Silber\n3. Bronze");
                alert.showAndWait();
                entityManager.close();
                return;
            }

            EntityTransaction transaction = null;
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.merge(user);
                Usertable.getItems().remove(user);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            } finally {
                entityManager.close();
                ConnectionMethod();
            }
            refresh();
        }

    }
    public void deleteButtonClicked(ActionEvent actionEvent) {
        F_Fragen fragen = (F_Fragen) tableView.getSelectionModel().getSelectedItem();
        EntityManager entityManager = Persistence.createEntityManagerFactory("Quiz").createEntityManager();
        if(questionsRadioButton.isSelected()) {
            EntityTransaction transaction = null;
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                fragen = entityManager.merge(fragen);
                entityManager.remove(fragen);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            } finally {
                entityManager.close();
                ConnectionMethod();
            }
            tableView.getItems().remove(fragen);
            refresh();
        }
        else if (userRadioButton.isSelected()) {
            U_User user = (U_User) Usertable.getSelectionModel().getSelectedItem();
            EntityTransaction transaction = null;
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                user = entityManager.merge(user);
                entityManager.remove(user);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction!= null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            } finally {
                entityManager.close();
                ConnectionMethod();
            }
            Usertable.getItems().remove(user);
            refresh();
        }
    }
    public void Suchen(ActionEvent actionEvent) {
        String suchbegriff = searchBar.getText();
        EntityManager entityManager = Persistence.createEntityManagerFactory("Quiz").createEntityManager();

        if (!suchbegriff.isEmpty()) {
            if (questionsRadioButton.isSelected()) {
                String queryString = "SELECT f FROM F_Fragen f WHERE LOWER(f.frage) LIKE LOWER(:suchbegriff)";
                TypedQuery<F_Fragen> query = entityManager.createQuery(queryString, F_Fragen.class);
                query.setParameter("suchbegriff", "%" + suchbegriff + "%");
                List<F_Fragen> suchergebnisse = query.getResultList();

                tableView.getItems().clear();
                tableView.getItems().addAll(suchergebnisse);
            } else if (userRadioButton.isSelected()) {
                String queryString = "SELECT u FROM U_User u WHERE LOWER(u.username) LIKE LOWER(:suchbegriff)";
                TypedQuery<U_User> query = entityManager.createQuery(queryString, U_User.class);
                query.setParameter("suchbegriff", "%" + suchbegriff + "%");
                List<U_User> suchergebnisse = query.getResultList();

                Usertable.getItems().clear();
                Usertable.getItems().addAll(suchergebnisse);
            }
        } else {
            refresh();
        }
    }
    private void choiceboxes() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Quiz");
        EntityManager em = emf.createEntityManager();

        List<String> hauptKategorieQuery = em.createQuery("select concat(hk.id, ' ', hk.bezeichnung) from HK_HauptKategorie hk", String.class).getResultList();
        ObservableList<String> hauptKategorieList = FXCollections.observableArrayList(hauptKategorieQuery);
        HauptKategorieChoice.setItems(hauptKategorieList);

        List<String> unterKategorieQuery = em.createQuery("select concat(uk.id, ' ', uk.bezeichnung) from UK_UnterKategorie uk", String.class).getResultList();
        ObservableList<String> unterKategorieList = FXCollections.observableArrayList(unterKategorieQuery);
        UntereKategorieChoice.setItems(unterKategorieList);
        HauptKategorieChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null && oldValue!=null) {
                    UntereKategorieChoice.getSelectionModel().clearSelection();
                    List<String> filteredUnterKategorieQuery = em.createQuery("select concat(uk.id, ' ', uk.bezeichnung) from UK_UnterKategorie uk  where uk.HK_id.id = :hkkategorie", String.class)
                            .setParameter("hkkategorie", Integer.parseInt(newValue.split(" ")[0])) // Extrahiere die ID aus dem ausgewählten Wert
                            .getResultList();
                    unterKategorieList.setAll(filteredUnterKategorieQuery);
                }
                if(newValue != null && oldValue==null){
                    Object old= UntereKategorieChoice.getValue();
                    List<String> filteredUnterKategorieQuery = em.createQuery("select concat(uk.id, ' ', uk.bezeichnung) from UK_UnterKategorie uk  where uk.HK_id.id = :hkkategorie", String.class)
                            .setParameter("hkkategorie", Integer.parseInt(newValue.split(" ")[0])) // Extrahiere die ID aus dem ausgewählten Wert
                            .getResultList();
                    unterKategorieList.setAll(filteredUnterKategorieQuery);
                    UntereKategorieChoice.setValue(old);
                }
            }
        });
        UntereKategorieChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    try{
                        String HKitem = em.createQuery("select concat(hk.id, ' ', hk.bezeichnung) from HK_HauptKategorie hk where hk.id = (select uk.HK_id from UK_UnterKategorie uk where uk.id= :Ukkategorie) ", String.class)
                                .setParameter("Ukkategorie", Integer.parseInt(newValue.split(" ")[0])) // Extrahiere die ID aus dem ausgewählten Wert
                                .getSingleResult();
                        HauptKategorieChoice.setValue(HKitem);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                }
            }
        });


        // Abrufen aller Hauptkategorien
//        TypedQuery<HK_HauptKategorie> query = em.createQuery("SELECT hk FROM HK_HauptKategorie hk", HK_HauptKategorie.class);
//        List<HK_HauptKategorie> hauptKategorieList = query.getResultList();
//        ObservableList<HK_HauptKategorie> hauptKategorieObservableList = FXCollections.observableArrayList(hauptKategorieList);
//        /*for (HK_HauptKategorie hk_hauptKategorie : hauptKategorieList) {
//            int hauptkategorieId = hauptKategorieList.getHauptkategorie().getId();
//            System.out.println("Frage " + hauptKategorieList.getId() + " hat die Hauptkategorie-Id " + hauptkategorieId);
//        }*/
//        // Abrufen aller Unterkategorien
//        TypedQuery<UK_UnterKategorie> unterKategorieQuery = em.createQuery("SELECT uk FROM UK_UnterKategorie uk",UK_UnterKategorie.class);
//        List<UK_UnterKategorie> unterKategorieList = unterKategorieQuery.getResultList();
//        ObservableList<UK_UnterKategorie> unterKategorieObservableList = FXCollections.observableArrayList(unterKategorieList);
//
//        // HauptkategorieChoice-Elemente setzen
//        HauptKategorieChoice.setItems(hauptKategorieObservableList);
//
//        ChoiceBox h= new ChoiceBox();
//
//        //----------------------------------------------------------------
//
//        // UnterkategorieChoice-Listener hinzufügen, um Hauptkategorie automatisch auszuwählen und Unterkategorien zu aktualisieren, wenn eine Unterkategorie ausgewählt wird
//
//        HauptKategorieChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HK_HauptKategorie>() {
//            @Override
//            public void changed(ObservableValue<? extends HK_HauptKategorie> observable, HK_HauptKategorie oldValue, HK_HauptKategorie newValue) {
//                // Überprüfen, ob eine Hauptkategorie ausgewählt wurde
//                if (newValue != null) {
//                    // Wenn eine Hauptkategorie ausgewählt wurde, dann nur die zugehörigen Unterkategorien abrufen
//                    TypedQuery<UK_UnterKategorie> unterKategorieQuery = em.createQuery("SELECT uk FROM UK_UnterKategorie uk WHERE uk.HK_id = :ausgewaehlteHauptKategorie", UK_UnterKategorie.class);
//                    unterKategorieQuery.setParameter("ausgewaehlteHauptKategorie", newValue);
//                    List<UK_UnterKategorie> unterKategorieList = unterKategorieQuery.getResultList();
//                    ObservableList<UK_UnterKategorie> unterKategorieObservableList = FXCollections.observableArrayList(unterKategorieList);
//                    h.setValue(UntereKategorieChoice.getValue());
//                    UntereKategorieChoice.setItems(unterKategorieObservableList);
//                    UntereKategorieChoice.getSelectionModel().clearSelection();
//
//                } else {
//                    // wenn keine Hauptkategorie ausgewählt wurde, dann alle Unterkategorien abrufen
//                    TypedQuery<UK_UnterKategorie> alleUnterKategorieQuery = em.createQuery("SELECT uk FROM UK_UnterKategorie uk", UK_UnterKategorie.class);
//                    List<UK_UnterKategorie> alleUnterKategorieList = alleUnterKategorieQuery.getResultList();
//                    ObservableList<UK_UnterKategorie> alleUnterKategorieObservableList = FXCollections.observableArrayList(alleUnterKategorieList);
//
//                    //  UnterkategorieChoice-Elemente setzen
//                    String h= UntereKategorieChoice.getValue().toString();
//                    UntereKategorieChoice.setItems(alleUnterKategorieObservableList);
//                    UntereKategorieChoice.getSelectionModel().select(h);
//                }
//            }
//        });


    }
    public void refresh(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Quiz");
        EntityManager em = emf.createEntityManager();
        //----------------------------------------------------------------
        // Daten aus Datenbank abrufen und zur Tabelle hinzufügen
        ObservableList<U_User> userListe=null;
        ObservableList<F_Fragen> fragenListe=null;

        if (questionsRadioButton.isSelected()) {
            try {
                TypedQuery<F_Fragen> query = em.createQuery("SELECT f FROM F_Fragen f", F_Fragen.class);
                List<F_Fragen> fragen = query.getResultList();
                fragenListe= FXCollections.observableArrayList(fragen);

            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                tableView.setItems(fragenListe);
                tableView.refresh();
            }


        } else if (userRadioButton.isSelected()) {
            try {
                List<U_User> users = em.createQuery("SELECT u FROM U_User u", U_User.class).getResultList();
                userListe= FXCollections.observableArrayList(users);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                Usertable.setItems(userListe);
                Usertable.refresh();
            }

        }
    }
    public void ConnectionMethod() {
        try {
            if (connection != null)
                return;
            ConnectionL.setText("Creating Connection to Database...");
            ConnectionL.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-label-padding: 5,10,5,10; -fx-background-radius: 5;-fx-background-color: #ee2525");connection = DriverManager.getConnection("jdbc:sqlite:OMEX.db");
            if (!connection.isClosed()){
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

}
