package Simulator;

import javafx.stage.Stage;

import java.util.List;

public class Line {
    private final String lineID;
    private Station endStation;

    public Line(String lineID, Station endStation) {
        this.lineID = lineID;
        this.endStation = endStation;
    }
}
