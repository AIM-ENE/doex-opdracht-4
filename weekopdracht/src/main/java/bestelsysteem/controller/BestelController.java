package bestelsysteem.controller;

import bestelsysteem.dto.*;
import bestelsysteem.model.Ingredient;
import bestelsysteem.model.Restaurant;
import bestelsysteem.model.Tafel;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.IngredientRepository;
import bestelsysteem.repository.RestaurantRepository;
import bestelsysteem.repository.TafelRepository;
import bestelsysteem.service.AllergenenService;
import bestelsysteem.service.AllergenenServiceImpl;
import bestelsysteem.service.RestaurantService;
import bestelsysteem.service.TafelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BestelController {
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;

    private final TafelService tafelService;
    private final TafelRepository tafelRepository;

    private final GerechtRepository gerechtRepository;
    private final IngredientRepository ingredientRepository;

    private final AllergenenService allergenenService;

    public BestelController(RestaurantService restaurantService,
                            RestaurantRepository restaurantRepository,
                            TafelService tafelService,
                            TafelRepository tafelRepository,
                            GerechtRepository gerechtRepository,
                            IngredientRepository ingredientRepository) {
        this.restaurantService = restaurantService;
        this.restaurantRepository = restaurantRepository;
        this.tafelService = tafelService;
        this.tafelRepository = tafelRepository;
        this.gerechtRepository = gerechtRepository;
        this.ingredientRepository = ingredientRepository;

        allergenenService = new AllergenenServiceImpl();
    }

    @GetMapping("/{restaurantId}/menu")
    public Menu getMenu(@PathVariable("restaurantId") Restaurant restaurant) {
        return gerechtRepository.findMenu(restaurant.id()).map(menu ->
                restaurantService.filterMenuOpVoorraad(restaurant, menu))
                .orElse(null);
    }

    @GetMapping("/{restaurantId}/menu/gerecht/{gerechtNaam}")
    public Gerecht getGerechtVanMenu(@PathVariable("restaurantId") Restaurant restaurant,
                                     @PathVariable("gerechtNaam") String gerechtNaam) {
        return getMenu(restaurant).gerechten().stream()
                        .filter(gerecht -> gerechtNaam.equals(gerecht.naam()))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/{restaurantId}/menu/filter")
    public Menu getMenuGefilterdOp(@PathVariable("restaurantId") Restaurant restaurant,
                                   @RequestParam("condities") Set<VoedingRestrictie> condities) {
        //TODO: waar zou de afhandeling van deze verantwoordelijkheid moeten liggen, restaurantService?
        return new Menu(getMenu(restaurant).gerechten().stream().filter(gerecht -> {
            for (Gerecht.GerechtIngredient gerechtIngredient : gerecht.ingredienten()) {
                Set<VoedingRestrictie> restrictiesIngredient = allergenenService.getVoedingRestrictie(gerechtIngredient.naam());
                if(condities.stream().anyMatch(restrictiesIngredient::contains)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList()));
    }

    @GetMapping("/{restaurantId}/voorraad")
    public List<Voorraad> getVoorraad(@PathVariable("restaurantId") Restaurant restaurant) {
        return restaurantService.getVoorraad(restaurant);
    }

    @GetMapping("/ingredient/{naam}")
    public Ingredient getIngredient(@PathVariable("naam") String ingredientNaam) {
        // geen automatische conversie mogelijk wegens non-primary key
        return ingredientRepository.findByNaam(ingredientNaam).orElse(null);
    }

    @PostMapping("/{restaurantId}/voorraad/{ingredientId}/{aantal}")
    public void voegIngredientToeAanVoorraad(@PathVariable("restaurantId") Restaurant restaurant,
                                             @PathVariable("ingredientId") int ingredientId,
                                             @PathVariable("aantal") int aantal) {
        restaurant.voegToeAanVoorraad(ingredientId, aantal);
        restaurantRepository.save(restaurant);
    }

    @GetMapping("{restaurantId}/winkelmand")
    public int getNieuweWinkelmand(@PathVariable("restaurantId") Restaurant restaurant) {
        bestelsysteem.model.Winkelmand winkelmand = restaurant.maakWinkelmand();
        restaurant = restaurantRepository.save(restaurant);
        //TODO: kan dit ook nog op een andere manier gematched worden?
        return restaurant.getWinkelmandOnDate(winkelmand.datumTijd())
                .map(bestelsysteem.model.Winkelmand::id).orElse(-1);
    }

    @GetMapping("{restaurantId}/winkelmand/{winkelmandId}")
    public Winkelmand getWinkelmand(@PathVariable("restaurantId") Restaurant restaurant,
                                    // geen automatische conversie naar niet aggregate-roots
                                    @PathVariable("winkelmandId") int winkelmandId) {
        return restaurant.getWinkelmand(winkelmandId)
                .map(this::converteerWinkelmandNaarDTO).orElse(null);
    }

    @PostMapping("{restaurantId}/winkelmand/{winkelmandId}/{gerechtNaam}")
    public void voegGerechtToeAanWinkelmand(@PathVariable("restaurantId") Restaurant restaurant,
                                           // geen automatische conversie naar niet aggregate-root
                                            @PathVariable("winkelmandId") int winkelmandId,
                                            @PathVariable("gerechtNaam") String gerechtNaam) {
        gerechtRepository.findByNaam(gerechtNaam).map(gerecht ->
                restaurantService.plaatsGerechtInWinkelmand(restaurant, winkelmandId, gerecht));
    }

    @PostMapping("{restaurantId}/{tafelnummer}/winkelmand/{winkelmandId}")
    public Integer plaatsBestelling(@PathVariable("restaurantId") Restaurant restaurant,
                                    @PathVariable("tafelnummer") Tafel tafel,
                                    // geen automatische conversie naar niet aggregate-roots
                                    @PathVariable("winkelmandId") int winkelmandId) {
        //TODO: eigenlijk zou dit transactioneel moeten zijn, bestelling slaagt alleen wanneer de winkelmand ook verwijderd is
        //TODO: zou dit naar een application service moeten?
        return restaurant.getWinkelmand(winkelmandId).map(winkelmand -> {
            Integer bestelNummer = tafelService.plaatsBestelling(tafel, getGerechten(winkelmand));
            if(bestelNummer > 0) {
                restaurant.verwijderWinkelmand(winkelmand);
                restaurantRepository.save(restaurant);
            }
            return bestelNummer;
        }).orElse(-1); //TODO: error handling
    }

    @GetMapping("/{tafelnummer}/bestelling/{bestelnummer}")
    public Bestelling getBestelling(@PathVariable("tafelnummer") Tafel tafel,
                                    @PathVariable("bestelnummer") int bestelnummer) {
        return tafelService.getBestelling(tafel, bestelnummer).orElse(null);
    }

    @GetMapping("/{tafelnummer}/rekening")
    public double getRekening(@PathVariable("tafelnummer") Tafel tafel) {
        return tafel.getRekening();
    }

    @PostMapping("/{tafelnummer}/betaal/{contantBedrag}")
    public double betaalRekening(@PathVariable("tafelnummer") Tafel tafel,
                                 @PathVariable("contantBedrag") double contantBedrag) {
        // als er geen openstaande rekening is, dan krijg je het geld gewoon weer terug
        double wisselgeld = tafel.betaalRekening(contantBedrag);
        tafelRepository.save(tafel);
        return wisselgeld;
    }

    private List<bestelsysteem.model.Gerecht> getGerechten(bestelsysteem.model.Winkelmand winkelmand) {
        //TODO: je zou hier heel mooi een custom query voor kunnen schrijven
        return winkelmand.gerechten().stream().map(
                        winkelmandGerecht -> gerechtRepository.findById(
                                Objects.requireNonNull(winkelmandGerecht.gerecht().getId())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    //TODO: zou dit naar een service moeten, bijvoorbeeld restaurantService?
    private Winkelmand converteerWinkelmandNaarDTO(bestelsysteem.model.Winkelmand winkelmand) {
        return new bestelsysteem.dto.Winkelmand(winkelmand.gerechten().stream().map(winkelmandGerecht ->
                        gerechtRepository.findById(Objects.requireNonNull(winkelmandGerecht.gerecht().getId()))
                                .map(gerecht -> new Winkelmand.WinkelmandGerecht(gerecht.id(), gerecht.naam())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList()));
    }
}
