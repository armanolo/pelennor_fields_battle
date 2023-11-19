package es.mmm.managerartillery.domain.valueobject;

public class CannonResponse {

    private final Coordinates target;
    private final NotNegativeNumber enemies;
    private final PositiveNumber generation;

    public CannonResponse(int generation, int x, int y, int enemies){
        this.generation = new PositiveNumber(generation);
        this.target = new Coordinates(x,y);
        this.enemies = new NotNegativeNumber(enemies);
    }

    public Coordinates getTarget() {
        return target;
    }

    public int getEnemies() {
        return enemies.value();
    }

    public int getGeneration() {
        return generation.value();
    }


}
