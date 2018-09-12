public class DataManager {
    private Importer importer;
    private Exporter exporter;

    private Logbook realBook, simBook;
    private String inFilePath, outFilePath;

    public DataManager(String inFilePath, String outFilePath) {
        this.inFilePath = inFilePath;
        this.outFilePath = outFilePath;
        importer = new Importer();
        exporter = new Exporter(outFilePath);
    }

    public void importData() {
        realBook = importer.importRealDataToLogbook(inFilePath);
    }

    public void exportData() {
        exporter.exportLogbook(realBook);
        exporter.exportLogbook(simBook);
    }


}
