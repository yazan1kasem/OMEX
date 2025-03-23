//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.omex_quiz;

import entities.U_User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button showPasswordButton;
    @FXML
    private TextField passwordValueField;
    @FXML
    private Label usernamecheckl;
    EntityManager em = Persistence.createEntityManagerFactory("Quiz").createEntityManager();
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Hauptkategorie.fxml"));
    private String guestUsername;

    public LoginController() {

    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws SQLException, IOException {
        if (!this.Logincheck()) {
            this.usernamecheckl.setVisible(true);
        } else {
            this.usernamecheckl.setVisible(false);
            mainApp.loginSuccessful();
            String username = usernameField.getText(); // Benutzername aus dem Feld extrahieren
            levelController.setUser(username);
        }

            /*Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader fxmlLoaderParent = new FXMLLoader(getClass().getResource("Hauptkategorie.fxml"));
            Parent rootParent = fxmlLoaderParent.load();
            HauptkategorieController hauptkategorieController = fxmlLoaderParent.getController();
            Stage newStage = new Stage();
            newStage.setTitle("OMEX_QUIZ");
            newStage.setScene(new Scene(rootParent, 1350.0, 700.0));
            newStage.setMinWidth(1000.0);
            newStage.setMinHeight(700.0);
            newStage.show();*/

    }

    private boolean Logincheck() throws SQLException {
        boolean isLoginSuccessful = false;

        try {
            String username = this.usernameField.getText();
            List<String> result = this.em.createQuery("SELECT u.username FROM U_User u WHERE u.username = :username", String.class).setParameter("username", username).getResultList();
            boolean usernameExists = !result.isEmpty();
            if (usernameExists) {
                String password = (String)this.em.createQuery("SELECT u.password FROM U_User u WHERE u.username = :username", String.class).setParameter("username", username).getSingleResult();
                if (this.passwordField.isVisible() && password.equals(this.passwordField.getText()) || this.passwordValueField.isVisible() && password.equals(this.passwordValueField.getText())) {
                    isLoginSuccessful = true;
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return isLoginSuccessful;
    }

    @FXML
    private void handleShowPasswordButtonAction(ActionEvent event) {
        if (this.passwordField.isVisible()) {
            this.passwordField.setVisible(false);
            this.passwordValueField.setText(this.passwordField.getText());
            this.passwordValueField.setVisible(true);
            this.showPasswordButton.setText("Hide");
        } else {
            this.passwordField.setText(this.passwordValueField.getText());
            this.passwordField.setVisible(true);
            this.passwordValueField.setVisible(false);
            this.showPasswordButton.setText("Show");
        }

    }

    public void signuplink(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Sign-up.fxml"));

        Parent root;
        try {
            root = (Parent)loader.load();
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }

        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }

    public void gastclicked(MouseEvent mouseEvent) throws IOException {
        /*try {
            String guestUsername = "Guest_" + UUID.randomUUID();
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();
            Stage newStage = new Stage();
            newStage.setTitle("OMEX_QUIZ");
            Scene scene = new Scene(fxmlLoader.load(), 1350.0, 700.0);
            newStage.setScene(scene);
            newStage.setMinWidth(1000.0);
            newStage.setMinHeight(700.0);
            newStage.show();
            mainApp.loginSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String guestUsername = "Guest_" + UUID.randomUUID();
        Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();

        mainApp.loginSuccessful();
    }

    private void deleteGuestAccount() {
        EntityManager em = Persistence.createEntityManagerFactory("Quiz").createEntityManager();
        em.getTransaction().begin();
        U_User guestUser = (U_User)em.createQuery("SELECT u FROM U_User u WHERE u.username = :username", U_User.class).setParameter("username", this.guestUsername).getSingleResult();
        em.remove(guestUser);
        em.getTransaction().commit();
        em.close();
    }

    //------------------------------------------------------------------------------------------------
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    //------------------------------------------------------------------------------------------------
    private LevelController2 levelController = new LevelController2();

    public void setLevelController(LevelController2 levelController) {
        this.levelController = levelController;
    }

    public void setUser(String user) {
        levelController.setUser(user);
    }
}
