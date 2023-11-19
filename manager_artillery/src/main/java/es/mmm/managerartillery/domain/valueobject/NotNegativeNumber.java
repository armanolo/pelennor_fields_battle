package es.mmm.managerartillery.domain.valueobject;

public class NotNegativeNumber {
    private final Integer value;

    public NotNegativeNumber(Integer number){
        if (number == null || number <= 0) {
            throw new IllegalArgumentException("Value must be not negative");
        }
        this.value = number;
    }

    public Integer value() {
        return value;
    }
}
