package bestelsysteem.model;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record WinkelmandRegel(Integer volgnummer, AggregateReference<Gerecht, Integer> gerecht) {
}
