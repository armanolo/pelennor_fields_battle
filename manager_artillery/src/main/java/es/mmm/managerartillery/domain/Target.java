package es.mmm.managerartillery.domain;

import es.mmm.managerartillery.domain.valueobject.Allies;
import es.mmm.managerartillery.domain.valueobject.Coordinates;
import es.mmm.managerartillery.domain.valueobject.Enemies;
import es.mmm.managerartillery.domain.valueobject.EnemyType;

import java.util.Optional;

public class Target {
    private final Coordinates coordinates;
    private final Enemies enemies;
    private final Optional<Allies> alliesOptional;

    public Target(Coordinates coordinates, Enemies enemies, Optional<Allies> allies){
        this.coordinates = coordinates;
        this.enemies = enemies;
        this.alliesOptional = allies;
    }

    public double calculateDistance(){
        double deltaX = coordinates.getX();
        double deltaY = coordinates.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Enemies getEnemies() {
        return enemies;
    }

    public Optional<Allies> getAlliesOptional() {
        return alliesOptional;
    }

    public boolean hasAllies() {
        return alliesOptional.isPresent();
    }

    public boolean hasMech() {
        return EnemyType.MUMAKIL == getEnemies().getEnemyType();
    }

    @Override
    public String toString() {
        return "distance= "+ calculateDistance() +
                "coordinates= " + coordinates.getX() + " : "+ coordinates.getY() +
                ", enemies=" + enemies.getNumber() + " , " +enemies.getEnemyType().name() +
                ", alliesOptional=" + alliesOptional.isPresent();
    }
}
