package bestelsysteem.repository;

import bestelsysteem.dto.Menu;
import bestelsysteem.model.Gang;
import bestelsysteem.model.Gerecht;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface GerechtRepository extends CrudRepository<Gerecht, Integer> {
    Optional<Gerecht> findByRestaurantAndNaam(int restaurantId, String naam);

    @Query(value = """
        SELECT  g.id AS gerecht_id,
                g.naam AS gerecht_naam,
                g.prijs AS gerecht_prijs,
                g.gang AS gerecht_gang,
                gi.hoeveelheid AS dosering_hoeveelheid,
                i.naam AS ingredient_naam,
                i.id AS ingredient_id
        FROM Gerecht g
                JOIN Dosering gi ON g.id = gi.gerecht
                JOIN Ingredient i ON gi.ingredient = i.id
        WHERE g.restaurant = :restaurantId
        """, resultSetExtractorClass= MenuResultSetExtractor.class)
    Optional<Menu> findMenu(@Param("restaurantId") int restaurantId);

    class MenuResultSetExtractor implements ResultSetExtractor<Menu> {
        private final Map<Integer, bestelsysteem.dto.Gerecht> gerechtMap = new HashMap<>();


        @Override
        public Menu extractData(ResultSet rs) throws SQLException, DataAccessException {
            while (rs.next()) {
                mapRow(rs);
            }
            return new Menu(List.copyOf(gerechtMap.values()));
        }

        private void mapRow(ResultSet rs) throws SQLException {
            int gerechtId = rs.getInt("gerecht_id");

            bestelsysteem.dto.Gerecht gerecht = gerechtMap.get(gerechtId);
            if (gerecht == null) {
                String gerechtNaam = rs.getString("gerecht_naam");
                double gerechtPrijs = rs.getDouble("gerecht_prijs");
                Gang gang = Gang.valueOf(rs.getString("gerecht_gang"));
                gerecht = new bestelsysteem.dto.Gerecht(gerechtId, gerechtNaam, gerechtPrijs, gang, new ArrayList<>());
                gerechtMap.put(gerechtId, gerecht);
            }

            int ingredientId = rs.getInt("ingredient_id");
            String ingredientNaam = rs.getString("ingredient_naam");
            int ingredientHoeveelheid = rs.getInt("dosering_hoeveelheid");
            bestelsysteem.dto.Gerecht.GerechtIngredient gerechtIngredient = new bestelsysteem.dto.Gerecht.GerechtIngredient(
                    ingredientNaam, ingredientHoeveelheid);
            gerecht.ingredienten().add(gerechtIngredient);
        }
    }
}
