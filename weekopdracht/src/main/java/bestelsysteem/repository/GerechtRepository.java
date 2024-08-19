package bestelsysteem.repository;

import bestelsysteem.model.Gerecht;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GerechtRepository extends CrudRepository<Gerecht, Integer> {
    Optional<Gerecht> findByNaam(String naam);
}
