import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private Line line;
    private Ledger stateLedger;
    private double secondsToSim = 10000.0;
    private double timeIncrement = 1.0;

    private List<Ledger> logBook;
    private Ledger recordLedger;

    public SimulationManager(Ledger initialStateLedger) {
        logBook = new ArrayList<>();
        createLine();
        line.initializeLine(initialStateLedger);

        for (int i = 0; i < secondsToSim; i += timeIncrement) {
            stateLedger = line.doTimeStep();
            logBook.add(stateLedger);
        }
        recordLedger = sortLogbook();
    }

    private Ledger sortLogbook() {
        Ledger newLedger = new Ledger();
        for (Ledger ledger : logBook) {
            for (Pair<String, Entry> entryPair : ledger.getEntries()) {
                newLedger.addEntry(entryPair.getKey(), entryPair.getValue());
            }
        }
        return newLedger;
    }

    public void createLine() {
        Station MA21 = new Station("MA21", timeIncrement);

        Station AS23 = new Station("AS23", timeIncrement);
        MA21.setPreviousStations(AS23);

        Station AS22 = new Station("AS22", timeIncrement);
        AS23.setPreviousStations(AS22);

        Station AS21 = new Station("AS21", timeIncrement);
        AS22.setPreviousStations(AS21);

        Station UB23 = new Station("UB23", timeIncrement);
        AS21.setPreviousStations();

        Station UB21 = new Station("UB21", timeIncrement);
        UB23.setPreviousStations(UB21);

        Station HC25 = new Station("HC25", timeIncrement);
        UB21.setPreviousStations(HC25);

        Station HC24 = new Station("HC24", timeIncrement);
        HC25.setPreviousStations(HC24);

        Station HC23 = new Station("HC23", timeIncrement);
        HC24.setPreviousStations(HC23);

        Station HC22 = new Station("HC22", timeIncrement);
        HC23.setPreviousStations(HC22);

        Station HC21 = new Station("HC21", timeIncrement);
        HC22.setPreviousStations(HC21);

        Station HU22 = new Station("HU22", timeIncrement);
        HC21.setPreviousStations(HU22);

        Station HU21 = new Station("HU21", timeIncrement);
        HU22.setPreviousStations(HU21);

        line = new Line("Z2", MA21);
    }

    public Ledger getRecordLedger() {
        return recordLedger;
    }
}
