package edu.ib;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Klasa zarzadzajaca GUI
 */
public class Controller implements Observer {

    @FXML
    private TextArea txtText;

    @FXML
    private TextField txtCity;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnStop;

    @FXML
    private Button btnClear;

    @FXML
    private Label txtReadCountNumber;

    @FXML
    private Label txtDisplayedCity;

    @FXML
    private Label txtStartTime;

    @FXML
    private Label txtLastTime;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnLoad;

    @FXML
    private TextField txtPath;

    @FXML
    private TableView<WindData> tabWindStatistics;

    @FXML
    private TableColumn<WindData,ArrayList<String>> colTime;

    @FXML
    private TableColumn<WindData,ArrayList<Double>> colWindDirection;

    @FXML
    private TableColumn<WindData,ArrayList<Double>> colWindSpeed;

    @FXML
    private Button btnChartSettingTemperature;

    @FXML
    private Button btnChartSettingHumidity;

    @FXML
    private Button btnChartSettingPressure;

    @FXML
    private Button btnChartSettingTemperatureMax;

    @FXML
    private Button btnChartSettingTemperatureMin;

    @FXML
    private Button btnChartSettingWindSpeed;

    @FXML
    private LineChart<String,Number> chart;

    @FXML
    private Text txtMaxTemperature;

    @FXML
    private Text txtMinTemperature;

    @FXML
    private Text txtMeanTemperature;

    @FXML
    private Text txtDevTemperature;

    @FXML
    private Text txtMaxHumidity;

    @FXML
    private Text txtMinHumidity;

    @FXML
    private Text txtMeanHumidity;

    @FXML
    private Text txtDevHumidity;

    @FXML
    private Text txtMaxPressure;

    @FXML
    private Text txtMinPressure;

    @FXML
    private Text txtMeanPressure;

    @FXML
    private Text txtDevPressure;

    @FXML
    private Text txtMaxWindSpeed;

    @FXML
    private Text txtMinWindSpeed;

    @FXML
    private Text txtMeanWindSpeed;

    @FXML
    private Text txtDevWindSpeed;


    private XYChart.Series temperatureSeries = new XYChart.Series();
    private XYChart.Series humiditySeries = new XYChart.Series();
    private XYChart.Series pressureSeries = new XYChart.Series();
    private XYChart.Series temperatureMaxSeries = new XYChart.Series();
    private XYChart.Series temperatureMinSeries = new XYChart.Series();
    private XYChart.Series windSpeedSeries = new XYChart.Series();
    private StringBuilder textPrint;
    private ObservableList<WindData> observableListWindData = FXCollections.observableArrayList();


    private boolean[] chartSettings = {false,false,false,false,false,false};
    private boolean isPauseClicked = false;
    private boolean containsData = false;
    private DataStorage dataStorage;
    private WeatherChecker weatherChecker;
    private boolean fileLoaded = false;
    private boolean isStarted = false;

    private File file;

    private DataManager dataManager = new DataManager();

    /**
     * Metoda tworzaca oraz startujaca nowy watek, metoda rowniez przygotowuje wszystkie elementy GUI oraz ustawia ich wartosci. Metoda równiez sprawdza poprawnosc wpisanej nazwy miasta.
     * @param event wygnereowany przez nacisnienie przycisku start
     */
    @FXML
    private void start(ActionEvent event) {
        if(!txtCity.getText().equals("")) {
            try{
                boolean r = false;
                isStarted = true;
                weatherChecker = new WeatherChecker();
                //sprawdzanie poprawnosci URL, dodawanie obserwatora
                weatherChecker.addObserver(this);

                if (!containsData || !dataStorage.getCityName().equals(txtCity.getText())) {
                    dataStorage = new DataStorage();
                    String properCity = weatherChecker.prepareConnection(txtCity.getText());

                    txtDisplayedCity.setText(properCity);
                    dataStorage.setCityName(properCity);
                    txtStartTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                    observableListWindData.clear();
                    textPrint = new StringBuilder();
                    txtText.clear();
                    r = true;
                } else {
                    weatherChecker.prepareConnection(txtCity.getText());
                }
                //rozruch 2 wątku
                weatherChecker.start();
                //Ustawienie poprawnie gui
                changeButtonStates();
                if (!containsData || r) {
                setUpChart();
                containsData = true;}

            } catch (IllegalArgumentException e){
                txtText.setText("Niepoprawna nazwa miasta!");
        }
        } else {
            txtText.setText("Należy podać nazwę miasta!");
        }
    }

