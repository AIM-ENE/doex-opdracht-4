package bestelsysteem.service;

import java.util.List;

public interface RestaurantService {
    void plaatsGerechtInWinkelmand(int restaurantId, int winkelmandId, List<String> gerechtNamen);
}
