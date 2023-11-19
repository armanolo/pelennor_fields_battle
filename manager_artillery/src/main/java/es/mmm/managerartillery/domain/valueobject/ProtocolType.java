package es.mmm.managerartillery.domain.valueobject;

import es.mmm.managerartillery.domain.exception.InvalidProtocolTypeException;

import java.util.HashMap;
import java.util.Map;

public enum ProtocolType {
    CLOSEST_ENEMIES("closest-enemies"),
    FURTHEST_ENEMIES("furthest-enemies"),
    ASSIST_ALLIES("assist-allies"),
    AVOID_CROSSFIRE("avoid-crossfire"),
    PRIORITIZE_MUMAKIL("prioritize-mumakil"),
    AVOID_MUMAKIL("avoid-mumakil");

    private final String value;
    private static final Map<String, ProtocolType> valueMap = new HashMap<>();

    static {
        for (ProtocolType cardinal : values()) {
            valueMap.put(cardinal.value, cardinal);
        }
    }


    ProtocolType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProtocolType fromValue(String value) {
        ProtocolType protocolType = valueMap.get(value);
        if (protocolType != null) {
            return protocolType;
        }
        throw new InvalidProtocolTypeException(
                String.format("Invalid protocol value: %s", (value == null ? "" : value))
        );
    }
}
