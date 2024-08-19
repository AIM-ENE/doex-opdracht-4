package bestelsysteem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.*;

public record Restaurant(@Id Integer id,
                         @Version long versie,
                         Set<Winkelmand> winkelmanden,
                         Set<Voorraad> voorraad) {
    public Optional<Winkelmand> getWinkelmand(int winkelmandId) {
        return winkelmanden.stream().filter(winkelmand -> winkelmand.id().equals(winkelmandId)).findFirst();
    }

    public Optional<Winkelmand> getWinkelmandOnDate(Date datumTijd) {
        return winkelmanden.stream().filter(winkelmand -> winkelmand.datumTijd().equals(datumTijd)).findFirst();
    }

    public Winkelmand maakWinkelmand() {
        bestelsysteem.model.Winkelmand winkelmand = new bestelsysteem.model.Winkelmand(new Date(), new ArrayList<>());
        winkelmanden.add(winkelmand);
        return winkelmand;
    }

    public void verwijderWinkelmand(Winkelmand winkelmand) {
        winkelmanden.remove(winkelmand);
    }

    public Voorraad getVoorraad(int ingredientId) {
        return voorraad().stream().filter(voorraadIngredient ->
                        voorraadIngredient.isVoorraadVan(ingredientId)).findFirst()
                .orElseGet(() -> {
                    Voorraad newVoorraad = new Voorraad(AggregateReference.to(ingredientId), 0);
                    this.voorraad.add(newVoorraad);
                    return newVoorraad;
                });
    }

    public void voegToeAanVoorraad(int ingredientId, int aantal) {
        Voorraad voorraad = getVoorraad(ingredientId);
        voorraad.voegToeAanVoorraad(aantal);
    }

    public boolean reduceerVoorraad(Gerecht gerecht) {
        List<Boolean> results = gerecht.doseringVanIngredient().stream()
                .map(doseringVanIngredient -> getVoorraad(doseringVanIngredient.ingredient().getId())
                        .reduceerVoorraad(doseringVanIngredient))
                .toList();
        return !results.isEmpty() && results.stream().allMatch(aBoolean -> aBoolean);
    }
}
