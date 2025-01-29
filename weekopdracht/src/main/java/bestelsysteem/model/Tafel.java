package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .filter(bestelling -> bestelling.id().equals(bestelnummer))
                .findFirst();
    }

    public int getLaatsteBestelling() {
        //TODO: goede manier vinden om te bepalen wat het bestelnummer is van de laatst geplaatste bestelling...
        return bestellingen.getLast().id();
    }

    public void plaatsBestelling(List<Gerecht> gerechten) {
        List<BestellingGerecht> bestellingGerechten = gerechten.stream().map(gerecht ->
                new BestellingGerecht(AggregateReference.to(gerecht.id()))).collect(Collectors.toList());
        bestellingen.add(new Bestelling(bestellingGerechten));

        Double subTotaal = gerechten.stream().map(Gerecht::prijs).reduce(0.0, Double::sum);
        rekening += subTotaal;
    }

    public double betaalRekening(double bedrag) {
        double rekeningNaBedrag = rekening - bedrag;
        if(rekeningNaBedrag <= 0) {
            rekening = 0;
        } else {
            rekening = rekeningNaBedrag;
        }
        return rekeningNaBedrag;
    }
}
