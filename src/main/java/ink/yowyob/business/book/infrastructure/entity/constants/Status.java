package ink.yowyob.business.book.infrastructure.entity.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;
import java.util.HashMap;

public enum Status {
    ENTREPRISE_INDIVIDUELLE,
    SOCIETE_A_RESPONSABILITE_LIMITEE,
    SOCIETE_ANONYME,
    SOCIETE_EN_NOM_COLLECTIF,
    SOCIETE_EN_COMMANDITE_SIMPLE,
    SOCIETE_COOPERATIVE,
    GROUPEMENT_D_INTERET_ECONOMIQUE,
    SOCIETE_CIVILE,
    SUCCURSALE,
    SOCIETE_UNIPERSONNELLE_A_RESPONSABILITE_LIMITEE,
    ACTIVE;


    private static final Map<String, Status> FORMAT_MAPPING = new HashMap<>();

    static {
        for (Status status : values()) {
            FORMAT_MAPPING.put(status.name(), status);
        }
    }

    @JsonCreator
    public static Status fromValue(String value) {
        if (value == null) {
            return null;
        }
        String sanitizedValue = value.trim().replace(",", "");
        return FORMAT_MAPPING.getOrDefault(sanitizedValue, null);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
