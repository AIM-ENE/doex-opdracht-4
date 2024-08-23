package bestelsysteem.controller;

import bestelsysteem.model.Ingredient;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.IngredientRepository;
import bestelsysteem.repository.RestaurantRepository;
import bestelsysteem.repository.TafelRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BestelController {
    private final RestaurantRepository restaurantRepository;

    private final TafelRepository tafelRepository;

    private final GerechtRepository gerechtRepository;
    private final IngredientRepository ingredientRepository;


    public BestelController(
                            RestaurantRepository restaurantRepository,
                            TafelRepository tafelRepository,
                            GerechtRepository gerechtRepository,
                            IngredientRepository ingredientRepository) {
        this.restaurantRepository = restaurantRepository;
        this.tafelRepository = tafelRepository;
        this.gerechtRepository = gerechtRepository;
        this.ingredientRepository = ingredientRepository;
    }

    //Opdracht: implementeer de bestelcontroller behorend bij de specificatie
    @GetMapping("/ingredient/{naam}")
    public Ingredient getIngredient(@Parameter(description = "naam van het ingredient")
                                    @PathVariable("naam") String ingredientNaam) {
        // geen automatische conversie mogelijk wegens non-primary key
        return ingredientRepository.findByNaam(ingredientNaam).orElse(null);
    }

}
