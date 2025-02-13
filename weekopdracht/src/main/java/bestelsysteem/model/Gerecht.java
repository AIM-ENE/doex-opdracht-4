package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.util.Set;

public record Gerecht(@Id @JsonIgnore Integer id, String naam, double prijs, Gang gang, Set<Dosering> doseringVanIngredient) {
};
