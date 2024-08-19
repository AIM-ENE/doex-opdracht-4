package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record WinkelmandGerecht(@Id @JsonIgnore Integer id, AggregateReference<Gerecht, Integer> gerecht) {

    public WinkelmandGerecht(AggregateReference<Gerecht, Integer> gerecht) {
        this(null, gerecht);
    }
}
