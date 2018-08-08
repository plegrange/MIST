import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Logbook {
    private List<Pair<Integer, Ledger>> ledgers;
    private Ledger initialStateLedger;

    public Logbook(Ledger initialStateLedger) {
        ledgers = new ArrayList<>();
        this.initialStateLedger = initialStateLedger;
    }

    public void addLedger(int timestamp, Ledger ledger) {
        ledgers.add(new Pair<>(timestamp, ledger));
    }

    public void compareLogbook(Logbook other) {
        double totalError = 0.0;
        for (Pair<Integer, Ledger> pair : ledgers) {
            totalError += pair.getValue().getError(other.findLedger(pair.getKey()));
        }
        initialStateLedger.setError(totalError);
    }

    private Ledger findLedger(Integer timeStamp) {
        for (Pair<Integer, Ledger> pair : ledgers) {
            if (pair.getKey().equals(timeStamp)) {
                return pair.getValue();
            }
        }
        return null;
    }

    public List<Pair<Integer, Ledger>> getLedgers() {
        return ledgers;
    }

    public Ledger getInitialStateLedger() {
        return initialStateLedger;
    }

    public void setInitialStateLedger(Ledger initialStateLedger) {
        this.initialStateLedger = initialStateLedger;
    }
}
