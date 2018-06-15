import java.util.ArrayList;
import java.util.List;

public class Station {
    private double timeIncrement;
    public final String ID;
    public String status;
    public double upChance;
    public int bufferLevel, bufferCapacity;
    public double cycleTime, meanRepairTime;
    public double globalTimeStep, downTime;
    private double localTimeStep;
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
        localTimeStep = 0.0;
        this.status = entry.status;
        this.upChance = entry.upChance;
        this.bufferLevel = entry.bufferLevel;
        this.localTimeStep = 0.0;
        this.globalTimeStep = 0.0;
        this.cycleTime = entry.cycleTime;
        this.meanRepairTime = entry.meanRepairTime;
        for (Station s : previousStations) {
            s.setupInitialState(ledger);
        }
    }

    public Ledger doTimeStep(Ledger ledger) {
        for (Station s : previousStations) {
            ledger = s.doTimeStep(ledger);
        }
        processStep();
        ledger.addEntry(ID, status, upChance, bufferLevel, bufferCapacity, cycleTime, meanRepairTime, globalTimeStep);
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
            default:
                break;
        }
    }

    private void work() {
        if (localTimeStep < cycleTime) {
            if (Math.random() > upChance) {
                status = "FAILED";
            } else {
                localTimeStep += timeIncrement;
            }
            return;
        } else if (bufferLevel >= bufferCapacity)
            status = "BLOCKED";
        else {
            bufferLevel++;
            status = "IDLE";
        }
        localTimeStep = 0.0;
    }

    private void failed() {
        if (downTime >= meanRepairTime) {
            status = "WORKING";
            downTime = 0.0;
        } else downTime += timeIncrement;
    }

    private void idle() {
        for (Station s : previousStations) {
            if (s.bufferLevel == 0) {
                status = "STARVED";
                return;
            }
        }
        for (Station s : previousStations) {
            s.bufferLevel--;
        }
        status = "WORKING";
    }

    private void starved() {
        for (Station s : previousStations) {
            if (s.bufferLevel == 0) {
                return;
            }
        }
        for (Station s : previousStations) {
            bufferLevel--;
        }
        status = "WORKING";
    }

    private void blocked() {
        if (bufferLevel < bufferCapacity) {
            status = "WORKING";
        }
    }

}
