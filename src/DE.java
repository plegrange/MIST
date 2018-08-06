import java.util.ArrayList;
import java.util.List;

public class DE {

    private List<Ledger> population;
    private int populationSize = 100;
    private Validator validator;

    public DE(Ledger ledger) {
        population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(ledger.createVariationClone());
        }
    }

    public void evolve(int generations){

    }
}
