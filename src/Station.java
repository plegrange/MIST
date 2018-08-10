import java.util.ArrayList;
import java.util.List;

public class Station {
    private double timeIncrement;
    public final String ID;
    public String status;
    public double upChance, cycleTime, meanRepairTime;
    public int bufferLevel, bufferCapacity;
    public int globalTimeStep;
    private double timeInCurrentState;
    private List<Station> previousStations;

    public Station(String ID, double timeIncrement) {
        this.ID = ID;
        this.previousStations = new ArrayList<>();
        this.timeIncrement = timeIncrement;
    }

    public void setPreviousStations(Station... previousStations) {
        for (Station s : previousStations) {
            this.previousStations.add(s);
        }
    }

    public void setupInitialState(Ledger ledger) {
        Entry entry = ledger.getEntry(ID);
        timeInCurrentState = 0.0;
        this.status = entry.status;
        this.upChance = entry.upChance;
        this.bufferLevel = entry.bufferLevel;
        this.globalTimeStep = 0;
        this.cycleTime = entry.cycleTime;
        this.meanRepairTime = entry.meanRepairTime;
        for (Station s : previousStations) {
            s.setupInitialState(ledger);
        }
    }

    public Ledger doTimeStep(Ledger ledger) {
        processStep();
        ledger.addEntry(ID, status, upChance, bufferLevel, bufferCapacity, cycleTime, meanRepairTime, globalTimeStep);
        for (Station s : previousStations) {
            ledger = s.doTimeStep(ledger);
        }
        return ledger;
    }

    private void processStep() {
        switch (status) {
            case "WORKING":
                work();
                break;
            case "IDLE":
                idle();
                break;
            case "FAILED":
                failed();
                break;
            case "STARVED":
                starved();
                break;
            case "BLOCKED":
                blocked();
                break;
            case "FORCED":
                forced();
                break;
            default:
                break;
        }
    }

    private void forced() {
        return;
    }

    private void work() {
        if (timeInCurrentState < cycleTime) {
            if (Math.random() > upChance) {
                status = "FAILED";
                timeInCurrentState = 0.0;
            } else {
                timeInCurrentState += timeIncrement;
            }
            return;
        } else if (bufferLevel >= bufferCapacity)
            status = "BLOCKED";
        else {
            bufferLevel++;
            status = "IDLE";
        }
        timeInCurrentState = 0.0;
    }

    private void failed() {
        if (timeInCurrentState >= meanRepairTime) {
            status = "WORKING";
            timeInCurrentState = 0.0;
        } else timeInCurrentState += timeIncrement;
    }

    private void idle() {
        if (previousStations.size() == 0) {
            status = "WORKING";
            timeInCurrentState = 0.0;
            return;
        }
        for (Station s : previousStations) {
            if (s.bufferLevel == 0) {
                status = "STARVED";
                timeInCurrentState = 0.0;
                return;
            }
        }

        for (Station s : previousStations) {
            s.bufferLevel--;
        }
        status = "WORKING";
        timeInCurrentState = 0.0;
    }

    private void starved() {
        for (Station s : previousStations) {
            if (s.bufferLevel == 0) {
                timeInCurrentState += timeIncrement;
                return;
            }
        }
        for (Station s : previousStations) {
            s.bufferLevel--;
        }
        status = "WORKING";
        timeInCurrentState = 0.0;
    }

    private void blocked() {
        if (bufferLevel < bufferCapacity) {
            status = "WORKING";
            timeInCurrentState = 0.0;
        } else {
            timeInCurrentState += timeIncrement;
        }
    }

}
