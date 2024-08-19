package bestelsysteem.repository;

import bestelsysteem.model.Ingredient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    Optional<Ingredient> findByNaam(String naam);
}
