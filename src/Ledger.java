import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Ledger {
    private List<Pair<String, Entry>> entries;

    public Ledger() {
        entries = new ArrayList<>();
    }

    public void addEntry(String stationID, String status, double upChance, double bufferLevel, double cycleTime) {
        entries.add(new Pair<>(stationID, new Entry(status, upChance, bufferLevel, cycleTime)));
    }

    public void addEntry(String stationID, Entry entry) {
        entries.add(new Pair<>(stationID, entry));
    }

    public Entry getEntry(String stationID) {
        for (Pair pair : entries) {
            if (pair.getKey().equals(stationID))
                return (Entry) pair.getValue();
        }
        return null;
    }


}
