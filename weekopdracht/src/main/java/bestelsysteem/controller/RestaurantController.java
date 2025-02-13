package bestelsysteem.controller;

import bestelsysteem.dto.*;
import bestelsysteem.model.Tafel;
import bestelsysteem.repository.GerechtRepository;
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
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;

    private final TafelService tafelService;
    private final TafelRepository tafelRepository;

    private final GerechtRepository gerechtRepository;

    private final AllergenenService allergenenService;

    public RestaurantController(RestaurantService restaurantService,
                                RestaurantRepository restaurantRepository,
                                TafelService tafelService,
                                TafelRepository tafelRepository,
                                GerechtRepository gerechtRepository) {
        this.restaurantService = restaurantService;
        this.restaurantRepository = restaurantRepository;
        this.tafelService = tafelService;
        this.tafelRepository = tafelRepository;
        this.gerechtRepository = gerechtRepository;

        allergenenService = new AllergenenServiceImpl();
    }

    @GetMapping("restaurant/{restaurantId}/menu")
    public Menu getMenu(@PathVariable("restaurantId") int restaurantId) {
        return gerechtRepository.findMenu(restaurantId).orElse(null);
    }

    @GetMapping("/restaurant/{restaurantId}/menu/filter")
    public Menu getMenuGefilterdOp(@PathVariable("restaurantId") int restaurantId,
                                   @RequestParam("condities") Set<VoedingRestrictie> condities) {
        Menu menu = getMenu(restaurantId);
        /* PLACEHOLDER: implementeer het filteren van de gerechten in het menu op basis van `allergenenService` */
        return menu;
    }

    @PutMapping("/restaurant/{restaurantId}/winkelmand")
    public int getNieuweWinkelmand(@PathVariable("restaurantId") int restaurantId) {
        return restaurantRepository.findById(restaurantId).map(restaurant -> {
            bestelsysteem.model.Winkelmand winkelmand = restaurant.maakWinkelmand();
            restaurantRepository.save(restaurant);
            return winkelmand.id();
        }).orElse(-1);
    }

    @GetMapping("/restaurant/{restaurantId}/winkelmand/{winkelmandId}")
    public Winkelmand getWinkelmand(@PathVariable("restaurantId") int restaurantId,
                                    @PathVariable("winkelmandId") int winkelmandId) {
        return restaurantRepository.findById(restaurantId).flatMap(restaurant ->
                restaurant.getWinkelmand(winkelmandId).map(this::converteerWinkelmandNaarDTO)).orElse(null);
    }

    @PostMapping("/restaurant/{restaurantId}/winkelmand/{winkelmandId}")
    public void voegGerechtenToeAanWinkelmand(@PathVariable("restaurantId") int restaurantId,
                                                @PathVariable("winkelmandId") int winkelmandId,
                                                @RequestBody List<String> gerechtNamen) {
        restaurantService.plaatsGerechtInWinkelmand(restaurantId, winkelmandId, gerechtNamen);
    }

    @PostMapping("/restaurant/{restaurantId}/bestelling")
    public Integer plaatsBestelling(@PathVariable("restaurantId") int restaurantId,
                                    @RequestBody TafelBestelling tafelBestelling) {
        /* PLACEHOLDER: implementeer de operatie plaatsBestelling */
        return -1;
    }

    @GetMapping("/restaurant/{restaurantId}/tafel/{tafelnummer}/bestelling/{bestelnummer}")
    public Bestelling getBestelling(@PathVariable("restaurantId") int restaurantId,
                                    @PathVariable("tafelnummer") int tafelNummer,
                                    @PathVariable("bestelnummer") int bestelnummer) {
        return findTafelByRestaurant(restaurantId, tafelNummer)
                .flatMap(tafel -> tafelService.getBestelling(tafel, bestelnummer)).orElse(null);
    }

    @GetMapping("/restaurant/{restaurantId}/tafel/{tafelnummer}/rekening")
    public double getRekening(@PathVariable("restaurantId") int restaurantId,
                              @PathVariable("tafelnummer") int tafelNummer) {
        return findTafelByRestaurant(restaurantId, tafelNummer).map(Tafel::getRekening).orElse(0.0);
    }

    @PostMapping("/restaurant/{restaurantId}/tafel/{tafelnummer}/rekening")
    public double betaalRekening(@PathVariable("restaurantId") int restaurantId,
                                 @PathVariable("tafelnummer") int tafelNummer,
                                 @RequestBody Betaling betaling) {
        return findTafelByRestaurant(restaurantId, tafelNummer).map(tafel -> {
            // als er geen openstaande rekening is, dan krijg je het geld gewoon weer terug
            double wisselgeld = tafel.betaalRekening(betaling.bedrag());
            tafelRepository.save(tafel);
            return wisselgeld > 0.05 ? wisselgeld : 0.0;
        }).orElse(0.0);
    }

    private Optional<Tafel> findTafelByRestaurant(int restaurantId, int tafelNummer) {
        /* PLACEHOLDER: implementeer het vinden van de juiste Tafel */
        return Optional.empty();
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
