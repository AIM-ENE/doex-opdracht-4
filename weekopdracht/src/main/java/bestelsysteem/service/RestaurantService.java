package bestelsysteem.service;

import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final GerechtRepository gerechtRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, GerechtRepository gerechtRepository) {
        this.restaurantRepository = restaurantRepository;
        this.gerechtRepository = gerechtRepository;
    }

    public void plaatsGerechtInWinkelmand(int restaurantId, int winkelmandId, List<String> gerechtNamen) {
        restaurantRepository.findById(restaurantId).map(restaurant ->
            restaurant.getWinkelmand(winkelmandId).map(winkelmand -> {
                for(String gerechtNaam : gerechtNamen) {
                    gerechtRepository.findByNaam(gerechtNaam).ifPresent(winkelmand::voegGerechtToe);
                }
                return restaurantRepository.save(restaurant);
            })
        );
    }
}
