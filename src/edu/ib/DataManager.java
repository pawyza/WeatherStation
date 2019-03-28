package edu.ib;

import com.google.gson.*;

import java.io.*;

public class DataManager {


    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    /**
     * Metoda zwracajaca obiekt klasy DataStorage utworzony na podstawie odczytanego pliku file
     * @param file plik z ktorego dane zostaja odczytane
     * @return obiekt klasy DataStorage przechowujacy odczytane dane
     */
    public DataStorage load(File file) throws IOException,RuntimeException {
        DataStorage dataStorage;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            dataStorage = gson.fromJson(bufferedReader, DataStorage.class);
            if(dataStorage.getDataStorageSize() < 1){
                throw new NullPointerException();
            }
        } catch (IOException e) {
            throw new IOException();
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
        return dataStorage;
    }

    /**
     * Metoda zapisujaca do pliku .txt w postaci kodu JSON wartosci znajdujace sie w obiekcie klasy DataStorage
     * @param path sciezka w ktorej nalezy zapisac dane
     * @param dataStorage obiekt przechowujacy listy tablicowe zawierajace zapisywane dane
     * @throws IOException blad generowany w momencie bledu napotkanego podczas proby zapisu
     */
    public void save(String path,DataStorage dataStorage) throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(path))) {
            gson.toJson(dataStorage, fileWriter);
        }   catch (IOException e) {
            throw new IOException();
        }
    }
}
