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

    private static final double kleinsteMuntEenheid = 0.05;

    public int getTafelNummer() {
        return tafelNummer;
    }

    /**
     * Haal de openstaande rekening van deze tafel op
     * @return rekening in meervoud van de {@link #kleinsteMuntEenheid}
     */
    public double getRekening() {
        return afrondenOpKleinsteMuntEenheid(rekening);
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

    /**
     * Betaal de openstaande rekening, maar als er geen openstaande rekening is dan krijg je het geld gewoon weer terug
     * @param bedrag het bedrag waarmee de rekening of een gedeelte daarvan afgelost mag worden
     * @return wisselgeld in meervoud van de {@link #kleinsteMuntEenheid}
     */
    public double betaalRekening(double bedrag) {
        rekening -= bedrag;
        double wisselgeld = rekening * -1; //wisselgeld als positief bedrag
        if(wisselgeld > 0) {
            rekening = 0;
        }
        return wisselgeld > kleinsteMuntEenheid ? afrondenOpKleinsteMuntEenheid(wisselgeld) : 0.0;
    }

    private static double afrondenOpKleinsteMuntEenheid(double num) {
        return Math.round(num / kleinsteMuntEenheid) * kleinsteMuntEenheid;
    }
}
