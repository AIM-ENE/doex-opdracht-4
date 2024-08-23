package bestelsysteem.dto;

import bestelsysteem.openapi.model.GerechtIngredient;

import java.util.List;

public class Gerecht extends bestelsysteem.openapi.model.Gerecht {
    public Gerecht(Integer id, String naam, double prijs, List<GerechtIngredient> ingredienten) {
        this.setId(id);
        this.setNaam(naam);
        this.setPrijs(prijs);
        this.setIngredienten(ingredienten);
    }
}
