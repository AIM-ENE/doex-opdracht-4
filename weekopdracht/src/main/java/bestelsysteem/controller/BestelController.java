package bestelsysteem.controller;

import bestelsysteem.openapi.api.BestelControllerApi;
import bestelsysteem.openapi.model.*;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.IngredientRepository;
import bestelsysteem.repository.RestaurantRepository;
import bestelsysteem.repository.TafelRepository;
import bestelsysteem.service.TafelService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BestelController implements BestelControllerApi {
    private final RestaurantRepository restaurantRepository;

    private final TafelService tafelService;
    private final TafelRepository tafelRepository;

    private final GerechtRepository gerechtRepository;
    private final IngredientRepository ingredientRepository;


    public BestelController(
                            RestaurantRepository restaurantRepository,
                            TafelService tafelService,
                            TafelRepository tafelRepository,
                            GerechtRepository gerechtRepository,
                            IngredientRepository ingredientRepository) {
        this.restaurantRepository = restaurantRepository;
        this.tafelService = tafelService;
        this.tafelRepository = tafelRepository;
        this.gerechtRepository = gerechtRepository;
        this.ingredientRepository = ingredientRepository;
    }

    //Opdracht: implementeer de bestelcontroller behorend bij de specificatie

    @Override
    public Double betaalRekening(Integer tafelnummer, Double contantBedrag) {
        return tafelRepository.findById(tafelnummer)
                .map(tafel -> {
                    // als er geen openstaande rekening is, dan krijg je het geld gewoon weer terug
                    double wisselgeld = tafel.betaalRekening(contantBedrag);
                    tafelRepository.save(tafel);
                    return wisselgeld;
                }).orElse(0.0);
    }

    @Override
    public Bestelling getBestelling(Integer tafelnummer, Integer bestelnummer) {
        return tafelRepository.findById(tafelnummer)
                .flatMap(tafel -> tafelService.getBestelling(tafel, bestelnummer))
                .orElse(null);
    }

    @Override
    public Gerecht getGerechtVanMenu(Integer restaurantId, String gerechtNaam) {
        return BestelControllerApi.super.getGerechtVanMenu(restaurantId, gerechtNaam);
    }

    @Override
    public Ingredient getIngredient(String naam) {
        // geen automatische conversie mogelijk wegens non-primary key
        return ingredientRepository.findByNaam(naam).orElse(null);
    }

    @Override
    public Menu getMenu(Integer restaurantId) {
        return BestelControllerApi.super.getMenu(restaurantId);
    }

    @Override
    public Menu getMenuGefilterdOp(Integer restaurantId, String condities) {
        return BestelControllerApi.super.getMenuGefilterdOp(restaurantId, condities);
    }

    @Override
    public Integer getNieuweWinkelmand(Integer restaurantId) {
        return BestelControllerApi.super.getNieuweWinkelmand(restaurantId);
    }

    @Override
    public Double getRekening(Integer tafelnummer) {
        return BestelControllerApi.super.getRekening(tafelnummer);
    }

    @Override
    public List<Voorraad> getVoorraad(Integer restaurantId) {
        return BestelControllerApi.super.getVoorraad(restaurantId);
    }

    @Override
    public Winkelmand getWinkelmand(Integer restaurantId, Integer winkelmandId) {
        return BestelControllerApi.super.getWinkelmand(restaurantId, winkelmandId);
    }

    @Override
    public Integer plaatsBestelling(Integer restaurantId, Integer tafelnummer, Integer winkelmandId) {
        BestelControllerApi.super.plaatsBestelling(restaurantId, tafelnummer, winkelmandId);
        return restaurantId;
    }

    @Override
    public void voegGerechtToeAanWinkelmand(Integer restaurantId, Integer winkelmandId, String gerechtNaam) {
        BestelControllerApi.super.voegGerechtToeAanWinkelmand(restaurantId, winkelmandId, gerechtNaam);
    }

    @Override
    public void voegIngredientToeAanVoorraad(Integer restaurantId, Integer ingredientId, Integer aantal) {
        BestelControllerApi.super.voegIngredientToeAanVoorraad(restaurantId, ingredientId, aantal);
    }
}
