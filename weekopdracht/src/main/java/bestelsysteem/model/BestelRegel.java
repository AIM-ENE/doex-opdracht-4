package bestelsysteem.model;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record BestelRegel(AggregateReference<Gerecht, Integer> gerecht, double prijs, int aantal) {
}
