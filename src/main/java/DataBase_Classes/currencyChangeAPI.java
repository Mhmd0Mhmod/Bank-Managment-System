package DataBase_Classes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class currencyChangeAPI {
    public  double convertCurrency(String fromCurrency, String toCurrency, double amount) throws IOException {
        String url_str = "https://v6.exchangerate-api.com/v6/15fb894408079402d66292c9/latest/"+ fromCurrency;

// Making Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

// Convert to JSON
        JsonParser jp = new JsonParser(); //
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        // object
        System.out.println(jsonobj);

        // Get the conversion rates object
        JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");
        System.out.println(conversionRates);

        // Extract the specific conversion rate
        double conversionRate = conversionRates.get(toCurrency).getAsDouble();

        // Calculate the converted amount
        double convertedAmount = amount * conversionRate;
        return convertedAmount;
    }
}
