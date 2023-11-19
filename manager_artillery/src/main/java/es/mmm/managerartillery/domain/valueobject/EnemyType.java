package es.mmm.managerartillery.domain.valueobject;

import es.mmm.managerartillery.domain.exception.InvalidEnemyTypeException;

import java.util.HashMap;
import java.util.Map;

public enum EnemyType {
    SOLDIER("soldier"),
    MUMAKIL("mumakil");

    private final String value;
    private static final Map<String, EnemyType> valueMap = new HashMap<>();

    static {
        for (EnemyType enemyType : values()) {
            valueMap.put(enemyType.value, enemyType);
        }
    }

    EnemyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnemyType fromValue(String value) {
        EnemyType enemyType = valueMap.get(value);
        if (enemyType != null) {
            return enemyType;
        }
        throw new InvalidEnemyTypeException(
                String.format("Invalid enemy value: %s", (value == null ? "" : value))
        );
    }
}
