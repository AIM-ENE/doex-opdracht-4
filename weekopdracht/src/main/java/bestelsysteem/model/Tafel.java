package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;
import java.util.Optional;

public class Tafel {
    private @Id @JsonIgnore int id;
    private int tafelNummer;
    private double rekening;
    private AggregateReference<Restaurant, Integer> restaurant;
    //belangrijk om hier geen set te gebruiken, omdat je dan niet twee keer exact dezelfde bestelling kan doen
    private @MappedCollection(idColumn = "TAFEL", keyColumn = "ID") List<Bestelling> bestellingen;

    public int getTafelNummer() {
        return tafelNummer;
    }

    public double getRekening() {
        return rekening;
    }

    public Optional<Bestelling> getBestelling(int bestelnummer) {
        return bestellingen.stream()
                .filter(bestelling -> bestelling.getBestelnummer().equals(bestelnummer))
                .findFirst();
    }

    public int plaatsBestelling(List<Gerecht> gerechten) {
        Bestelling bestelling = new Bestelling(bestellingen.size()+1, gerechten);
        bestellingen.add(bestelling);
        rekening += bestelling.getSubTotaal();
        return bestelling.getBestelnummer();
    }

    public double betaalRekening(double bedrag) {
        rekening -= bedrag;
        double wisselgeld = rekening * -1; //wisselgeld als positief bedrag
        if(wisselgeld > 0) {
            rekening = 0;
        }
        return wisselgeld;
    }
}
