package bestelsysteem.controller;

import bestelsysteem.dto.*;
import bestelsysteem.model.Restaurant;
import bestelsysteem.model.Tafel;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.RestaurantRepository;
import bestelsysteem.repository.TafelRepository;
import bestelsysteem.service.AllergenenService;
import bestelsysteem.service.AllergenenServiceImpl;
import bestelsysteem.service.RestaurantService;
import bestelsysteem.service.TafelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    }

    @GetMapping("restaurant/{restaurantId}/menu")
    public Menu getMenu(@PathVariable("restaurantId") int restaurantId) {
        return gerechtRepository.findMenu(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "menu voor restaurant niet gevonden"));
    }

    @GetMapping("/restaurant/{restaurantId}/menu/filter")
    public Menu getMenuGefilterdOp(@PathVariable("restaurantId") int restaurantId,
                                   @RequestParam("condities") Set<VoedingRestrictie> condities) {
        /* 3. PLACEHOLDER: implementeer het filteren van de gerechten in het menu op basis van `allergenenService` */
        return null;
    }

    @PutMapping("/restaurant/{restaurantId}/winkelmand")
    public int getNieuweWinkelmand(@PathVariable("restaurantId") int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "restaurant niet gevonden"));

        bestelsysteem.model.Winkelmand winkelmand = restaurant.maakWinkelmand();
        restaurantRepository.save(restaurant);
        return winkelmand.id();
    }

    @GetMapping("/restaurant/{restaurantId}/winkelmand/{winkelmandId}")
    public Winkelmand getWinkelmand(@PathVariable("restaurantId") int restaurantId,
                                    @PathVariable("winkelmandId") int winkelmandId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "restaurant niet gevonden"));
        bestelsysteem.model.Winkelmand winkelmand = restaurant.getWinkelmand(winkelmandId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "winkelmand niet gevonden"));

        return converteerWinkelmandNaarDTO(winkelmand);
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
        /* 2. PLACEHOLDER: implementeer de operatie plaatsBestelling */
        return -1;
    }

    @GetMapping("/restaurant/{restaurantId}/tafel/{tafelnummer}/bestelling/{bestelnummer}")
    public Bestelling getBestelling(@PathVariable("restaurantId") int restaurantId,
                                    @PathVariable("tafelnummer") int tafelNummer,
                                    @PathVariable("bestelnummer") int bestelnummer) {
        return tafelService.getBestelling(findTafelByRestaurant(restaurantId, tafelNummer), bestelnummer);
    }

    @GetMapping("/restaurant/{restaurantId}/tafel/{tafelnummer}/rekening")
    public double getRekening(@PathVariable("restaurantId") int restaurantId,
                              @PathVariable("tafelnummer") int tafelNummer) {
        return findTafelByRestaurant(restaurantId, tafelNummer).getRekening();
    }

    @PostMapping("/restaurant/{restaurantId}/tafel/{tafelnummer}/rekening")
    public double betaalRekening(@PathVariable("restaurantId") int restaurantId,
                                 @PathVariable("tafelnummer") int tafelNummer,
                                 @RequestBody Betaling betaling) {
        Tafel tafelByRestaurant = findTafelByRestaurant(restaurantId, tafelNummer);
        double wisselgeld = tafelByRestaurant.betaalRekening(betaling.bedrag());
        tafelRepository.save(tafelByRestaurant);
        return wisselgeld;
    }

    private Tafel findTafelByRestaurant(int restaurantId, int tafelNummer) {
        /* 1. PLACEHOLDER: implementeer het vinden van de juiste Tafel */
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tafel in restaurant niet gevonden");
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
