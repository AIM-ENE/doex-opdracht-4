package bestelsysteem.service;

import bestelsysteem.model.Restaurant;
import bestelsysteem.model.Winkelmand;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final GerechtRepository gerechtRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, GerechtRepository gerechtRepository) {
        this.restaurantRepository = restaurantRepository;
        this.gerechtRepository = gerechtRepository;
    }

    public void plaatsGerechtInWinkelmand(int restaurantId, int winkelmandId, List<String> gerechtNamen) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "restaurant niet gevonden"));
        Winkelmand winkelmand = restaurant.getWinkelmand(winkelmandId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "winkelmand niet gevonden"));

        for(String gerechtNaam : gerechtNamen) {
            bestelsysteem.model.Gerecht gerecht = gerechtRepository.findByRestaurantAndNaam(restaurantId, gerechtNaam)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("gerecht '%s' niet gevonden voor restaurant %d", gerechtNaam, restaurantId)));

            winkelmand.voegGerechtToe(gerecht);
        }
        restaurantRepository.save(restaurant);
    }
}