    /**
     * Metoda wywolywana przez metode start, ktorej celem jest zmiana widoków oraz stanow przyciskow w GUI, jest rowniez odpowiedzialna za ustawienie serii temperatury na wykresie w momencie braku innych ustawien.
     */
    private void changeButtonStates(){
        btnPause.setDisable(false);
        btnPause.setOpacity(1);
        btnStop.setDisable(false);
        btnStop.setOpacity(1);
        btnClear.setDisable(false);
        btnClear.setOpacity(1);
        txtCity.setDisable(true);
        txtCity.setOpacity(0.5);
        btnStart.setDisable(true);
        btnStart.setOpacity(0.5);
        if(getTrueSettings()==0) {
            chartSettings[0] = true;
            btnChartSettingTemperature.setOpacity(0.5);
        }

    }

    /**
     * Metoda ma na celu zatrzymanie uruchomionego watku oraz zmiane stanu przyciskow GUI.
     * @param event wygenerowany przez nacisniecie przycisku stop
     */
    @FXML
    private void stop(ActionEvent event) {
        isStarted=false;
        weatherChecker.stop(this);
        btnPause.setDisable(true);
        btnPause.setOpacity(0.5);
        btnPause.setText("Pause");
        btnStop.setDisable(true);
        btnStop.setOpacity(0.5);
        txtCity.setDisable(false);
        txtCity.setOpacity(1);
        btnStart.setDisable(false);
        btnStart.setOpacity(1);
    }


    /**
     * Metoda pozwalająca na wybor pliku do zapisu lub odczytu.
     * @param event wygenerowany przez nacisniecie okna wyboru sciezki do pliku
     */
    @FXML
    private void pathClicked(MouseEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(stage);
        if(file == null) {
            if(txtPath.equals("Path:"))
            txtPath.setText("Path:");
            txtPath.setOpacity(0.75);
            fileLoaded = false;
        } else {
            txtPath.setText(file.getAbsolutePath());
            txtPath.setOpacity(0.5);
            fileLoaded = true;
        }
        checkIfAllCorrect();
    }

    /**
     * Metoda wywołujaca metode load obiektu klasy DataManager, która zajmuje sie odczytem pliku oraz zapisaniem go w postaci obiektu klasy DataStorage.
     * @param event wygnerowany przez nacisniecie przycisku load
     */
    @FXML
    private void load(ActionEvent event) {
        if(fileLoaded){
        try {
            dataStorage = dataManager.load(file);
            containsData = true;
            unpackLoadedDataStorage();
        }  catch (IOException e) {
            if(isStarted){
                stop(event);
            }
            containsData = false;
            txtText.setText("Błąd odczytu");
        } catch (RuntimeException e) {
            if(isStarted){
                stop(event);
            }
            containsData = false;
            txtText.setText("Wczytano niepoprawny plik lub plik jest pusty");
        }
        }
    }

