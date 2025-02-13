package bestelsysteem.dto;

import bestelsysteem.model.Gang;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.util.List;

public record Gerecht(@Id @JsonIgnore Integer id, String naam, double prijs, Gang gang, List<GerechtIngredient> ingredienten) {
    public record GerechtIngredient(String naam, Integer hoeveelheid) {
    }
}
