package bestelsysteem.service;

import bestelsysteem.model.Gerecht;
import bestelsysteem.model.Restaurant;
import bestelsysteem.model.Winkelmand;
import bestelsysteem.openapi.model.GerechtIngredient;
import bestelsysteem.openapi.model.Ingredient;
import bestelsysteem.openapi.model.Menu;
import bestelsysteem.repository.IngredientRepository;
import bestelsysteem.repository.RestaurantRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final IngredientRepository ingredientRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, IngredientRepository ingredientRepository) {
        this.restaurantRepository = restaurantRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Menu filterMenuOpVoorraad(Restaurant restaurant, Menu menu) {
        List<bestelsysteem.openapi.model.@Valid Gerecht> gerechten = menu.getGerechten().stream().filter(gerecht -> {
            for (GerechtIngredient gerechtIngredient : gerecht.getIngredienten()) {
                return ingredientRepository.findByNaam(gerechtIngredient.getNaam()).map(ingredient -> {
                    if (!restaurant.getVoorraad(ingredient.getId()).isVoldoendeVoorraad(gerechtIngredient.getHoeveelheid())) {
                        return false;
                    }
                    return true;
                }).orElse(false);
            }
            return true;
        }).toList();
        Menu filteredMenu = new Menu();
        filteredMenu.setGerechten(gerechten);
        return filteredMenu;
    }

    public List<bestelsysteem.dto.Voorraad> getVoorraad(Restaurant restaurant) {
        return restaurant.voorraad().stream().map(voorraad -> new bestelsysteem.dto.Voorraad(
                        ingredientRepository.findById(Objects.requireNonNull(voorraad.getIngredientId()))
                                .map(Ingredient::getNaam)
                                .orElse("unknown ingredient"),
                        voorraad.getAantal())).collect(Collectors.toList());
    }

    public int plaatsGerechtInWinkelmand(Restaurant restaurant, int winkelmandId, Gerecht gerecht) {
        int result = -1;
        Optional<Winkelmand> winkelmandOptional = restaurant.getWinkelmand(winkelmandId);
        if(winkelmandOptional.isPresent() && restaurant.reduceerVoorraad(gerecht)) {
            Winkelmand winkelmand = winkelmandOptional.get();
            winkelmand.voegGerechtToe(gerecht);
            restaurant = restaurantRepository.save(restaurant);
            return restaurant.winkelmanden().stream()
                    //TODO: is dit de beste manier om te matchen..?
                    .filter(winkelmand1 -> winkelmand1.datumTijd().equals(winkelmand.datumTijd()))
                    .map(Winkelmand::id)
                    .findFirst()
                    .orElse(-1);
        }
        return result;
    }
}
