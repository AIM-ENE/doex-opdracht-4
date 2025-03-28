package bestelsysteem.service;

import bestelsysteem.dto.Bestelling;
import bestelsysteem.model.Tafel;
import bestelsysteem.repository.GerechtRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class TafelServiceImpl implements TafelService {
    private final GerechtRepository gerechtRepository;

    public TafelServiceImpl(GerechtRepository gerechtRepository) {
        this.gerechtRepository = gerechtRepository;
    }

    public Bestelling getBestelling(Tafel tafel, int bestelnummer) {
        //TODO: je zou hier heel mooi een custom query voor kunnen schrijven
        bestelsysteem.model.Bestelling bestelling = tafel.getBestelling(bestelnummer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bestelling niet gevonden"));

        List<String> collect = bestelling.getGerechten().stream()
                .map(bestellingGerecht -> gerechtRepository.findById(
                                Objects.requireNonNull(bestellingGerecht.gerecht().getId()))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("gerecht %d in bestelling niet bestaand",
                                        bestellingGerecht.gerecht().getId())))
                        .naam())
                .toList();
        return new Bestelling(bestelling.getBestelnummer(), collect);
    }
}
