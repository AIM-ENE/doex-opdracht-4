package bestelsysteem;

import bestelsysteem.model.*;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.Optional;

@DataJdbcTest
public class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private GerechtRepository gerechtRepository;

    @Test
    public void testFindById() {
        //ACT
        Optional<Restaurant> restaurant = restaurantRepository.findById(1);
        //ASSERT
        Assertions.assertTrue(restaurant.isPresent());
    }

    @Test
    public void testAanmakenWinkelmand() {
        //ARRANGE
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(1);
        Assertions.assertTrue(restaurantOptional.isPresent());
        Restaurant restaurant = restaurantOptional.get();

        //ACT
        Winkelmand winkelmand = restaurant.maakWinkelmand();
        restaurant = restaurantRepository.save(restaurant);

        //ASSERT
        Optional<Winkelmand> winkelmandOptional = restaurant.getWinkelmandOnDate(winkelmand.datumTijd());
        Assertions.assertTrue(winkelmandOptional.isPresent());
    }

    @Test
    public void testToevoegenWinkelmand() {
        //ARRANGE
        Optional<Gerecht> gerechtOptional = gerechtRepository.findByNaam("rib-eye");
        Assertions.assertTrue(gerechtOptional.isPresent());
        Gerecht gerecht = gerechtOptional.get();
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(1);
        Assertions.assertTrue(restaurantOptional.isPresent());
        Restaurant restaurant = restaurantOptional.get();

        //ACT
        Winkelmand winkelmand = restaurant.maakWinkelmand();
        winkelmand.voegGerechtToe(gerecht);
        restaurant = restaurantRepository.save(restaurant);

        //ASSERT
        Optional<Winkelmand> winkelmandOptional = restaurant.getWinkelmandOnDate(winkelmand.datumTijd());
        Assertions.assertTrue(winkelmandOptional.isPresent());
        Assertions.assertEquals(gerecht.id(),
                winkelmandOptional.get().gerechten().getFirst().gerecht().getId());
    }

    @Test
    public void testVerwijderenWinkelmand() {
        //ARRANGE
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(1);
        Assertions.assertTrue(restaurantOptional.isPresent());
        Restaurant restaurant = restaurantOptional.get();
        Winkelmand winkelmand = restaurant.maakWinkelmand();
        restaurant = restaurantRepository.save(restaurant);
        Optional<Winkelmand> winkelmandOptional = restaurant.getWinkelmandOnDate(winkelmand.datumTijd());
        Assertions.assertTrue(winkelmandOptional.isPresent());

        //ACT
        restaurant.verwijderWinkelmand(winkelmandOptional.get());
        restaurant = restaurantRepository.save(restaurant);

        //ASSERT
        Optional<Winkelmand> winkelmandOptional2 = restaurant.getWinkelmand(1);
        Assertions.assertFalse(winkelmandOptional2.isPresent());
    }
}
