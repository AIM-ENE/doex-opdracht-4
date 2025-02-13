package bestelsysteem.repository;

import bestelsysteem.model.Tafel;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TafelRepository extends CrudRepository<Tafel, Integer> {
}
