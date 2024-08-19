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
        Optional<Restaurant> restaurant = restaurantRepository.findById(1);
        Assertions.assertTrue(restaurant.isPresent());
    }

    @Test
    public void testVoorraadBijvullen() {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(1);
        Assertions.assertTrue(restaurantOptional.isPresent());
        Restaurant restaurant = restaurantOptional.get();
        int voorraad = restaurant.getVoorraad(1).getAantal();

        restaurant.voegToeAanVoorraad(1, 100);
        restaurant = restaurantRepository.save(restaurant);

        Voorraad voorraadNaBijvullen = restaurant.getVoorraad(1);
        Assertions.assertEquals(voorraad+100,
                voorraadNaBijvullen.getAantal());
    }

    @Test
    public void testVoorraadReduceren() {
        Optional<Gerecht> gerechtOptional = gerechtRepository.findByNaam("rib-eye");
        Assertions.assertTrue(gerechtOptional.isPresent());
        Gerecht gerecht = gerechtOptional.get();
        Optional<Dosering> doseringIngredientOptional = gerecht.doseringVanIngredient().stream()
                .findFirst();
        Assertions.assertTrue(doseringIngredientOptional.isPresent());
        Dosering dosering = doseringIngredientOptional.get();
        Integer ingredientId = dosering.ingredient().getId();

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(1);
        Assertions.assertTrue(restaurantOptional.isPresent());
        Restaurant restaurant = restaurantOptional.get();

        restaurant.voegToeAanVoorraad(1, dosering.hoeveelheid());
        int voorraad = restaurant.getVoorraad(ingredientId).getAantal();
        restaurant.reduceerVoorraad(gerecht);
        restaurant = restaurantRepository.save(restaurant);

        int voorraadNaGerecht = restaurant.getVoorraad(ingredientId).getAantal();
        Assertions.assertEquals(voorraad-dosering.hoeveelheid(), voorraadNaGerecht);
    }

    @Test
    public void testAanmakenWinkelmand() {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(1);
        Assertions.assertTrue(restaurantOptional.isPresent());
        Restaurant restaurant = restaurantOptional.get();

        Winkelmand winkelmand = restaurant.maakWinkelmand();
        restaurant = restaurantRepository.save(restaurant);
        Optional<Winkelmand> winkelmandOptional = restaurant.getWinkelmandOnDate(winkelmand.datumTijd());
        Assertions.assertTrue(winkelmandOptional.isPresent());
    }

    @Test
    public void testToevoegenWinkelmand() {
        Optional<Gerecht> gerechtOptional = gerechtRepository.findByNaam("rib-eye");
        Assertions.assertTrue(gerechtOptional.isPresent());
        Gerecht gerecht = gerechtOptional.get();
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(1);
        Assertions.assertTrue(restaurantOptional.isPresent());
        Restaurant restaurant = restaurantOptional.get();

        Winkelmand winkelmand = restaurant.maakWinkelmand();
        winkelmand.voegGerechtToe(gerecht);
        restaurant = restaurantRepository.save(restaurant);
        Optional<Winkelmand> winkelmandOptional = restaurant.getWinkelmandOnDate(winkelmand.datumTijd());
        Assertions.assertTrue(winkelmandOptional.isPresent());
        Assertions.assertEquals(gerecht.id(),
                winkelmandOptional.get().gerechten().getFirst().gerecht().getId());
    }

    @Test
    public void testVerwijderenWinkelmand() {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(1);
        Assertions.assertTrue(restaurantOptional.isPresent());
        Restaurant restaurant = restaurantOptional.get();
        Winkelmand winkelmand = restaurant.maakWinkelmand();
        restaurant = restaurantRepository.save(restaurant);
        Optional<Winkelmand> winkelmandOptional = restaurant.getWinkelmandOnDate(winkelmand.datumTijd());
        Assertions.assertTrue(winkelmandOptional.isPresent());

        restaurant.verwijderWinkelmand(winkelmandOptional.get());
        restaurant = restaurantRepository.save(restaurant);
        Optional<Winkelmand> winkelmandOptional2 = restaurant.getWinkelmand(1);
        Assertions.assertFalse(winkelmandOptional2.isPresent());
    }
}
