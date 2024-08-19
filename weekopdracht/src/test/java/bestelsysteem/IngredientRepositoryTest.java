package bestelsysteem;

import bestelsysteem.model.Ingredient;
import bestelsysteem.repository.IngredientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.Optional;

@DataJdbcTest
public class IngredientRepositoryTest {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void testFindByNaam() {
        //ACT
        Optional<Ingredient> ingredient = ingredientRepository.findByNaam("zalm");
        //ASSERT
        Assertions.assertTrue(ingredient.isPresent());
    }
}
