package aimene.doex.bestelling.repository;

import aimene.doex.bestelling.model.Bestelling;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BestellingRepository extends CrudRepository<Bestelling, Integer> {

    @Query("""
            SELECT *
            FROM bestelling b
            JOIN bestelregel br ON b.id = br.bestelling
            WHERE br.product = :productId
            """)
    List<Bestelling> findAllMetProductId(Integer productId);

}
