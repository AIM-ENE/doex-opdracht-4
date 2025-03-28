package bestelsysteem.domein;

import bestelsysteem.dto.Menu;
import bestelsysteem.model.Gerecht;
import bestelsysteem.repository.GerechtRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.Optional;

@DataJdbcTest
public class GerechtRepositoryTest {
    @Autowired
    private GerechtRepository gerechtRepository;

    @Test
    public void testFindByNaam() {
        //ACT
        Optional<Gerecht> gerecht = gerechtRepository.findByNaam("Spaghetti_Bolognese");
        //ASSERT
        Assertions.assertTrue(gerecht.isPresent());
    }

    @Test
    public void testGetMenu() {
        Optional<Menu> menu = gerechtRepository.findMenu(1);
        System.out.println(menu);
    }
}
