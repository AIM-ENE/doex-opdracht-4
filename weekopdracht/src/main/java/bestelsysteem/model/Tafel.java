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
                .filter(bestelling -> bestelling.getId().equals(bestelnummer))
                .findFirst();
    }

    public Optional<Bestelling> getLaatsteBestelling() {
        return Optional.ofNullable(bestellingen.getLast());
    }

    public void plaatsBestelling(List<Gerecht> gerechten) {
        List<BestellingGerecht> bestellingGerechten = gerechten.stream().map(gerecht ->
                new BestellingGerecht(AggregateReference.to(gerecht.id()), gerecht.prijs())).collect(Collectors.toList());
        double subTotaal = bestellingGerechten.stream().map(BestellingGerecht::prijs).reduce(0.0, Double::sum);
        bestellingen.add(new Bestelling(bestellingGerechten, subTotaal));
        rekening += subTotaal;
    }

    public double betaalRekening(double bedrag) {
        double restBedrag = bedrag;
        for(Bestelling bestelling : bestellingen) {
            if(restBedrag > 0) {
                if(bestelling.getStatus() == BestellingStatus.OPEN) {
                    double subRekening = bestelling.getRekening();
                    if (restBedrag >= subRekening) {
                        bestelling.setPaid();
                        restBedrag -= subRekening;
                        rekening -= subRekening;
                    }
                }
            } else {
                break;
            }
        }
        return restBedrag;
    }
}
