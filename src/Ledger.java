import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Ledger {
    private List<Pair<String, Entry>> entries;

    public Ledger() {
        entries = new ArrayList<>();
    }

    public void addEntry(String stationID, String status, double upChance, int bufferLevel, int bufferCapacity, double cycleTime, double meanRepairTime, double timeStep) {
        entries.add(new Pair<>(stationID, new Entry(status, upChance, bufferLevel, bufferCapacity, cycleTime, meanRepairTime, timeStep)));
    }

    public void addEntry(String stationID, Entry entry) {
        entries.add(new Pair<>(stationID, entry));
    }

    public List<Pair<String, Entry>> getEntries() {
        return entries;
    }

    public Entry getEntry(String stationID) {
        for (Pair pair : entries) {
            if (pair.getKey().equals(stationID))
                return (Entry) pair.getValue();
        }
        return null;
    }

    public Ledger createVariationClone() {
        Ledger newLedger = new Ledger();
        for (Pair<String, Entry> entry : entries) {
            newLedger.addEntry(entry.getKey()
                    , entry.getValue().status
                    , createRandomNumber(0.9, 1.0)
                    , entry.getValue().bufferLevel
                    , entry.getValue().bufferCapacity
                    , entry.getValue().cycleTime
                    , createRandomNumber(300.0, 1500.0)
                    , 0.0);
        }
        return newLedger;
    }

    private double createRandomNumber(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public Ledger createTrialVectorLedger(Ledger b, Ledger c, double scalingFactor) {
        Ledger newLedger = new Ledger();
        for (Pair<String, Entry> entry : entries) {
            Entry value = entry.getValue();
            newLedger.addEntry(entry.getKey(), value.status
                    , value.upChance + scalingFactor * (b.getEntry(entry.getKey()).upChance - c.getEntry(entry.getKey()).upChance)
                    , value.bufferLevel, value.bufferCapacity, value.cycleTime
                    , value.meanRepairTime + scalingFactor * (b.getEntry(entry.getKey()).meanRepairTime - c.getEntry(entry.getKey()).meanRepairTime)
                    , value.timeStep
            );
        }
        return newLedger;
    }

    public Ledger crossOver(Ledger b, double crossOverRate) {
        Ledger newLedger = new Ledger();
        double upChance, meanRepairTime;
        for (Pair<String, Entry> entry : entries) {
            if (Math.random() < crossOverRate) {
                upChance = entry.getValue().upChance;
            } else {
                upChance = b.getEntry(entry.getKey()).upChance;
            }
            if (Math.random() < crossOverRate) {
                meanRepairTime = entry.getValue().meanRepairTime;
            } else {
                meanRepairTime = b.getEntry(entry.getKey()).meanRepairTime;
            }
            newLedger.addEntry(entry.getKey()
                    , entry.getValue().status
                    , upChance
                    , entry.getValue().bufferLevel
                    , entry.getValue().bufferCapacity
                    , entry.getValue().cycleTime
                    , meanRepairTime
                    , entry.getValue().timeStep);
        }
        return newLedger;
    }
}
