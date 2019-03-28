package edu.ib;

public class WindData {
    private String timeData;
    private Double windSpeedData;
    private Double windDegreeData;
    /**
     * Konstruktor obiektu klasy WindData pobierajacy String timeData, Double windSpeedData, Double windDegreeData
     * @param timeData czas w ktorym dokonanu pomiaru
     * @param windSpeedData predkosc wiatru w danym pomiarze
     * @param windDegreeData kierunek z ktorego wiatr pochodzi
     */
    public WindData(String timeData, Double windSpeedData, Double windDegreeData) {
        this.timeData = timeData;
        this.windSpeedData = windSpeedData;
        this.windDegreeData = windDegreeData;
    }

    public String getTimeData() {
        return timeData;
    }

    public void setTimeData(String timeData) {
        this.timeData = timeData;
    }

    public Double getWindSpeedData() {
        return windSpeedData;
    }

    public void setWindSpeedData(Double windSpeedData) {
        this.windSpeedData = windSpeedData;
    }

    public Double getWindDegreeData() {
        return windDegreeData;
    }

    public void setWindDegreeData(Double windDegreeData) {
        this.windDegreeData = windDegreeData;
    }
}
