package es.mmm.managerartillery.domain;

import es.mmm.managerartillery.domain.valueobject.NotNegativeNumber;
import es.mmm.managerartillery.domain.valueobject.PositiveNumber;

public class CannonResult {
    private final PositiveNumber generation;
    private final NotNegativeNumber casualties;

    public CannonResult(Integer generation, Integer casualties){
        this.generation = new PositiveNumber(generation);
        this.casualties = new NotNegativeNumber(casualties);
    }

    public PositiveNumber getGeneration() {
        return generation;
    }

    public NotNegativeNumber getCasualties() {
        return casualties;
    }
}
