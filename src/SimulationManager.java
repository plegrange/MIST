import javafx.util.Pair;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private Line line;
    private Ledger stateLedger;
    private int secondsToSim = 3600;
    private int timeIncrement = 1;

    public SimulationManager() {
        createLine();
    }

    public Logbook runSimulation(Ledger initialStateLedger) {
        Logbook logBook = new Logbook(initialStateLedger);
        //logBook.addLedger(0, initialStateLedger);
        line.initializeLine(initialStateLedger);
        for (int i = 1; i < secondsToSim; i += timeIncrement) {
            stateLedger = line.doTimeStep();
            logBook.addLedger(i, stateLedger);
        }
        return logBook;
    }


    public void createLine() {
        Station MA213 = new Station("MA213", timeIncrement);

        Station MA212 = new Station("MA212", timeIncrement);
        MA213.setPreviousStations(MA212);

        Station AS23 = new Station("AS23", timeIncrement);
        MA212.setPreviousStations(AS23);

        Station AS22 = new Station("AS22", timeIncrement);
        AS23.setPreviousStations(AS22);

        Station AS21 = new Station("AS21", timeIncrement);
        AS22.setPreviousStations(AS21);

        Station UB233 = new Station("UB233", timeIncrement);
        AS21.setPreviousStations(UB233);

        Station UB232 = new Station("UB232", timeIncrement);
        UB233.setPreviousStations(UB232);

        Station UB231 = new Station("UB231", timeIncrement);
        UB232.setPreviousStations(UB231);

        Station UB213 = new Station("UB213", timeIncrement);
        UB231.setPreviousStations(UB213);

        Station UB212 = new Station("UB212", timeIncrement);
        UB213.setPreviousStations(UB212);

        Station HC252 = new Station("HC252", timeIncrement);
        UB212.setPreviousStations(HC252);

        Station HC251 = new Station("HC251", timeIncrement);
        HC252.setPreviousStations(HC251);

        Station HC242 = new Station("HC242", timeIncrement);
        HC251.setPreviousStations(HC242);

        Station HC241 = new Station("HC241", timeIncrement);
        HC242.setPreviousStations(HC241);

        Station HC23 = new Station("HC23", timeIncrement);
        HC241.setPreviousStations(HC23);

        Station HC22 = new Station("HC22", timeIncrement);
        HC23.setPreviousStations(HC22);

        Station HC21 = new Station("HC21", timeIncrement);
        HC22.setPreviousStations(HC21);

        Station HU22 = new Station("HU22", timeIncrement);
        HC21.setPreviousStations(HU22);

        Station HU21 = new Station("HU21", timeIncrement);
        HU22.setPreviousStations(HU21);

        line = new Line("Z2", MA213);
    }
}
