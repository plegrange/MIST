import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Ledger {
    private List<Pair<String, Entry>> entries;
    private double error;

    public Ledger() {
        entries = new ArrayList<>();
    }

    public void addEntry(String stationID, String status, double upChance, int bufferLevel, int bufferCapacity, double cycleTime, double meanRepairTime, int timeStep) {
        entries.add(new Pair<>(stationID, new Entry(status, upChance, bufferLevel, bufferCapacity, cycleTime, meanRepairTime, timeStep)));
    }

    public double getError(Ledger other) {
        //int totalErrors = 0;
        double totalDeviation = 0.0;
        for (Pair<String, Entry> pair : other.entries) {
            Entry currentEntry = getEntry(pair.getKey());
            if (currentEntry.status != pair.getValue().status) {
                totalDeviation += 10.0;
                //      totalErrors++;
            }
            if (currentEntry.bufferLevel != pair.getValue().bufferLevel) {
                totalDeviation += Math.abs(currentEntry.bufferLevel - pair.getValue().bufferLevel);
                //    totalErrors++;
            }
            if (currentEntry.bufferLevel != pair.getValue().bufferLevel) {
                totalDeviation += Math.abs(currentEntry.bufferLevel - pair.getValue().bufferLevel);
                //  totalErrors++;
            }
        }
        return totalDeviation;
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
                    , 0);
        }
        return newLedger;
    }

    private double createRandomNumber(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public Ledger createTrialVectorLedger(Ledger a, Ledger b, double scalingFactor) {
        Ledger newLedger = new Ledger();
        for (Pair<String, Entry> entry : entries) {
            Entry value = entry.getValue();
            newLedger.addEntry(entry.getKey(), value.status
                    , value.upChance + scalingFactor * (a.getEntry(entry.getKey()).upChance - b.getEntry(entry.getKey()).upChance)
                    , value.bufferLevel, value.bufferCapacity, value.cycleTime
                    , value.meanRepairTime + scalingFactor * (a.getEntry(entry.getKey()).meanRepairTime - b.getEntry(entry.getKey()).meanRepairTime)
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

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}
