import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DE {
    private double scalingFactor = 0.1, crossoverRate = 0.5;

    private List<Ledger> population;
    private int populationSize = 100;
    private Validator validator;
    private SimulationManager simulationManager;

    public DE(Ledger ledger, Validator validator, SimulationManager simulationManager) {
        population = new ArrayList<>();
        this.validator = validator;
        this.simulationManager = simulationManager;
        for (int i = 0; i < populationSize; i++) {
            population.add(ledger.createVariationClone());
        }
    }

    public void run(int generations) {
        for (Ledger ledger : population) {
            validator.evaluate(ledger, simulationManager);
        }
        List<Pair<Ledger, Ledger>> bothPops;
        for (int i = 0; i < generations; i++) {
            bothPops = createOffspringPopulation();
            for (Pair<Ledger, Ledger> pair : bothPops) {
                validator.evaluate(pair.getValue(), simulationManager);
            }
            selectNewPop(bothPops);
        }
    }

    private void selectNewPop(List<Pair<Ledger, Ledger>> bothPops) {
        population = new ArrayList<>();
        for (Pair<Ledger, Ledger> pair : bothPops) {
            if (pair.getKey().getError() < pair.getValue().getError())
                population.add(pair.getKey());
            else population.add(pair.getValue());
        }
    }

    public List<Pair<Ledger, Ledger>> createOffspringPopulation() {
        List<Pair<Ledger, Ledger>> offspringPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Ledger parent = population.get((new Random().nextInt(population.size())));
            Ledger a = select(parent, null);
            Ledger b = select(parent, a);
            Ledger trialLedger = parent.createTrialVectorLedger(a, b, scalingFactor);
            offspringPopulation.add(new Pair<>(parent, parent.crossOver(trialLedger, crossoverRate)));
        }
        return offspringPopulation;
    }

    private Ledger select(Ledger a, Ledger b) {
        Ledger selected = population.get((new Random()).nextInt(population.size()));
        while (selected.equals(a) || selected.equals(b)) {
            selected = population.get((new Random()).nextInt(population.size()));
        }
        return selected;
    }
}
