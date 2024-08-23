package bestelsysteem.service;

import bestelsysteem.dto.Bestelling;
import bestelsysteem.model.Gerecht;
import bestelsysteem.model.Tafel;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.TafelRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TafelService {
    private final TafelRepository tafelRepository;
    private final GerechtRepository gerechtRepository;

    public TafelService(TafelRepository tafelRepository, GerechtRepository gerechtRepository) {
        this.tafelRepository = tafelRepository;
        this.gerechtRepository = gerechtRepository;
    }

    public Optional<Bestelling> getBestelling(@PathVariable("tafelCode") Tafel tafel,
                                              @PathVariable("bestelnummer") int bestelnummer) {
        //TODO: je zou hier heel mooi een custom query voor kunnen schrijven
        Optional<bestelsysteem.model.Bestelling> bestellingOptional = tafel.getBestelling(bestelnummer);
        return bestellingOptional.map(bestelling -> {
            List<String> collect = bestelling.gerechten().stream()
                    .map(bestellingGerecht -> gerechtRepository.findById(
                                    Objects.requireNonNull(bestellingGerecht.gerecht().getId()))
                            .map(Gerecht::naam)
                            .orElse("gerecht not found"))
                    .toList();
            return new Bestelling(bestelling.id(), collect);
        });
    }

    public Integer plaatsBestelling(Tafel tafel, List<Gerecht> gerechten) {
        int bestelnummer = -1;
        if(!gerechten.isEmpty()) {
            tafel.plaatsBestelling(gerechten);
            tafelRepository.save(tafel);
            bestelnummer = tafel.getLaatsteBestelling();
        }
        return bestelnummer;
    }
}
