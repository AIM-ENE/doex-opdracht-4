package aimene.doex.bestelling.model;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Embedded;

import java.util.Objects;

public class Bestelregel {

    /* Bestelregel -> Product */
    private final AggregateReference<Product, Integer> product;

    private final int aantal;

    /* Bestelregel -> Geld */
    @Embedded.Empty
    private Geld stukPrijs;

    public Bestelregel(AggregateReference<Product, Integer> product,
                       int aantal,
                       Geld stukPrijs) {

        this.product = product;
        this.aantal = aantal;
        this.stukPrijs = stukPrijs;
    }

    public Geld getRegelPrijs() {
        return stukPrijs.keer(aantal);
    }

    public int getAantal() {
        return aantal;
    }

    public Geld getStukPrijs() {
        return stukPrijs;
    }

    public void setStukPrijs(Geld stukPrijs) {
        this.stukPrijs = stukPrijs;
    }

    public AggregateReference<Product, Integer> getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestelregel that = (Bestelregel) o;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product);
    }

    @Override
    public String toString() {
        return "Bestelregel{" +
                "product=" + product +
                ", aantal=" + aantal +
                ", stukPrijs=" + stukPrijs +
                '}';
    }
}