    /**
     * Metoda ustawiajaca pola GUI przy uzyciu odczytanych wartosci wywoływana po poprawnym rozpakowaniu pliku przez metode load.
     */
    private void unpackLoadedDataStorage(){
        if(isStarted){
            stop(new ActionEvent());
        }
            textPrint = new StringBuilder();
            txtCity.setText(dataStorage.getCityName());
            txtDisplayedCity.setText(dataStorage.getCityName());
            txtStartTime.setText(dataStorage.getCheckTimeAtAsString(0));
            observableListWindData.clear();
            for (int i = 0; i < dataStorage.getDataStorageSize(); i++) {
                updateWindStatistics(i);
                if (textPrint.length() < 1)
                    textPrint.append(dataStorage.printReadAt(i));
                else
                    textPrint.append("\n\n" + dataStorage.printReadAt(i));
            }
            txtText.setText(textPrint.toString());
            setUpChart();
            updateStatistics();
    }

    /**
     * Metoda sprawdzajaca stan boolean fileLoaded oraz na jego podstawie ustawiajaca przyciski wywolujace metoda save oraz load
     */
    private void checkIfAllCorrect() {
        if(fileLoaded) {
            btnLoad.setDisable(false);
            btnLoad.setOpacity(1);
        } else {
            btnLoad.setDisable(true);
            btnLoad.setOpacity(0.5);
        }
        if(!(dataStorage == null))
        if(fileLoaded && dataStorage.getDataStorageSize()>0) {
            btnSave.setDisable(false);
            btnSave.setOpacity(1);
        } else {
            btnSave.setDisable(true);
            btnSave.setOpacity(0.5);
        }
    }

    /**
     * Metoda wywolujaca metode save obiektu klasy DataManager, ktora zajmuje sie odczytem pliku z postaci obiektu klasy DataStorage oraz zapisaniem go w postaci pliku .txt pod postacia kodu JSON.
     * @param event wygenerowany przez nacisniecie przycisku save
     */
    @FXML
    private void save(ActionEvent event) {
        if(fileLoaded) {
            try {
                dataManager.save(file.getAbsolutePath(),dataStorage);
            } catch (IOException e) {
                System.out.println("IO error");
            }
        }
    }

    /**
     * Metoda zajmujaca sie usunieciem wartosci z obiektu DataStorage oraz wyczyszczeniem serii uzywanych przez wykres.
     * @param event wygenerowany przez nacisniecie przycisku clear
     */
    @FXML
    private void clear(ActionEvent event) {
        textPrint = new StringBuilder();
        txtText.setText(textPrint.toString());
        String lastCity = dataStorage.getCityName();
        dataStorage = new DataStorage();
        dataStorage.setCityName(lastCity);
        observableListWindData.clear();
        temperatureSeries.getData().clear();
        humiditySeries.getData().clear();
        pressureSeries.getData().clear();
        temperatureMaxSeries.getData().clear();
        temperatureMinSeries.getData().clear();
        windSpeedSeries.getData().clear();
    }

    /**
     * Metoda wywolujaca na podstawie wartosci boolean isPauseClicked, metody obiektu klasy WeatherChecker pozwalajace na czasowe wstrzymanie lub wznowienie watku.
     * @param event wygenerowany przez nacisniecie przycisku pause/unpause
     */
    @FXML
    private void pauseAndUnpause(ActionEvent event) {
        if(!isPauseClicked){
            btnPause.setText("Unpause");
            weatherChecker.pause();
        }
        else if(isPauseClicked){
            btnPause.setText("Pause");
            weatherChecker.unpause();
        }
        isPauseClicked = !isPauseClicked;
        //tutaj trzeba zrobic wstrzymywanie (przerwa w czytaniu do momentu ponownego nacisniecia przycisku), nacisniecie zmienia pause na unpause,
        //potrzebny boolean czy zatrzymane ustawiony na false i zmienia sie go klikajac przycisk, metoda sprawddza i wykonuje odpowiednia czynnosc
    }

