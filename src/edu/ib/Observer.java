package edu.ib;

import java.time.LocalTime;
import java.util.ArrayList;

public interface Observer {
    void updateConditions(ArrayList<String> conditionsList, LocalTime time);
}
