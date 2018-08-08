public class DataManager {
    private Importer importer;
    private Exporter exporter;

    private Logbook realBook, simBook;
    private String filePath = "";

    public DataManager() {
        realBook = importer.importRealDataToLogbook(filePath);
    }
}
