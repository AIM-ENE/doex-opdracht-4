package bestelsysteem.controller;

import bestelsysteem.dto.VoedingRestrictie;
import bestelsysteem.model.Tafel;
import bestelsysteem.openapi.api.BestelControllerApi;
import bestelsysteem.openapi.model.*;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.IngredientRepository;
import bestelsysteem.repository.RestaurantRepository;
import bestelsysteem.repository.TafelRepository;
import bestelsysteem.service.AllergenenService;
import bestelsysteem.service.AllergenenServiceImpl;
import bestelsysteem.service.RestaurantService;
import bestelsysteem.service.TafelService;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class BestelController implements BestelControllerApi {
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
        return getMenu(restaurantId).getGerechten().stream()
                .filter(gerecht -> gerechtNaam.equals(gerecht.getNaam()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Ingredient getIngredient(String naam) {
        // geen automatische conversie mogelijk wegens non-primary key
        return ingredientRepository.findByNaam(naam).orElse(null);
    }

    @Override
    public Menu getMenu(Integer restaurantId) {
        return restaurantRepository.findById(restaurantId).flatMap(restaurant ->
                gerechtRepository.findMenu(restaurant.id())
                .map(menu -> restaurantService.filterMenuOpVoorraad(restaurant, menu))).orElse(null);
    }

    @Override
    public Menu getMenuGefilterdOp(Integer restaurantId, List<String> condities) {
        //TODO: waar zou de afhandeling van deze verantwoordelijkheid moeten liggen, restaurantService?
        Menu menu = new Menu();
        menu.setGerechten(getMenu(restaurantId).getGerechten().stream().filter(gerecht -> {
            for (GerechtIngredient gerechtIngredient : gerecht.getIngredienten()) {
                Set<VoedingRestrictie> restrictiesIngredient = allergenenService.getVoedingRestrictie(
                        gerechtIngredient.getNaam());
                Set<String> restricties = restrictiesIngredient.stream().map(Enum::name).collect(Collectors.toSet());
                if(condities.stream().anyMatch(restricties::contains)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList()));
        return menu;
    }

    @Override
    public Integer getNieuweWinkelmand(Integer restaurantId) {
        return restaurantRepository.findById(restaurantId).map(restaurant -> {
            bestelsysteem.model.Winkelmand winkelmand = restaurant.maakWinkelmand();
            restaurant = restaurantRepository.save(restaurant);
            //TODO: kan dit ook nog op een andere manier gematched worden?
            return restaurant.getWinkelmandOnDate(winkelmand.datumTijd())
                    .map(bestelsysteem.model.Winkelmand::id).orElse(-1);
        }).orElse(null);
    }

    @Override
    public Double getRekening(Integer tafelnummer) {
        return tafelRepository.findById(tafelnummer).map(Tafel::getRekening).orElse(0.0);
    }

    @Override
    public List<Voorraad> getVoorraad(Integer restaurantId) {
        // concrete cast: https://stackoverflow.com/questions/5082044/most-efficient-way-to-cast-listsubclass-to-listbaseclass
        return new ArrayList<Voorraad>(restaurantRepository.findById(restaurantId)
                .map(restaurantService::getVoorraad)
                .orElse(new ArrayList<>()));
    }

    @Override
    public Winkelmand getWinkelmand(Integer restaurantId, Integer winkelmandId) {
        return restaurantRepository.findById(restaurantId).flatMap(restaurant ->
                restaurant.getWinkelmand(winkelmandId)
                        .map(this::converteerWinkelmandNaarDTO))
                .orElse(null);
    }

    @Override
    public Integer plaatsBestelling(Integer restaurantId, Integer tafelnummer, Integer winkelmandId) {
        //TODO: eigenlijk zou dit transactioneel moeten zijn, bestelling slaagt alleen wanneer de winkelmand ook verwijderd is
        //TODO: zou dit naar een application service moeten?
        return restaurantRepository.findById(restaurantId).map(restaurant -> {
            return restaurant.getWinkelmand(winkelmandId).map(winkelmand -> {
                Optional<Tafel> tafelOptional = tafelRepository.findById(tafelnummer);
                return tafelOptional.map(tafel -> {
                    Integer bestelNummer = tafelService.plaatsBestelling(tafel, getGerechten(winkelmand));
                    if (bestelNummer > 0) {
                        restaurant.verwijderWinkelmand(winkelmand);
                        restaurantRepository.save(restaurant);
                    }
                    return bestelNummer;
                }).orElse(-1); //TODO: error handling
            }).orElse(-1); //TODO: error handling
        }).orElse(-1); //TODO: error handling
    }

    @Override
    public void voegGerechtToeAanWinkelmand(Integer restaurantId, Integer winkelmandId, String gerechtNaam) {
        restaurantRepository.findById(restaurantId).flatMap(restaurant ->
            gerechtRepository.findByNaam(gerechtNaam).map(gerecht ->
                    restaurantService.plaatsGerechtInWinkelmand(restaurant, winkelmandId, gerecht)));
    }

    @Override
    public void voegIngredientToeAanVoorraad(Integer restaurantId, Integer ingredientId, Integer aantal) {
        restaurantRepository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.voegToeAanVoorraad(ingredientId, aantal);
            restaurantRepository.save(restaurant);
        });
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
                                .map(gerecht -> {
                                    WinkelmandGerecht winkelmandGerechtNew = new WinkelmandGerecht();
                                    winkelmandGerechtNew.setId(gerecht.id());
                                    winkelmandGerechtNew.setNaam(gerecht.naam());
                                    return winkelmandGerechtNew;
                                }))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList()));
    }
}
