package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record BestellingGerecht(@Id @JsonIgnore Integer id, AggregateReference<Gerecht, Integer> gerecht, double prijs) {
    public BestellingGerecht(AggregateReference<Gerecht, Integer> gerecht, double prijs) {
        this(null, gerecht, prijs);
    }
}
