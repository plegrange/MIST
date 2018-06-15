import java.util.List;

public class Station {

    public final String ID;
    public String status;
    public double upChance;
    public double bufferLevel;
    public double cycleTime;
    public int localTimeStep;
    private List<Station> previousStations;

    public Station(String ID) {
        this.ID = ID;
    }

    public void setPreviousStations(Station... previousStations) {
        for (Station s : previousStations) {
            this.previousStations.add(s);
        }
    }

    public void setupInitialState(Ledger ledger) {
        Entry entry = ledger.getEntry(ID);
        this.status = entry.status;
        this.upChance = entry.upChance;
        this.bufferLevel = entry.bufferLevel;
        this.localTimeStep = 0;
        this.cycleTime = entry.cycleTime;
        for (Station s : previousStations) {
            s.setupInitialState(ledger);
        }
    }

}
