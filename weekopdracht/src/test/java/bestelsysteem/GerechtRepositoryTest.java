package bestelsysteem;

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
        Optional<Gerecht> gerecht = gerechtRepository.findByNaam("rib-eye");
        Assertions.assertTrue(gerecht.isPresent());
    }
}
