package es.mmm.managerartillery.domain.valueobject;

public class Allies {
    private final PositiveNumber number;

    public Allies(Integer number){
        this.number = new PositiveNumber(number);
    }

    public PositiveNumber getNumber() {
        return number;
    }
}
