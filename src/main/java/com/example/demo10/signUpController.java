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
    private Button registerButton;


    public void initialize() {


        // Create a list with some dummy values.
        currencyComboBox.setEditable(false);

        // Retrieve all available currencies
        Set<Currency> currencies = Currency.getAvailableCurrencies();

        //Using map to get the currency code not the whole object and using collect to insert them in the set
        // using Treeset to sort them in alphabetically order
        SortedSet<String> sortedCurrencyCodes = new TreeSet<>(
                currencies.stream()
                        .map(Currency::getCurrencyCode)
                        .collect(Collectors.toSet())
        );

        // Create an ObservableList from the sorted set of currency codes
        // creating observable to be helpful when u add and remove something
        ObservableList<String> currencyItems = FXCollections.observableArrayList(sortedCurrencyCodes);


        // FilteredList to provide a way to filter which items are visible
        FilteredList<String> filteredItems = new FilteredList<>(currencyItems, p -> true);

        // Set the ComboBox items to the FilteredList to change upon any change happens in the FilteredList
        currencyComboBox.setItems(filteredItems);

        // StringBuilder to hold the typed characters
        StringBuilder searchQuery = new StringBuilder();

        // Add an event handler to handle key presses
        currencyComboBox.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            String character = event.getText();
            if (!character.isEmpty()) { // Check if a character was typed (Shift and ctrl and empty for example)
                // Append the character to the search query
                searchQuery.append(character);
                currencyComboBox.setPromptText(String.valueOf(searchQuery));
                // Refilter the list based on the search query by choosing currencies starts with searchQuery and it's case-insensitive
                filteredItems.setPredicate(item -> item.toUpperCase().startsWith(searchQuery.toString().toUpperCase()));
            } else if (event.getCode() == KeyCode.BACK_SPACE && searchQuery.length() > 0) {
                // Handle backspace
                searchQuery.deleteCharAt(searchQuery.length() - 1);
                if (searchQuery.isEmpty()) currencyComboBox.setPromptText("Choose currency");
                else currencyComboBox.setPromptText(String.valueOf(searchQuery));
                filteredItems.setPredicate(item -> item.toUpperCase().startsWith(searchQuery.toString().toUpperCase()));
            }
            // Open the ComboBox popup to show the filtered items
            if (!currencyComboBox.isShowing()) {
                currencyComboBox.show();
            }
        });
    }
    public void signUpLabelAction(ActionEvent event) throws IOException {
        new LoadScene("login.fxml", ((Node) event.getSource()).getScene()).createScene();
    }

    public void registerButtonOnAction(ActionEvent event) throws SQLException, IOException {
        RadioButton gen = (RadioButton) gender.getSelectedToggle();
        RadioButton term = (RadioButton) terms.getSelectedToggle();
        if (term == null)
            termsLabel.setText("You should accept our Terms first.");
        else {
            termsLabel.setText("You have successfully register :)");
            InsertUser insertUser = new InsertUser(usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText()
                    , currencyComboBox.getValue(), gen.getText());
            User user = insertUser.checkValidation();
            if(!user.equals(null)){
            new LoadScene("dashboard.fxml", ((Node) event.getSource()).getScene(), user).createScene();
            }else {
                System.out.println("NULL");
            }
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
