package es.mmm.managerartillery.domain.valueobject;

import es.mmm.managerartillery.domain.exception.InvalidTargetCoordinateException;

public class Coordinates {

    private int x = 0;
    private int y = 0;

    public Coordinates(int x, int y) {
        validNegativeValues(x, y);
        this.x = x;
        this.y = y;
    }

    private void validNegativeValues(int x, int y){
        if (x < 0 || y < 0) {
            throw new InvalidTargetCoordinateException("Coordinates must be non-negative");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
