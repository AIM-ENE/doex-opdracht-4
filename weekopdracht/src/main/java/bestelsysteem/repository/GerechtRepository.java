package bestelsysteem.repository;

import bestelsysteem.model.Gerecht;
import bestelsysteem.openapi.model.GerechtIngredient;
import bestelsysteem.openapi.model.Menu;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface GerechtRepository extends CrudRepository<Gerecht, Integer> {
    Optional<Gerecht> findByNaam(String naam);

    @Query(value = """
        SELECT  g.id AS gerecht_id,
                g.naam AS gerecht_naam,
                g.prijs AS gerecht_prijs,
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
            Menu menu = new Menu();
            menu.setGerechten(List.copyOf(gerechtMap.values()));
            return new Menu();
        }

        private void mapRow(ResultSet rs) throws SQLException {
            int gerechtId = rs.getInt("gerecht_id");

            bestelsysteem.dto.Gerecht gerecht = gerechtMap.get(gerechtId);
            if (gerecht == null) {
                String gerechtNaam = rs.getString("gerecht_naam");
                double gerechtPrijs = rs.getDouble("gerecht_prijs");
                gerecht = new bestelsysteem.dto.Gerecht(gerechtId, gerechtNaam, gerechtPrijs, new ArrayList<>());
                gerechtMap.put(gerechtId, gerecht);
            }

            int ingredientId = rs.getInt("ingredient_id");
            String ingredientNaam = rs.getString("ingredient_naam");
            int ingredientHoeveelheid = rs.getInt("dosering_hoeveelheid");
            GerechtIngredient gerechtIngredient = new GerechtIngredient();
            gerechtIngredient.setNaam(ingredientNaam);
            gerechtIngredient.setHoeveelheid(ingredientHoeveelheid);
            gerecht.getIngredienten().add(gerechtIngredient);
        }
    }
}
