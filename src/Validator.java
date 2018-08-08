import javafx.util.Pair;

import java.util.List;

public class Validator {
    private Logbook validationLogBook;

    public void validate(Logbook simulatedLogBook) {
        simulatedLogBook.compareLogbook(validationLogBook);
    }

    public Ledger evaluate(Ledger initialStateLedger, SimulationManager simulationManager) {
        validate(simulationManager.runSimulation(initialStateLedger));
        return initialStateLedger;
    }

    public Logbook getValidationLogBook() {
        return validationLogBook;
    }

    public void setValidationLogBook(Logbook validationLogBook) {
        this.validationLogBook = validationLogBook;
    }
}