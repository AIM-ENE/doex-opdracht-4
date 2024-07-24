package aimene.doex.bestelling.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Embedded;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Bestelling {

    @Id @JsonIgnore
    private Integer id;

    @Version
    private Integer versie;

    private Status status;

    /* Bestelling -> Bestelregel */
    private Set<Bestelregel> bestelregels;

    /* Bestelling -> Geld */
    @Embedded.Empty
    private Geld totaalPrijs;

    public Bestelling() {
        status = Status.CONCEPT;
        bestelregels = new HashSet<>();
    }

    public void plaatsBestelling() {
        status = Status.GEPLAATST;
    }

    private Geld bepaalTotaalPrijs() {
        return bestelregels.stream()
                .map(Bestelregel::getRegelPrijs)
                .reduce(new Geld(0, Valuta.EUR), Geld::plus);
    }

    public void voegProductToe(AggregateReference<Product, Integer> product, int aantal, Geld prijs) {
        Bestelregel nieuweBestelregel = new Bestelregel(product, aantal, prijs);

        boolean productToegevoegd = bestelregels.add(nieuweBestelregel);

        if (!productToegevoegd) throw new IllegalArgumentException("Product bestaat al in deze bestelling");

        totaalPrijs = bepaalTotaalPrijs();
    }

    public void veranderStukPrijs(AggregateReference<Product, Integer> product, Geld nieuwePrijs) {
        if (status == Status.CONCEPT) {
            bestelregels.stream()
                    .filter(bestelregel -> bestelregel.getProduct().equals(product))
                    .forEach(bestelregel -> bestelregel.setStukPrijs(nieuwePrijs));

            totaalPrijs = bepaalTotaalPrijs();
        }
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Geld getTotaalPrijs() {
        return totaalPrijs;
    }

    public Set<Bestelregel> getBestelregels() {
        return bestelregels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestelling that = (Bestelling) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bestelling{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", bestelregels=" + bestelregels +
                '}';
    }
}
