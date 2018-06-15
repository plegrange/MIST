package Simulator;

import org.omg.CORBA.portable.IDLEntity;

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

    public void setupInitialState(String status, double upChance, double bufferLevel, double cycleTime) {
        this.status = status;
        this.upChance = upChance;
        this.bufferLevel = bufferLevel;
        this.localTimeStep = 0;
        this.cycleTime = cycleTime;
    }

}
