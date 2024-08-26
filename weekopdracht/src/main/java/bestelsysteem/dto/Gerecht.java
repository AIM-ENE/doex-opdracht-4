package bestelsysteem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.util.List;

public record Gerecht(@Id @JsonIgnore Integer id, String naam, double prijs, List<GerechtIngredient> ingredienten) {
    public record GerechtIngredient(String naam, Integer hoeveelheid) {
    }
}
