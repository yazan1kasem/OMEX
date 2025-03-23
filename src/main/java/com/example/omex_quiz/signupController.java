package com.example.omex_quiz;

import entities.U_User;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class signupController {
    @FXML
    private TextField passwordValueField;
    @FXML
    private TextField passwordbestaetigungValueLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordbestaetigungField;
    @FXML
    private Button showPasswordbestaetigungButton;
    @FXML
    private Button showPasswordButton;
    @FXML
    private Label passwortcheckl;
    @FXML
    private Label benutzernamecheckl;
    @FXML
    private Label passwordValuecheckl;
    @FXML
    private Label passwordbestaetigungValuecheckl;
    @FXML
    private TextField Benutzername;
    @FXML
    private Label usernamecheckl;




    EntityManager em= Persistence.createEntityManagerFactory("Quiz").createEntityManager();
    public void loginlink(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }

    public void show(ActionEvent actionEvent) {
        if (actionEvent.getSource() == showPasswordButton) {
            if (passwordField.isVisible()) {
                passwordField.setVisible(false);
                passwordValueField.setVisible(true);
                passwordValueField.requestFocus();
                passwordValueField.setText(passwordField.getText());
                // Schätzen der Cursor-Position basierend auf der Textlänge
                passwordValueField.positionCaret(passwordValueField.getText().length());
            } else {
                passwordField.setVisible(true);
                passwordValueField.setVisible(false);
                passwordField.requestFocus();
                passwordField.setText(passwordValueField.getText());
                // Schätzen der Cursor-Position basierend auf der Textlänge
                passwordField.positionCaret(passwordValueField.getText().length());
            }
        } else if (actionEvent.getSource() == showPasswordbestaetigungButton) {
            if (passwordbestaetigungField.isVisible()) {
                passwordbestaetigungField.setVisible(false);
                passwordbestaetigungValueLabel.setVisible(true);
                passwordbestaetigungValueLabel.requestFocus();
                passwordbestaetigungValueLabel.setText(passwordbestaetigungField.getText());
                passwordbestaetigungField.positionCaret(passwordbestaetigungValueLabel.getText().length());

            } else {
                passwordbestaetigungField.setVisible(true);
                passwordbestaetigungValueLabel.setVisible(false);
                passwordbestaetigungField.requestFocus();
                passwordbestaetigungField.setText(passwordbestaetigungValueLabel.getText());
                passwordbestaetigungValueLabel.positionCaret(passwordbestaetigungField.getText().length());
            }
        }
    }





    public void Signingup(ActionEvent actionEvent) throws SQLException {
        if(!usernamecheck()&&ifs()){
            TypedQuery<Integer> query = em.createQuery("SELECT MAX(u.id) FROM U_User u", Integer.class);
            Integer lastId = query.getSingleResult()!=null? Integer.parseInt(query.getSingleResult().toString()):0;
            U_User u = new U_User();
            u.setId(lastId+1);
            u.setUsername(Benutzername.getText());
            if(passwordValueField.isVisible()){
                u.setPassword(passwordValueField.getText());
            } else if (passwordField.isVisible()) {
                u.setPassword(passwordField.getText());
            }
            u.setLevel("bronze");
            u.setScore(0);
            EntityTransaction transaction = null;
            try {
                transaction = em.getTransaction();
                transaction.begin();
                em.persist(u);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction!= null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            } finally {
                em.close();
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    private boolean usernamecheck() throws SQLException {
        boolean usernameExists = false;
        try {
            // Abfrage ausführen
            List<String> result = em.createQuery("SELECT u.username FROM U_User u WHERE u.username = :username", String.class)
                    .setParameter("username", Benutzername.getText())
                    .getResultList();

            if(!result.isEmpty()) {
                usernameExists = true;
                usernamecheckl.setVisible(true);
            }else{
                usernamecheckl.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Fehlerbehandlung
        }
        return usernameExists;
    }
    private boolean ifs() {
        boolean result = true;
        if (!Benutzername.getText().isEmpty()) {
            benutzernamecheckl.setVisible(false);
        } else {
            benutzernamecheckl.setVisible(true);
            result = false;
        }
        if ((passwordbestaetigungField.isVisible() && passwordbestaetigungField.getText().isEmpty()) || (!passwordbestaetigungField.isVisible() && passwordbestaetigungValueLabel.getText().isEmpty())) {
            passwordbestaetigungValuecheckl.setVisible(true);
            passwortcheckl.setVisible(false);
            result = false;
        } else {
            passwordbestaetigungValuecheckl.setVisible(false);
        }
        if ((passwordField.isVisible() && passwordField.getText().isEmpty()) || (!passwordField.isVisible() && passwordValueField.getText().isEmpty())) {
            passwordValuecheckl.setVisible(true);
            passwortcheckl.setVisible(false);
            result = false;
        } else {
            passwordValuecheckl.setVisible(false);
        }
        if (passwordbestaetigungField.isVisible() && passwordField.isVisible()) {
            if (!passwordbestaetigungField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                if (passwordbestaetigungField.getText().equals(passwordField.getText())) {
                    passwortcheckl.setVisible(false);
                } else {
                    passwortcheckl.setVisible(true);
                    result = false;
                }
            }
        } else if (passwordbestaetigungField.isVisible() && !passwordField.isVisible()) {
            if (!passwordbestaetigungField.getText().isEmpty() && !passwordValueField.getText().isEmpty()) {
                if (passwordbestaetigungField.getText().equals(passwordValueField.getText())) {
                    passwortcheckl.setVisible(false);
                } else {
                    passwortcheckl.setVisible(true);
                    result = false;
                }
            }
        } else if (!passwordbestaetigungField.isVisible() && passwordField.isVisible()) {
            if (!passwordbestaetigungValueLabel.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                if (passwordbestaetigungValueLabel.getText().equals(passwordField.getText())) {
                    passwortcheckl.setVisible(false);
                } else {
                    passwortcheckl.setVisible(true);
                    result = false;
                }
            }
        } else if (!passwordbestaetigungField.isVisible() && !passwordField.isVisible()) {
            if (!passwordbestaetigungValueLabel.getText().isEmpty() && !passwordValueField.getText().isEmpty()) {
                if (passwordbestaetigungValueLabel.getText().equals(passwordValueField.getText())) {
                    passwortcheckl.setVisible(false);
                } else {
                    passwortcheckl.setVisible(true);
                    result = false;
                }
            }
        }
        return result;
    }

}
