package bestelsysteem.model;

import org.springframework.data.annotation.Version;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.Objects;

public class Voorraad {
    private AggregateReference<Ingredient,Integer> ingredient;
    private int aantal;

    public Voorraad() {
        // kennelijk nodig voor Spring Data JDBC...
    }

    public Voorraad(AggregateReference<Ingredient,Integer> ingredient, int aantal) {
        this.ingredient = ingredient;
        this.aantal = aantal;
    }

    public Integer getIngredientId() {
        return ingredient.getId();
    }

    public int getAantal() {
        return aantal;
    }

    public boolean isVoorraadVan(int ingredientId) {
        return Objects.equals(ingredientId, ingredient.getId());
    }

    public void voegToeAanVoorraad(int aantal) {
        this.aantal += aantal;
    }

    public boolean isVoldoendeVoorraad(int hoeveelheid) {
        return aantal >= hoeveelheid;
    }

    public boolean reduceerVoorraad(Dosering dosering) {
        if(isVoldoendeVoorraad(dosering.hoeveelheid())) {
            aantal = aantal - dosering.hoeveelheid();
            return true;
        }
        return false;
    }
}
