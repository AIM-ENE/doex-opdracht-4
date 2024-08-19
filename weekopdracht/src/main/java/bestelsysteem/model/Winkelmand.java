package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Date;
import java.util.List;

public record Winkelmand(@Id @JsonIgnore Integer id, Date datumTijd, @MappedCollection(idColumn = "WINKELMAND", keyColumn = "ID") List<WinkelmandGerecht> gerechten) {
    public Winkelmand(Date datumTijd, List<WinkelmandGerecht> gerechten) {
        this(null, datumTijd, gerechten);
    }

    public void voegGerechtToe(Gerecht gerecht) {
        gerechten.add(new WinkelmandGerecht(AggregateReference.to(gerecht.id())));
    }
}
