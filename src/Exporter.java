import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Exporter {
    private String outFilePath;

    public Exporter(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    public void exportLogbook(Logbook logbook) {
        List<Pair<Integer, String[][]>> tables = logbook.getLogbookAsTables();
        try {
            writeToCSV(tables);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeToCSV(List<Pair<Integer, String[][]>> tables) throws FileNotFoundException {
        PrintWriter pw;
        for (Pair<Integer, String[][]> pair : tables) {
            String fileName = outFilePath + String.valueOf(pair.getKey()) + ".csv";
            pw = new PrintWriter(new File(fileName));
            for (int row = 0; row < pair.getValue().length; row++) {
                for (int col = 0; col < 8; col++) {
                    pw.append(pair.getValue()[row][col] + ",");
                }
                pw.append("\n");
            }
            pw.flush();
            pw.close();
        }
    }
}
