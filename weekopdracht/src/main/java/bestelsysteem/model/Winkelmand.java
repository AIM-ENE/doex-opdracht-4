package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;

public record Winkelmand(@Id @JsonIgnore Integer id, @MappedCollection(idColumn = "WINKELMAND", keyColumn = "VOLGNUMMER") List<WinkelmandRegel> gerechten) {
    public void voegGerechtToe(Gerecht gerecht) {
        gerechten.add(new WinkelmandRegel(gerechten.size() + 1, AggregateReference.to(gerecht.id())));
    }
}
