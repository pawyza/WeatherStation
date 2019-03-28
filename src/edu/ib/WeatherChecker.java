package edu.ib;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *Metoda odpowiadajaca za kontrolowanie drugiego watka
 */
public class WeatherChecker implements Runnable, Observable {

    private WeatherStation weatherStation;

    private volatile ArrayList<Observer> observers = new ArrayList<>();

    private Thread timerThread;
    protected volatile boolean isRunning = false;
    private int interval = 10000;
    private boolean isPaused;

    /**
     * Metoda sprawdzajca czy podano poprawna nazwe lub kod miasta oraz zwracajaca jego nazwe poprawnie zapisana.
     * @param cityName podana nazwa lub kod miasta
     * @return pelna nazwa miasta dla ktorego pobierane sa wartosci
     * @throws IllegalArgumentException blad generowany w momencie blednego podania miasta lub braku polaczenia
     */
    public String prepareConnection(String cityName) throws IllegalArgumentException{
        String properName;
        try{
            weatherStation = new WeatherStation();
            weatherStation.setCity(cityName);
            properName = weatherStation.checkAndSetURL();
        } catch (IllegalArgumentException e){
            System.out.println("Nie znaleziono podanego miasta lub brak połączenia");
            throw new IllegalArgumentException();
        }
        return properName;
    }

    /**
     * Metoda tworzaca oraz startujaca nowy watek.
     */
    public void start(){
            isPaused = false;
            timerThread = new Thread(this, "Timer thread");
            timerThread.start();
    }

    /**
     * Metoda usuwajaca obserwatora oraz zatrzymująca watek
     */
    public void stop(Observer observer) {
        isRunning = false;
        removeObserver(observer);
    }

    /**
     * Metda czasowo wstrzymujaca watek
     */
    synchronized void pause() {
        isPaused = true;
    }

    /**
     * Metoda wznawiająca wątek.
     */
    synchronized void unpause() {
        isPaused = false;
        notify();
    }

    /**
     * Metoda w ktorej wątek utworzony w obiekcie klasy WeatherChecker wywoluje metode updateObservers co upływ czasu okreslony przez interval
     */
    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {

            try {
                updateObservers();
                Thread.sleep(interval);
                synchronized (this) {
                    while (isPaused) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Failed to complete operation");
            }

        }
    }

    /**
     * Metoda pobierajaca liste tablicowa aktualnych warunkow z serwera wykorzystujac metody checkConditions() oraz getConditions() obiektu klasy WeatherStation
     * @return warunki pogodowe pobrane z serwera
     */
    private ArrayList<String> checkActualWeather(){
        weatherStation.checkConditions();
        return weatherStation.getConditionList();
    }

    /**
     * Metoda dodajaca obserwatora do listy obserwatorow
     */
    @Override
    public void addObserver(Observer observer) {
        if(!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Metoda usuwajaca obserwatora z listy obserwatorow
     */
    @Override
    public void removeObserver(Observer observer) {
        if(observers.contains(observer))
            observers.remove(observer);
    }

    /**
     * Metoda wywolujaca metode updateConditions(ArrayList<String> conditionsList, LocalTime time) obserwatorow w znajdujacych sie w liscie obserwatorow
     */
    @Override
    public void updateObservers() {
        for(Observer o:observers) {
            o.updateConditions(checkActualWeather(),LocalTime.now());
        }
    }
}
