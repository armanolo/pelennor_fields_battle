package es.mmm.managerartillery.domain.valueobject;

public class Enemies {

    private final EnemyType enemyType;
    private final PositiveNumber number;

    public Enemies(String type, Integer number){
        this.enemyType = EnemyType.fromValue(type);
        this.number = new PositiveNumber(number);
    }

    public Integer getNumber() {
        return number.value();
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }
}
