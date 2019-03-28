package edu.ib;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 *Metoda odpowiadajaca za laczenie z serwerem
 */
public class WeatherStation {

        private boolean state;
        private String url;
        private ArrayList<String> conditionList;
        URL obj;
        HttpURLConnection connection;


    /**
     * Metoda ustawiajaca URL przy pomocy podanego nazwy lub kodu miasta String city
     * @param city kod lub nazwa miasta
     */
        public void setCity(String city) {
            url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&main&APPID=6b12fccf84d979361416710c5104be41";
        }

    /**
     * Metoda sprawdzajaca poprawnosc ustawionego URL oraz zwracajaca poprawna nazwę miasta odczytana z pobranego obiektu JSON
     * @return poprawna nazwa miasta dla podanego url
     * @throws IllegalArgumentException blad generowany w momencie podania nieprawidlowego kodu lub nazwy miasta
     */
        public String checkAndSetURL() throws IllegalArgumentException{
            JsonParser parser = new JsonParser();
            String properName = "";
            System.out.println("Sprawdzam połączenie.");
           try{
                obj = new URL(url);
                connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
               while ((inputLine = bufferedReader.readLine()) != null) {
                   JsonObject readObject = parser.parse(inputLine).getAsJsonObject();
                   properName = readObject.get("name").getAsString();
               }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                System.out.println("bad url");
                throw new IllegalArgumentException();
            } catch (IOException e) {
               System.out.println("Connection failed");
               throw new IllegalArgumentException();
            }
            return properName;
        }

    /**
     * Metoda łaczaca sie z serwerem oraz pobierajaca z niego wartosci, ktore sa zapisywane pod postacia listy tablicowej conditionList obiektow typu String
     */
        public void checkConditions(){
        JsonParser parser = new JsonParser();
        conditionList = new ArrayList<>();

        try {
            connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response: " + responseCode);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                JsonObject readObject = parser.parse(inputLine).getAsJsonObject();
                JsonObject weather = readObject.getAsJsonArray("weather").get(0).getAsJsonObject();
                conditionList.add(weather.get("main").getAsString());
                conditionList.add(weather.get("description").getAsString());
                JsonObject main = readObject.getAsJsonObject("main");
                for (Map.Entry<String,JsonElement> values : main.entrySet()){
                    conditionList.add(values.getValue().getAsString());
                }
                JsonObject wind = readObject.getAsJsonObject("wind");
                for (Map.Entry<String,JsonElement> values : wind.entrySet()){
                    conditionList.add(values.getValue().getAsString());
                }
            }

            bufferedReader.close();
        } catch (MalformedURLException e) {
            System.out.println("bad url");
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
    }

    /**
     * Metoda pobierajaca liste tablicowa conditionList
     * @return warunki pogodowe w postaci listy tablicowej
     */
    public ArrayList<String> getConditionList(){
            return conditionList;
    }
}
