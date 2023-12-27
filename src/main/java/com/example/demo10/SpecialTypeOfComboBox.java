package com.example.demo10;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.Currency;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SpecialTypeOfComboBox {

    public void initilaize(ComboBox<String> currencyComboBox) {


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
}
