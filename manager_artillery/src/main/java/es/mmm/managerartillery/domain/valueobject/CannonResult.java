package es.mmm.managerartillery.domain.valueobject;


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
