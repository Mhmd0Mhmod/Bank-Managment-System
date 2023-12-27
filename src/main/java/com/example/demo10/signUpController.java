package com.example.demo10;

import DataBase_Classes.InsertUser;
import DataBase_Classes.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Currency;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class signUpController {
    ObservableList<String> currencies = FXCollections.observableArrayList("USD", "EGP", "EUR", "KWD");
    @FXML
    private ComboBox<String> currencyComboBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ToggleGroup gender;
    @FXML
    private ToggleGroup terms;
    @FXML
    private Label termsLabel;

    @FXML
    private Label emailLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label currencyLabel;
    @FXML
    private Button registerButton;

    public void initialize() {
        SpecialTypeOfComboBox sp = new SpecialTypeOfComboBox();
        sp.initilaize(currencyComboBox);
    }

    public void signUpLabelAction(ActionEvent event) throws IOException {
        new LoadScene("login.fxml", ((Node) event.getSource()).getScene()).createScene();
    }

    public void registerButtonOnAction(ActionEvent event) throws SQLException, IOException {
        boolean valid = true;
        if (emailTextField.getText().isEmpty()) {
            emailLabel.setText("*Required");
            valid = false;
        } else emailLabel.setText("");
        if (usernameTextField.getText().isEmpty()) {
            usernameLabel.setText("*Required");
            valid = false;
        } else usernameLabel.setText("");
        if (passwordTextField.getText().isEmpty()) {
            passwordLabel.setText("*Required");
            valid = false;
        } else passwordLabel.setText("");
        if (currencyComboBox.getSelectionModel().getSelectedItem()==null) {
            currencyLabel.setText("*Required");
            valid = false;
        } else currencyLabel.setText("");
        RadioButton gen = (RadioButton) gender.getSelectedToggle();
        RadioButton term = (RadioButton) terms.getSelectedToggle();

        if (term == null)
            termsLabel.setText("You should accept our Terms first.");
        else
            termsLabel.setText("");
        if (!valid) return;
        termsLabel.setText("You have successfully register :)");
        InsertUser insertUser = new InsertUser(usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText()
                , currencyComboBox.getValue(), gen.getText());
        User user = insertUser.checkValidation();
        if (!user.equals(null)) {
            new LoadScene("login.fxml", ((Node) event.getSource()).getScene(), user).createScene();
        } else {
            termsLabel.setText("Change Your Username OR Email");
        }
    }

    public void backToLgoin(ActionEvent event) throws IOException {
        new LoadScene("login.fxml", ((Node) event.getSource()).getScene()).createScene();
    }

    @FXML
    private Button exitButton;

    @FXML
    public void handleMouseEnter() {
        exitButton.setStyle("-fx-background-color: RED;");
    }

    @FXML

    public void handleMouseExit() {
        exitButton.setStyle("-fx-background-color: transparent;"); // Reset to default or another color
    }

    @FXML

    public void cancelButtonOnAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}
