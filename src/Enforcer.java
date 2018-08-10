import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Enforcer {
    public List<Logbook> runSimulation(Ledger initalStateLedger, SimulationManager simulationManager) {
        List<Logbook> logbooks = new ArrayList<>();
        for (int i = 0; i < initalStateLedger.getEntries().size(); i++) {
            logbooks.add(simulationManager.runSimulation(cloneAndForce(initalStateLedger, i)));
        }
        return logbooks;
    }

    private Ledger cloneAndForce(Ledger initialStateLedger, int entryIndex) {
        return initialStateLedger.cloneAndForceLedger(entryIndex);
    }
}
