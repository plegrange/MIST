import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importer {
    private List<Entry> entries;

    public Logbook importRealDataToLogbook(String path) {
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";
        entries = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] entryString = line.split(splitBy);
                entries.add(new Entry(entryString[1],
                        entryString[2],
                        Double.valueOf(entryString[3]),
                        Integer.valueOf(entryString[4]),
                        Integer.valueOf(entryString[5]),
                        Double.valueOf(entryString[6]),
                        Double.valueOf(entryString[7]),
                        Integer.valueOf(entryString[0])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buildLogbook();
    }

    private Logbook buildLogbook() {
        Ledger initialStateLedger = buildInitialStateLedger();
        Ledger liveLedger = initialStateLedger.clonePure();
        Logbook logbook = new Logbook(initialStateLedger);
        Entry nextEntry;
        int timeStamp = 1;
        while (entries.size() > 0) {
            nextEntry = getNextEntry();
            liveLedger.updateEntry(new Pair<>(nextEntry.stationID, nextEntry));
            logbook.addLedger(timeStamp, liveLedger.clonePure());
            timeStamp++;
        }
        return logbook;
    }

    private Entry getNextEntry() {
        return entries.remove(0);
    }

    private Ledger buildInitialStateLedger() {
        Ledger initialStateLedger = new Ledger();
        List<String> listOfStationIDs = getListOfStationIDs();
        for (int i = 0; i < listOfStationIDs.size(); i++) {
            initialStateLedger.addEntry(getFirstOccurrence(listOfStationIDs.get(i)));
        }
        return initialStateLedger;
    }

    private Entry getFirstOccurrence(String stationID) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).stationID.equals(stationID)) {
                return entries.remove(i);
            }
        }
        return null;
    }

    private List<String> getListOfStationIDs() {
        List<String> stationIDs = new ArrayList<>();
        boolean found;
        for (int i = 0; i < entries.size(); i++) {
            found = false;
            for (int j = 0; j < stationIDs.size(); j++) {
                if (stationIDs.get(j).equals(entries.get(i).stationID)) {
                    found = true;
                }
            }
            if (!found) {
                stationIDs.add(entries.get(i).stationID);
            }
        }
        return stationIDs;
    }
}
