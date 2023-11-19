package es.mmm.managerartillery.domain.valueobject;

public class PositiveNumber {
    private final Integer value;

    public PositiveNumber(Integer number){
        if (number == null || number < 0) {
            throw new IllegalArgumentException("Value must be positive");
        }
        this.value = number;
    }

    public Integer value() {
        return value;
    }
}
