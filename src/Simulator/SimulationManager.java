package Simulator;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private Line line;

    public SimulationManager() {

    }

    public void createLine() {
        Station MA21 = new Station("MA21");

        Station AS23 = new Station("AS23");
        MA21.setPreviousStations(AS23);

        Station AS22 = new Station("AS22");
        AS23.setPreviousStations(AS22);

        Station AS21 = new Station("AS21");
        AS22.setPreviousStations(AS21);

        Station UB23 = new Station("UB23");
        AS21.setPreviousStations();

        Station UB21 = new Station("UB21");
        UB23.setPreviousStations(UB21);

        Station HC25 = new Station("HC25");
        UB21.setPreviousStations(HC25);

        Station HC24 = new Station("HC24");
        HC25.setPreviousStations(HC24);

        Station HC23 = new Station("HC23");
        HC24.setPreviousStations(HC23);

        Station HC22 = new Station("HC22");
        HC23.setPreviousStations(HC22);

        Station HC21 = new Station("HC21");
        HC22.setPreviousStations(HC21);

        Station HU22 = new Station("HU22");
        HC21.setPreviousStations(HU22);

        Station HU21 = new Station("HU21");
        HU22.setPreviousStations(HU21);

        line = new Line("Z2", MA21);
    }

}