    /**
     * Metoda ustawiajaca tekst w textArea w GUI wywolywane przez metode updateConditions(ArrayList<String> conditionsList, LocalTime time)
     */
    private void updateTexts(){
        if(textPrint.length()<1)
        textPrint.append(dataStorage.printReadAt(dataStorage.getDataStorageSize()-1));
        else {
            textPrint.append("\n\n");
            textPrint.append(dataStorage.printReadAt(dataStorage.getDataStorageSize() - 1));
        }
        Platform.runLater(()->
        {txtText.setText(textPrint.toString());
        txtLastTime.setText(dataStorage.getCheckTimeAtAsString(dataStorage.getDataStorageSize()-1));
        txtReadCountNumber.setText(String.valueOf(dataStorage.getDataStorageSize()));});
    }

    /**
     * Metoda ustawiajaca wartosci w kolumnach tabeli dla kolumn colTime,colWindDirection,colWindSpeed wywolywane przez metode updateConditions(ArrayList<String> conditionsList, LocalTime time)
     */
    private void updateWindStatistics(int index){
        Platform.runLater(()->{
        observableListWindData.add(new WindData(dataStorage.getCheckTimeAtAsString(index),dataStorage.getWindSpeedAt(index),dataStorage.getWindDegreeAt(index)));
        colTime.setCellValueFactory(new PropertyValueFactory<>("timeData"));
        colWindDirection.setCellValueFactory(new PropertyValueFactory<>("windDegreeData"));
        colWindSpeed.setCellValueFactory(new PropertyValueFactory<>("windSpeedData"));
        tabWindStatistics.setItems(observableListWindData);});
    }

    /**
     * Metoda przygotowujaca wykres do otrzymania wartosci poprzez wyczyszczenie,przygotowanie oraz dodanie odpowiednich serii
     */
    private void setUpChart(){
        chart.getData().removeAll();
        chart.getData().clear();
        chart.setCreateSymbols(false);

        temperatureSeries = new XYChart.Series();
        temperatureSeries.setName("Temperature [\u2103]");
        humiditySeries = new XYChart.Series();
        humiditySeries.setName("Humidity [%]");
        pressureSeries = new XYChart.Series();
        pressureSeries.setName("Pressure [hPa]");
        temperatureMaxSeries = new XYChart.Series();
        temperatureMaxSeries.setName("Max temperature [\u2103]");
        temperatureMinSeries = new XYChart.Series();
        temperatureMinSeries.setName("Min temperature [\u2103]");
        windSpeedSeries = new XYChart.Series();
        windSpeedSeries.setName("Wind speed [m/s]");

        for(int i = 0; i < dataStorage.getDataStorageSize();i++) {
            String time = dataStorage.getCheckTimeAtAsString(i);
            temperatureSeries.getData().add(new XYChart.Data<>(time, dataStorage.getTemperatureAt(i)));
            humiditySeries.getData().add(new XYChart.Data<>(time, dataStorage.getHumidityAt(i)));
            pressureSeries.getData().add(new XYChart.Data<>(time, dataStorage.getPressureAt(i)));
            temperatureMaxSeries.getData().add(new XYChart.Data<>(time, dataStorage.getMaxTemperatureAt(i)));
            temperatureMinSeries.getData().add(new XYChart.Data<>(time, dataStorage.getMinTemperatureAt(i)));
            windSpeedSeries.getData().add(new XYChart.Data<>(time, dataStorage.getWindSpeedAt(i)));
        }
        if(chartSettings[0])
        chart.getData().add(temperatureSeries);
        if(chartSettings[1])
            chart.getData().add(humiditySeries);
        if(chartSettings[2])
            chart.getData().add(pressureSeries);
        if(chartSettings[3])
            chart.getData().add(temperatureMaxSeries);
        if(chartSettings[4])
            chart.getData().add(temperatureMinSeries);
        if(chartSettings[5])
            chart.getData().add(windSpeedSeries);
    }

