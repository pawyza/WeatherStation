package edu.ib;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 *Metoda przechowujaca wszystkie zebrane odczyty
 */
public class DataStorage {
    private int dataStorageSize;
    private String cityName;
    private ArrayList<LocalTime> checkTime;
    private ArrayList<String> weatherType;
    private ArrayList<String> weatherDescription;
    private ArrayList<Double> temperature;
    private ArrayList<Double> pressure;
    private ArrayList<Double> humidity;
    private ArrayList<Double> minTemperature;
    private ArrayList<Double> maxTemperature;
    private ArrayList<Double> windSpeed;
    private ArrayList<Double> windDegree;

    /**
     * Konstruktor klasy DataStorage
     */
    public DataStorage(){
        dataStorageSize=0;
        cityName = null;
        this.checkTime = new ArrayList<>();
        this.weatherType = new ArrayList<>();
        this.weatherDescription = new ArrayList<>();
        this.temperature = new ArrayList<>();
        this.pressure = new ArrayList<>();
        this.humidity = new ArrayList<>();
        this.minTemperature = new ArrayList<>();
        this.maxTemperature = new ArrayList<>();
        this.windSpeed = new ArrayList<>();
        this.windDegree = new ArrayList<>();
    }
    //checkTime methods

    /**
     * Getter zwracajacy listę tablicową checkTime
     */
    public ArrayList<LocalTime> getCheckTime() {
        return checkTime;
    }

    /**
     * Getter zwracajacy element listy tablicowej checkTime o numerze index
     */
    public LocalTime getCheckTimeAt(int index) {
        try{
            return checkTime.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return LocalTime.of(0,0,0);
        }
    }

