import javafx.beans.property.SimpleDoubleProperty;

import java.util.Random;

public class Main {
    private DataManager dataManager;
    private SimulationManager simulationManager;
    private DE differentialEvolution;

    private static void main(String[] args) {
        new Main();
    }

    public Main() {
        dataManager = new DataManager();
        simulationManager = new SimulationManager();

    }
}
