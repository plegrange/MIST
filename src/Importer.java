import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importer {

    public Logbook importRealDataToLogbook(String path) {
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";
        List<Entry> entries = new ArrayList<>();
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
        return buildLogbook(entries);
    }

    private Logbook buildLogbook(List<Entry> entries) {
        Ledger newLedger;
        Logbook logbook = null;
        Entry entry = null;
        boolean[] entryPositions;
        while (entries.size() > 0) {
            entry = entries.get(0);
            newLedger = new Ledger();
            entryPositions = new boolean[entries.size()];
            for (int i = 1; i < entries.size(); i++) {
                if (entries.get(i).timeStep == entry.timeStep) {
                    entryPositions[i] = true;
                }
            }
            for (int i = entries.size() - 1; i >= 0; i--) {
                if (entryPositions[i]) {
                    Entry temp = entries.remove(i);
                    newLedger.addEntry(temp.stationID, temp);
                }
            }
            if (logbook == null) {
                logbook = new Logbook(newLedger);
            } else logbook.addLedger(newLedger.getTimeStep(), newLedger);
        }
        return logbook;
    }
}