    /**
     * Metoda dodajace najnowszy odczyt do serii wywoływana przez metode updateConditions(ArrayList<String> conditionsList, LocalTime time)
     */
    private void updateChart(){
        Platform.runLater(()-> {
            int i = dataStorage.getDataStorageSize() - 1;
            String time = dataStorage.getCheckTimeAtAsString(i);
            temperatureSeries.getData().add(new XYChart.Data<>(time, dataStorage.getTemperatureAt(i)));
            humiditySeries.getData().add(new XYChart.Data<>(time, dataStorage.getHumidityAt(i)));
            pressureSeries.getData().add(new XYChart.Data<>(time, dataStorage.getPressureAt(i)));
            temperatureMaxSeries.getData().add(new XYChart.Data<>(time, dataStorage.getMaxTemperatureAt(i)));
            temperatureMinSeries.getData().add(new XYChart.Data<>(time, dataStorage.getMinTemperatureAt(i)));
            windSpeedSeries.getData().add(new XYChart.Data<>(time, dataStorage.getWindSpeedAt(i)));
        });
        //nalezy dodawac kolejne punkty jesli takowy sie pojawil w data storage
    }


    /**
     * Metoda pobierajaca nacisniecie przycisku odpowiadajacego za dodanie lub usuniecie serii temperatureSeries z wykresu oraz wywołujca metode odpowiedzialna z odpowiednie ustawienie wykresu
     * @param event wygenerowany przez nacisniecie przycisku temperature
     */
    @FXML
    private void chartSettingTemperature(MouseEvent event) {
        if(event.getClickCount()<2)
        applyChartSettings(0,btnChartSettingTemperature,temperatureSeries); }

    /**
     * Metoda pobierajaca nacisniecie przycisku odpowiadajacego za dodanie lub usuniecie serii humiditySeries z wykresu oraz wywołujca metode odpowiedzialna z odpowiednie ustawienie wykresu
     * @param event wygenerowany przez nacisniecie przycisku humidity
     */
    @FXML
    private void chartSettingHumidity(MouseEvent event) {
        if(event.getClickCount()<2)
        applyChartSettings(1,btnChartSettingHumidity,humiditySeries);
    }

    /**
     * Metoda pobierajaca nacisniecie przycisku odpowiadajacego za dodanie lub usuniecie serii pressureSeries z wykresu oraz wywołujca metode odpowiedzialna z odpowiednie ustawienie wykresu
     * @param event wygenerowany przez nacisniecie przycisku pressure
     */
    @FXML
    private void chartSettingPressure(MouseEvent event) {
        if(event.getClickCount()<2)
        applyChartSettings(2,btnChartSettingPressure,pressureSeries);
    }

    /**
     * Metoda pobierajaca nacisniecie przycisku odpowiadajacego za dodanie lub usuniecie serii temperatureMaxSeries z wykresu oraz wywołujca metode odpowiedzialna z odpowiednie ustawienie wykresu
     * @param event wygenerowany przez nacisniecie przycisku temperature max
     */
    @FXML
    private void chartSettingTemperatureMax(MouseEvent event) {
        if(event.getClickCount()<2)
        applyChartSettings(3,btnChartSettingTemperatureMax,temperatureMaxSeries); }

    /**
     * Metoda pobierajaca nacisniecie przycisku odpowiadajacego za dodanie lub usuniecie serii temperatureMin z wykresu oraz wywołujca metode odpowiedzialna z odpowiednie ustawienie wykresu
     * @param event wygenerowany przez nacisniecie przycisku temperature min
     */
    @FXML
    private void chartSettingTemperatureMin(MouseEvent event) {
        if(event.getClickCount()<2)
        applyChartSettings(4,btnChartSettingTemperatureMin,temperatureMinSeries); }
    /**
     * Metoda pobierajaca nacisniecie przycisku odpowiadajacego za dodanie lub usuniecie serii windSpeedSeries z wykresu oraz wywołujca metode odpowiedzialna z odpowiednie ustawienie wykresu
     * @param event wygenerowany przez nacisniecie przycisku wind speed
     */

    @FXML
    private void chartSettingWindSpeed(MouseEvent event) {
        if(event.getClickCount()<2)
        applyChartSettings(5,btnChartSettingWindSpeed,windSpeedSeries);
    }


