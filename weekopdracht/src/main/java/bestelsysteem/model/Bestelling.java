package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Collections;
import java.util.List;

public class Bestelling {
    private @Id @JsonIgnore Integer id;
    private BestellingStatus status;
    private Double rekening;
    private @MappedCollection(idColumn = "BESTELLING", keyColumn = "ID") List<BestellingGerecht> gerechten;

    // vereist voor Spring Data JDBC om bestaande bestellingen te kunnen reconstrueren vanuit de repository interface
    // TODO: is er ook een manier zonder NO_CONSTRUCTOR?
    protected Bestelling() {
    }

    public Bestelling(Integer id, BestellingStatus status, List<BestellingGerecht> gerechten, double rekening) {
        this.id = id;
        this.status = status;
        this.gerechten = gerechten;
        this.rekening = rekening;
    }

    public Bestelling(List<BestellingGerecht> bestellingen, double rekening) {
        this(null, BestellingStatus.OPEN, bestellingen, rekening); /* 1 betekent nieuwe bestelling */
    }

    public Integer getId() {
        return id;
    }

    public BestellingStatus getStatus() {
        return status;
    }

    public double getRekening() {
        return rekening;
    }

    public void setPaid() {
        this.status = BestellingStatus.PAID;
    }

    public List<BestellingGerecht> getGerechten() {
        return Collections.unmodifiableList(gerechten);
    }
}
