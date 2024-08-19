package bestelsysteem.model;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Dosering(
        AggregateReference<Ingredient,Integer> ingredient,
        Integer hoeveelheid) {
}
