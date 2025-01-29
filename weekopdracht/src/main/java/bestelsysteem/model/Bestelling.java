package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;

public record Bestelling(@Id @JsonIgnore Integer id,
                         Integer status,
                         @MappedCollection(idColumn = "BESTELLING", keyColumn = "ID") List<BestellingGerecht> gerechten) {
    public Bestelling(List<BestellingGerecht> bestellingen) {
        this(null, 1, bestellingen); /* 1 betekent nieuwe bestelling */
    }
}
