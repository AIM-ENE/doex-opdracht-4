package aimene.doex.bestelling.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;

import java.util.Objects;

public class Product {

    @Id @JsonIgnore
    private Integer id;

    private String naam;

    @Version
    private Integer versie;

    @Embedded.Nullable
    private Geld prijs;

    public Product(String naam, Geld prijs) {
        this.naam = naam;
        this.prijs = prijs;
    }

    public void veranderPrijs(Geld nieuwePrijs) {
        prijs = nieuwePrijs;
    }

    public Integer getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setPrijs(Geld prijs) {
        this.prijs = prijs;
    }

    public Geld getPrijs() {
        return prijs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", prijs=" + prijs +
                '}';
    }
}