    /**
     * Getter zwracajacy element listy tablicowej checkTime o numerze index jako obiekt typu String
     */
    public String getCheckTimeAtAsString(int index) {
        try{
            return checkTime.get(index).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return LocalTime.of(0,0,0).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa checkTime
     */
    public void setCheckTime(ArrayList<LocalTime> checkTime) {
        if(temperature.size() == dataStorageSize)
            this.checkTime = checkTime;
        else
            System.out.println("Niepoprawny rozmiar listy tablicowej");
    }

    //weatherType methods

    /**
     * Getter zwracajacy liste tablicowa weatherType
     */
    public ArrayList<String> getWeatherType() {
        return weatherType;
    }

    /**
     * Getter zwracajacy element listy tablicowej weatherType o numerze index
     */
    public String getWeatherTypeAt(int index) {
        try{
            return weatherType.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return null;
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa weatherType
     */
    public void setWeatherType(ArrayList<String> weatherType) {
        if(temperature.size() == dataStorageSize)
            this.weatherType = weatherType;
        else
            System.out.println("Niepoprawny rozmiar listy tablicowej");
    }
    //weatherDescription methods

    /**
     * Getter zwracajacy liste tablicowa weatherDescription
     */
    public ArrayList<String> getWeatherDescription() {
        return weatherDescription;
    }

    /**
     * Getter zwracajacy element listy tablicowej weatherDescription o numerze index
     */
    public String getWeatherDescriptionAt(int index) {
        try{
            return weatherDescription.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return null;
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa weatherDescription
     */
    public void setWeatherDescription(ArrayList<String> weatherDescription) {
        if(temperature.size() == dataStorageSize)
            this.weatherDescription = weatherDescription;
        else
            System.out.println("Niepoprawny rozmiar listy tablicowej");
    }

    //Temperature methods

    /**
     * Getter zwracajacy liste tablicowa temperature
     */
    public ArrayList<Double> getTemperature() {
        return temperature;
    }

    /**
     * Getter zwracajacy element listy tablicowej temperature o numerze index
     */
    public double getTemperatureAt(int index) {
        try{
            return temperature.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return 0;
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa temperature
     */
    public void setTemperature(ArrayList<Double> temperature) {
        if(temperature.size() == dataStorageSize)
            this.temperature = temperature;
        else
            System.out.println("Niepoprawny rozmiar listy tablicowej");
    }


    //Pressure methods

    /**
     * Getter zwracajacy listę tablicowa pressure
     */
    public ArrayList<Double> getPressure() {
        return pressure;
    }

    /**
     * Getter zwracajacy element listy tablicowej pressure o numerze index
     */
    public double getPressureAt(int index) {
        try{
            return pressure.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return 0;
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa pressure
     */
    public void setPressure(ArrayList<Double> pressure) {
        if(temperature.size() == dataStorageSize)
            this.pressure = pressure;
        else
            System.out.println("Niepoprawny rozmiar listy tablicowej");
    }
    //Humidity methods

    /**
     * Getter zwracajacy lista tablicowa Humidity
     */
    public ArrayList<Double> getHumidity() {
        return humidity;
    }

    /**
     * Getter zwracajacy element listy tablicowej humidity o numerze index
     */
    public double getHumidityAt(int index) {
        try{
            return humidity.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return 0;
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa humidity
     */
    public void setHumidity(ArrayList<Double> humidity) {
        if(temperature.size() == dataStorageSize)
            this.humidity = humidity;
        else
            System.out.println("Niepoprawny rozmiar listy tablicowej");
    }

    //minTemperature methods

    /**
     * Getter zwracajacy liste tablicowa minTemperature
     */
    public ArrayList<Double> getMinTemperature() {
        return minTemperature;
    }

    /**
     * Getter zwracajacy element listy tablicowej minTemperature o numerze index
     */
    public double getMinTemperatureAt(int index) {
        try{
            return minTemperature.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return 0;
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa minTemperature
     */
    public void setMinTemperature(ArrayList<Double> minTemperature) {
        if(temperature.size() == dataStorageSize)
            this.minTemperature = minTemperature;
        else
            System.out.println("Niepoprawny rozmiar listy tablicowej");
    }

    //maxTemperature methods

    /**
     * Getter zwracajacy liste tablicowa maxTemperature
     */
    public ArrayList<Double> getMaxTemperature() {
        return maxTemperature;
    }

    /**
     * Getter zwracajacy element listy tablicowej maxTemperature o numerze index
     */
    public double getMaxTemperatureAt(int index) {
        try{
            return maxTemperature.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return 0;
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa maxTemperature
     */
    public void setMaxTemperature(ArrayList<Double> maxTemperature) {
        if(temperature.size() == dataStorageSize)
            this.maxTemperature = maxTemperature;
        else
            System.out.println("Niepoprawny rozmiar listy tablicowej");
    }
    //windSpeed methods

    /**
     * Getter zwracajacy liste tablicowa windSpeed
     */
    public ArrayList<Double> getWindSpeed() {
        return windSpeed;
    }

    /**
     * Getter zwracajacy element listy tablicowej windSpeed o numerze index
     */
    public double getWindSpeedAt(int index) {
        try{
            return windSpeed.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return 0;
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa windSpeed
     */
    public void setWindSpeed(ArrayList<Double> windSpeed) {
        if(temperature.size() == dataStorageSize)
            this.windSpeed = windSpeed;
        else
            System.out.println("Niepoprawny rozmiar listy tablicowej");
    }
    //windDegree methods

    /**
     * Getter zwracajacy listę tablicowa windDegree
     */
    public ArrayList<Double> getWindDegree() {
        return windDegree;
    }

    /**
     * Getter zwracajacy element listy tablicowej windDegree o numerze index
     */
    public double getWindDegreeAt(int index) {
        try{
            return windDegree.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Podano nieprawidłowy index tablicy");
            return 0;
        }
    }

    /**
     * Setter ustawiajacy liste tablicowa windDegree
     */
    public void setWindDegree(ArrayList<Double> windDegree) {
        if(temperature.size() == dataStorageSize)
            this.windDegree = windDegree;
        else
        System.out.println("Niepoprawny rozmiar listy tablicowej");
    }

    /**
     * Getter zmiennej cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Setter zmiennej cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    //ogolne

    /**
     * Metoda dodajaca do list tablicowych nowy odczyt
     */
    public void addRead(LocalTime checkTime, ArrayList<String> read) {
        dataStorageSize++;
        this.checkTime.add(checkTime);
        this.weatherType.add(read.get(0));
        this.weatherDescription.add(read.get(1));
        this.temperature.add(Double.parseDouble(read.get(2)));
        this.pressure.add(Double.parseDouble(read.get(3)));
        this.humidity.add(Double.parseDouble(read.get(4)));
        this.minTemperature.add(Double.parseDouble(read.get(5)));
        this.maxTemperature.add(Double.parseDouble(read.get(6)));
        this.windSpeed.add(Double.parseDouble(read.get(7)));
        this.windDegree.add(Double.parseDouble(read.get(8)));
    }

    /**
     * Getter zmiennej dataStorageSize
     */
    public int getDataStorageSize(){
        return dataStorageSize;
    }

    /**
     * Metoda zwracajaca najmniejsza wartosc w liscie tablicowej
     */
    public double getMinimum(ArrayList<Double> list){
        double value = Collections.min(list);
        return value;
    }

    /**
     * Metoda zwracajaca najwieksza wartosc w liscie tablicowej
     */
    public double getMaximum(ArrayList<Double> list){
        double value = Collections.max(list);
        return value;
    }

    /**
     * Metoda zwracajaca srednia wartosc z listy tablicowej
     */
    public double getMean(ArrayList<Double> list){
        double value = 0;
        for (Double x:list) {
        value += x;}
        value = value/list.size();
        return value;
    }

    /**
     * Metoda zwracajaca odchylenie standardowe z listy tablicowej
     */
    public double getStandardDeviation(ArrayList<Double> list){
        double value = 0;
        double mean = getMean(list);
        for (Double x:list) {
            value += Math.pow((x-mean),2);}
        value = Math.sqrt(value/(list.size()-1));
        return value;
    }

    /**
     * Metoda zwracajaca odczyt o numerze index w postaci zmiennej typu String
     * @param index index dla ktorego nalezy pobrac warunki pogodowe
     * @return warunki pogodowe dla wskazanego indexu
     */
    public String printReadAt(int index) {
        StringBuilder print = new StringBuilder();
        print.append("City name: " + cityName + " Time: " + checkTime.get(index).format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\nWeather type: " + weatherType.get(index) + "\nWeather description: " + weatherDescription.get(index) +
                "\nTemperature: " + temperature.get(index) + " \u2103     Pressure: " + pressure.get(index) + " hPa      Humidity " + humidity.get(index) + " %       Temperature range: " + minTemperature.get(index) + " - " + maxTemperature.get(index) +
                " \u2103\nWind speed: " + windSpeed.get(index) + " m/s      Wind degree: " + windDegree.get(index) + " \u00b0");
        return print.toString();
    }

    /**
     * Metoda zwracajaca wszystkie odczyty z obiektu klasy DataStorage
     */
    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();
        int index=-1;
        for (LocalTime time:checkTime) {
            index++;
            print.append("City name: " + cityName + "Time: " + time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\nWeather type: " + weatherType.get(index) + "\nWeather description: " + weatherDescription.get(index) +
                    "\nTemperature: " + temperature.get(index) + " \u2103     Pressure: " + pressure.get(index) + " hPa      Humidity " + humidity.get(index) + " %       Temperature range: " + minTemperature.get(index) + " - " + maxTemperature.get(index) +
                    " \u2103\nWind speed: " + windSpeed.get(index) + " m/s      Wind degree: " + windDegree.get(index) + " \u00b0");
        }
        return print.toString();
    }
}