    /**
     * Metoda ustawiajaca odpowiednie serie na wykresie według ustawien chartSettings
     * @param setting numer odpowiadajacy boolean z tablicy odpowiadajacej za ustawienia
     * @param button przycisk ktory zostal nacisniety w celu dodania serii
     * @param series dodawana seria
     */
    private void applyChartSettings(int setting, Button button, XYChart.Series series)
        {
        if(!chartSettings[setting] && !chart.getData().contains(series)){
            button.setOpacity(0.5);
            chart.getData().add(series);
            chartSettings[setting]=true;
        }
        else if(chartSettings[setting] && chart.getData().contains(series) && 1<getTrueSettings()){
            button.setOpacity(1);
            chart.getData().remove(series);
            chartSettings[setting]=false;
        }
    }

    /**
     * Metoda sprawdzajaca ile serii znajduje sie na wykresie
     * @return ilosc serii wyswietlanych na wykresie
     */
    private int getTrueSettings(){
        int t=0;
        for (boolean bool:chartSettings){
            if(bool){
                t++;
            }
        }
        return t;
    }

    /**
     * Metoda ustawiajaca pola txt w tab Statistics na podstawie danych otrzymanych z obiektu dataStorage. Metoda jest wywolywana przez updateConditions(ArrayList<String> conditionsList, LocalTime time)
     */
    private void updateStatistics(){
        DecimalFormat df = new DecimalFormat("#.###");
        txtMaxTemperature.setText(df.format(dataStorage.getMaximum(dataStorage.getTemperature())));
        txtMaxHumidity.setText(df.format(dataStorage.getMaximum(dataStorage.getHumidity())));
        txtMaxPressure.setText(df.format(dataStorage.getMaximum(dataStorage.getPressure())));
        txtMaxWindSpeed.setText(df.format(dataStorage.getMaximum(dataStorage.getWindSpeed())));

        txtMinTemperature.setText(df.format(dataStorage.getMinimum(dataStorage.getTemperature())));
        txtMinHumidity.setText(df.format(dataStorage.getMinimum(dataStorage.getHumidity())));
        txtMinPressure.setText(df.format(dataStorage.getMinimum(dataStorage.getPressure())));
        txtMinWindSpeed.setText(df.format(dataStorage.getMinimum(dataStorage.getWindSpeed())));

        txtMeanTemperature.setText(df.format(dataStorage.getMean(dataStorage.getTemperature())));
        txtMeanHumidity.setText(df.format(dataStorage.getMean(dataStorage.getHumidity())));
        txtMeanPressure.setText(df.format(dataStorage.getMean(dataStorage.getPressure())));
        txtMeanWindSpeed.setText(df.format(dataStorage.getMean(dataStorage.getWindSpeed())));

        txtDevTemperature.setText(df.format(dataStorage.getStandardDeviation(dataStorage.getTemperature())));
        txtDevHumidity.setText(df.format(dataStorage.getStandardDeviation(dataStorage.getHumidity())));
        txtDevPressure.setText(df.format(dataStorage.getStandardDeviation(dataStorage.getPressure())));
        txtDevWindSpeed.setText(df.format(dataStorage.getStandardDeviation(dataStorage.getWindSpeed())));
    }


    /**
    * Metoda wywolywana przez watek odpowiadajacy za pobieranie wartosci z serwera, metoda uaktualnia wszystkie informacje w dataStorage oraz GUI
     * @param conditionsList lista pobranych warunkow pogodowych
     * @param time chwila w ktorej dokonanu pomiaru
     */
    @Override
    public void updateConditions(ArrayList<String> conditionsList, LocalTime time) {
        dataStorage.addRead(time,conditionsList);
        updateTexts();
        updateWindStatistics(dataStorage.getDataStorageSize()-1);
        updateChart();
        updateStatistics();
        checkIfAllCorrect();
    }

}
