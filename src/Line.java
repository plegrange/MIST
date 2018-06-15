public class Line {
    private final String lineID;
    private Station endStation;

    public Line(String lineID, Station endStation) {
        this.lineID = lineID;
        this.endStation = endStation;
    }

    public void initializeLine(Ledger initialStateLedger) {
        endStation.setupInitialState(initialStateLedger);
    }

    public Ledger doTimeStep() {
        return endStation.doTimeStep(new Ledger());
    }
}
