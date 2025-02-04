package bestelsysteem.domein;

import bestelsysteem.model.Bestelling;
import bestelsysteem.model.BestellingStatus;
import bestelsysteem.model.Gerecht;
import bestelsysteem.model.Tafel;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.TafelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.List;
import java.util.Optional;

@DataJdbcTest
public class TafelRepositoryTest {
    @Autowired
    private TafelRepository tafelRepository;

    @Autowired
    private GerechtRepository gerechtRepository;

    @Test
    public void testFindById() {
        //ACT
        Optional<Tafel> tafel = tafelRepository.findById(1);

        //ASSERT
        Assertions.assertTrue(tafel.isPresent());
    }

    @Test
    public void testMaakBestelling() {
        //ARRANGE
        Optional<Gerecht> gerecht = gerechtRepository.findByNaam("rib-eye");
        Assertions.assertTrue(gerecht.isPresent());
        Optional<Tafel> tafelOptional = tafelRepository.findById(1);
        Assertions.assertTrue(tafelOptional.isPresent());
        List<Gerecht> bestelling = gerecht.stream().toList();
        Tafel tafel = tafelOptional.get();

        //ACT
        tafel.plaatsBestelling(bestelling);
        tafel = tafelRepository.save(tafel);

        //ASSERT
        Optional<Bestelling> bestellingOptional = tafel.getLaatsteBestelling();
        Assertions.assertTrue(bestellingOptional.isPresent());
        Assertions.assertEquals(bestelling.getFirst().id(),
                bestellingOptional.get().getGerechten().getFirst().gerecht().getId());
    }

    @Test
    public void testGetRekening() {
        //ARRANGE
        Optional<Gerecht> gerecht = gerechtRepository.findByNaam("rib-eye");
        Assertions.assertTrue(gerecht.isPresent());
        Optional<Tafel> tafelOptional = tafelRepository.findById(1);
        Assertions.assertTrue(tafelOptional.isPresent());
        List<Gerecht> bestelling = gerecht.stream().toList();
        Tafel tafel = tafelOptional.get();

        //ACT
        tafel.plaatsBestelling(bestelling);
        tafel = tafelRepository.save(tafel);

        //ASSERT
        double rekening = tafel.getRekening();
        Assertions.assertEquals(gerecht.get().prijs(), rekening);
    }

    @Test
    public void testBetaalRekening() {
        //ARRANGE
        Optional<Gerecht> gerecht = gerechtRepository.findByNaam("rib-eye");
        Assertions.assertTrue(gerecht.isPresent());
        Optional<Tafel> tafelOptional = tafelRepository.findById(1);
        Assertions.assertTrue(tafelOptional.isPresent());
        List<Gerecht> bestelling = gerecht.stream().toList();
        Tafel tafel = tafelOptional.get();

        //ACT
        tafel.plaatsBestelling(bestelling);
        tafel = tafelRepository.save(tafel);
        tafel.betaalRekening(gerecht.get().prijs());
        tafel = tafelRepository.save(tafel);

        //ASSERT
        Assertions.assertEquals( 0, tafel.getRekening());
        Assertions.assertEquals(tafel.getLaatsteBestelling().map(Bestelling::getStatus)
                .orElse(null), BestellingStatus.PAID);
    }
}
